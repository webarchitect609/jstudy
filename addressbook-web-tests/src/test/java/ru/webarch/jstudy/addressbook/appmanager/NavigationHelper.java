package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    NavigationHelper(WebDriver wd, ApplicationManager app) {
        super(wd, app);
    }

    public void groupPage() {

        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("GROUPS")
                && isElementPresent(By.name("new"))
                ) {
            return;
        }
        click(By.xpath("//a[@href=\"group.php\"]"));
    }

    public void contactPage() {
        if (isElementPresent(By.id("maintable"))) {
            return;
        }
        click(By.xpath("//*[@id='nav']/ul/li[1]/a"));
    }
}
