package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract class HelperBase {

    @SuppressWarnings("WeakerAccess")
    protected WebDriver wd;

    @SuppressWarnings("WeakerAccess")
    protected ApplicationManager app;

    @SuppressWarnings("unused")
    private int waitElementInSeconds = 10;

    @SuppressWarnings("WeakerAccess")
    protected HelperBase(WebDriver wd, ApplicationManager app) {
        this.wd = wd;
        this.app = app;
    }

    @SuppressWarnings("WeakerAccess")
    protected void click(By locator) {
        wd.findElement(locator).click();
    }

    @SuppressWarnings("WeakerAccess")
    protected void type(By locator, String text) {
        click(locator);
        if (text != null) {
            String existingText = wd.findElement(locator).getAttribute("value");
            if (!text.equals(existingText)) {
                wd.findElement(locator).clear();
                wd.findElement(locator).sendKeys(text);
            }
        }
    }

    @SuppressWarnings({"WeakerAccess", "unused"})
    protected void setSelected(By locator) {
        if (!wd.findElement(locator).isSelected()) {
            click(locator);
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected boolean isElementPresent(By locator) {
        try {
            wd.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected boolean isElementPresent(WebElement webElement, By locator) {
        try {
            webElement.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public boolean isAlertPresent() {
        try {
            this.wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void acceptAlert() {
        wd.switchTo().alert().accept();

        /**
         * Ожидание, пока алерта явно не будет
         * Тоже заплатка для стабильной работы Opera
         * Иначе вылазиет ошибка типа
         * UnhandledAlertException: unexpected alert open
         */
        //TODO Внедрить для оперы эту заплатку
        //Thread.sleep(1000)
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
    @SuppressWarnings("unused")
    protected WebElement getElementWhenClickable(By locator, int timeOutInSeconds) {
        return (new WebDriverWait(wd, timeOutInSeconds))
                .until(
                        ExpectedConditions.elementToBeClickable(locator)
                );
    }

    /**
     * Выбрать элемент из выпадающего списка (select)
     * @param locator Локатор
     * @param visibleText видимый текст
     */
    @SuppressWarnings("WeakerAccess")
    protected void selectOption(By locator, String visibleText) {
        if (visibleText != null) {
            new Select(wd.findElement(locator)).selectByVisibleText(visibleText);
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected boolean isOptionExistsInSelect(By selectLocator, String optionVisibleText) {
        try {
            selectOption(selectLocator, optionVisibleText);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
