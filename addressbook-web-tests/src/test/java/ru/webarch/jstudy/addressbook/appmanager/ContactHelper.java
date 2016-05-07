package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.webarch.jstudy.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    ContactHelper(WebDriver wd, ApplicationManager app) {
        super(wd, app);
    }

    @SuppressWarnings("WeakerAccess")
    public void returnToContactList() {
        click(By.linkText("home page"));
    }

    @SuppressWarnings("WeakerAccess")
    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    @SuppressWarnings("WeakerAccess")
    public void fillContactForm(ContactData contactData, boolean creation) {

        //Если контакт создаётся и задана группа
        if (creation && contactData.getGroup() != null) {
            //А её в выпадашке нет
            Assert.assertTrue(
                    isOptionExistsInSelect(By.name("new_group"), contactData.getGroup()),
                    "проверка наличия группы `" + contactData.getGroup() + "` в выпадающем списке"
            );
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

    @SuppressWarnings("WeakerAccess")
    public void initContactCreation() {
        click(By.xpath("//a[@href=\"edit.php\"]"));
    }

    @SuppressWarnings("WeakerAccess")
    public void editContact(int index) {
        wd.findElements(By.xpath("//*[@id='maintable']//tr[@name=\"entry\"]//td[8]/a/img")).get(index).click();
    }

    @SuppressWarnings("WeakerAccess")
    public void submitContactModification() {
        click(By.name("update"));
    }

    @SuppressWarnings("WeakerAccess")
    public void selectContacts(int index) {
        WebElement contact = wd.findElements(By.name("selected[]")).get(index);
        if (!contact.isSelected()) {
            contact.click();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void submitContactDeletion() {
        click(By.xpath("//div/div[4]/form[2]/div[2]/input"));
    }

    @SuppressWarnings("WeakerAccess")
    public void confirmContactDeletion() {
        if (isAlertPresent()) {
            acceptAlert();
        }
    }

    public void create(ContactData contactData) {
        initContactCreation();
        fillContactForm(contactData, true);
        submitContactCreation();
        returnToContactList();
    }

    public void modify(int contactIndex, ContactData editedContact) {
        editContact(contactIndex);
        fillContactForm(editedContact, false);
        submitContactModification();
        returnToContactList();
    }

    public void delete(int contactIndex) {
        selectContacts(contactIndex);
        submitContactDeletion();
        confirmContactDeletion();
        app.goTo().contactPage();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> contactElements = wd.findElements(By.cssSelector("tr[name=\"entry\"]"));
        for (WebElement contactElement : contactElements) {
            int id = Integer.parseInt(contactElement.findElement(By.tagName("input")).getAttribute("value"));
            String lastName = contactElement.findElement(By.xpath(".//td[2]")).getText();
            String firstName = contactElement.findElement(By.xpath(".//td[3]")).getText();
            String address = contactElement.findElement(By.xpath(".//td[4]")).getText();
            String email = contactElement.findElement(By.xpath(".//td[5]/a[1]")).getText();

            contacts.add(
                    new ContactData()
                            .withId(id)
                            .withLastName(lastName)
                            .withFirstName(firstName)
                            .withAddress(address)
                            .withEmail(email)
            );
        }
        return contacts;
    }
}
