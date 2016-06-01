package ru.webarch.jstudy.mantis.tests;

import org.testng.annotations.*;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.webarch.jstudy.mantis.model.MailMessage;
import ru.webarch.jstudy.mantis.model.MantisUser;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PasswordResetTests extends TestBase {

    @BeforeSuite
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testPasswordReset() throws IOException {

        List<MantisUser> allUsers = app.db().allUsers();
        MantisUser targetUser = allUsers.get(new Random().nextInt(allUsers.size()));
        targetUser.withPassword("NewPassword#" + System.currentTimeMillis());

        app.log().info(String.format("Target user is %s", targetUser));
        app.log().info(String.format("New pass is %s", targetUser.getPassword()));

        app.user().login(
                new MantisUser()
                        .withUsername(app.getProperty("web.adminUsername"))
                        .withPassword(app.getProperty("web.adminPass"))
        );
        app.user().resetPassword(targetUser);
        app.user().logout();

        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        app.user().setPassword(findPasswordChangleLink(mailMessages, targetUser), targetUser);

        assertThat(app.newSession().login(targetUser), is(true));
    }

    private String findPasswordChangleLink(List<MailMessage> mailMessages, MantisUser user) {
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(user.getEmail())).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterSuite(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }

}
