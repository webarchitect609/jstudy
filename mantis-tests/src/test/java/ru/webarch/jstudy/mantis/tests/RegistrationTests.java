package ru.webarch.jstudy.mantis.tests;

import org.testng.annotations.Test;

public class RegistrationTests extends TestBase {

    @Test
    public void testRegistration() {
        app.registration().start("chuck-norris", "chuck-norris@example.com");
    }

}
