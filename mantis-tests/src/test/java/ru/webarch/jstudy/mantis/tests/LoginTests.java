package ru.webarch.jstudy.mantis.tests;

import org.testng.annotations.Test;
import ru.webarch.jstudy.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LoginTests extends TestBase {

    @Test
    public void testLogin() throws IOException {
        HttpSession session = app.newSession();
        assertThat(session.login("administrator", "root"), is(true));
        assertThat(session.isLoggedInAs("administrator"), is(true));
    }

}
