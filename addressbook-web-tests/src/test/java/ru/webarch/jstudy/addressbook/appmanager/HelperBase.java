package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract class HelperBase {

    @SuppressWarnings("WeakerAccess")
    protected WebDriver wd;

    private int waitElementInSeconds = 10;

    @SuppressWarnings("WeakerAccess")
    protected HelperBase(WebDriver wd) {
        this.wd = wd;
    }

    @SuppressWarnings("WeakerAccess")
    protected void click(By locator) {
        WebElement element = getElementWhenClickable(locator, waitElementInSeconds);
        element.click();
    }

    @SuppressWarnings("WeakerAccess")
    protected void type(By locator, String text) {
        click(locator);
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
    }

    @SuppressWarnings("WeakerAccess")
    protected void setSelected(By locator) {
        WebElement element = getElementWhenClickable(locator, waitElementInSeconds);
        if (!element.isSelected()) {
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

        /**
         * Ожидание, пока алерта явно не будет
         * Тоже заплатка для стабильной работы Opera
         * Иначе вылазиет ошибка типа
         * UnhandledAlertException: unexpected alert open
         */
        //TODO Не работает! :(
        (new WebDriverWait(wd, waitElementInSeconds))
                .until(
                        ExpectedConditions.not(
                                ExpectedConditions.alertIsPresent()
                        )
                );
    }

    /**
     * Получить элемент, дождавшись его готовности к кликанью
     *
     * Заплатка для Opera. Возможно, придётся применить и в дургих местах
     * Поиск элемента осуществляется иногда одновременно с перезагрузкой страницы и вылазиет ошибка типа
     * Stale Element Reference Exception
     *
     * @param locator          Локатор
     * @param timeOutInSeconds Таймаут ожидания в секундах
     * @return Ожидаемый элемент
     */
    protected WebElement getElementWhenClickable(By locator, int timeOutInSeconds) {
        return (new WebDriverWait(wd, timeOutInSeconds))
                .until(
                        ExpectedConditions.elementToBeClickable(locator)
                );
    }
}
