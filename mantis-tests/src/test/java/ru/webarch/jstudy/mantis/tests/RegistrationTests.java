package ru.webarch.jstudy.mantis.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.webarch.jstudy.mantis.model.MailMessage;
import ru.webarch.jstudy.mantis.model.MantisUser;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RegistrationTests extends TestBase {

//    @BeforeSuite
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testRegistration() throws IOException, MessagingException {
        long ts = System.currentTimeMillis();
        MantisUser user = new MantisUser()
                .withEmail(String.format("chuck-norris_clone%s@localhost", ts))
                .withUsername("chuck-norris_clone" + ts)
                .withPassword("password");

        app.james().createUser(user);

        app.user().register(user);
//        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        List<MailMessage> mailMessages = app.james().waitForMail(user, 60000);
        app.user().setPassword(findConfirmationLink(mailMessages, user), user);
        assertThat(app.newSession().login(user), is(true));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, MantisUser user) {
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(user.getEmail())).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

//    @AfterSuite(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }

}
