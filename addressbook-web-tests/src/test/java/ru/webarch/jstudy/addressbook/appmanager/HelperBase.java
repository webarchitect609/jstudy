package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;

abstract class HelperBase {

    @SuppressWarnings("WeakerAccess")
    protected FirefoxDriver wd;

    @SuppressWarnings("WeakerAccess")
    protected HelperBase(FirefoxDriver wd) {
        this.wd = wd;
    }

    @SuppressWarnings("WeakerAccess")
    protected void click(By locator) {
        wd.findElement(locator).click();
    }

    @SuppressWarnings("WeakerAccess")
    protected void type(By locator, String text) {
        click(locator);
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
    }

    @SuppressWarnings("WeakerAccess")
    protected void setSelected(By locator) {
        if (!wd.findElement(locator).isSelected()) {
            click(locator);
        }
    }

    public boolean isAlertPresent() {
        try {
            this.wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public void acceptAlert() {
        wd.switchTo().alert().accept();
    }
}
