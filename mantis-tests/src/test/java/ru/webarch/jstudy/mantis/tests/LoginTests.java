package ru.webarch.jstudy.mantis.tests;

import org.testng.annotations.Test;
import ru.webarch.jstudy.mantis.appmanager.HttpSession;
import ru.webarch.jstudy.mantis.model.MantisUser;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LoginTests extends TestBase {

    @Test
    public void testLogin() throws IOException {
        HttpSession session = app.newSession();
        MantisUser user = new MantisUser()
                .withUsername(app.getProperty("web.adminUsername"))
                .withPassword(app.getProperty("web.adminPass"));
        assertThat(session.login(user), is(true));
        assertThat(session.isLoggedInAs(user), is(true));
    }

}
