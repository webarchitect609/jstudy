package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.GroupData;

public class ContactHelper extends HelperBase {

    ContactHelper(WebDriver wd, ApplicationManager app) {
        super(wd, app);
    }

    public void returnToContactList() {
        click(By.linkText("home page"));
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {

        //Если контакт создаётся и задана группа, а её в выпадашке нет
        if (creation && contactData.getGroup() != null && !isOptionExistsInSelect(By.name("new_group"), contactData.getGroup())) {
            //Создать нужную группу
            app.getNavigationHelper().gotoGroupPage();
            app.getGroupHelper().createGroup(new GroupData(contactData.getGroup(), null, null));
            //И вернуться к созданию контакта
            app.getNavigationHelper().gotoContactPage();
            initContactCreation();
        }

        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("middlename"), contactData.getMidName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("title"), contactData.getTitle());
        type(By.name("company"), contactData.getCompany());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getPhoneHome());
        type(By.name("mobile"), contactData.getPhoneMobile());
        type(By.name("work"), contactData.getPhoneWork());
        type(By.name("fax"), contactData.getFax());
        type(By.name("email"), contactData.getEmail());

        if (creation) {
            selectOption(By.name("new_group"), contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.xpath("//a[@href=\"edit.php\"]"));
    }

    public void editContact() {
        click(By.xpath("//*[@id='maintable']//tr[@name=\"entry\"]//td[8]/a/img"));
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void selectContacts() {
        setSelected(By.name("selected[]"));
    }

    public void submitContactDeletion() {
        click(By.xpath("//div/div[4]/form[2]/div[2]/input"));
    }

    public void confirmContactDeletion() {
        if (isAlertPresent()) {
            acceptAlert();
        }
    }

    public void createContact(ContactData contactData) {
        initContactCreation();
        fillContactForm(contactData, true);
        submitContactCreation();
        returnToContactList();
    }

    public boolean isContactsPresent() {
        return isElementPresent(By.name("selected[]"));
    }
}
