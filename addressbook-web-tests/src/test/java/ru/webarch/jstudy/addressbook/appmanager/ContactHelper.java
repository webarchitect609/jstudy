package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.ContactSet;

import java.util.List;

public class ContactHelper extends HelperBase {


    /**
     * Кеш списка контактов
     */
    private ContactSet contactSetCache;

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
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("fax"), contactData.getFax());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        type(By.name("homepage"), contactData.getHomepage());
        //TODO Сделать заполнение даты рождения и годовщины
        type(By.name("address2"), contactData.getSecondaryAddress());
        type(By.name("phone2"), contactData.getHomePhone2());
        type(By.name("notes"), contactData.getNotes());

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
    public void editContactById(int id) {
        wd.findElement(By.xpath(String.format("//a[@href='edit.php?id=%s']", id))).click();
    }

    private void viewContactById(int id) {
        wd.findElement(By.xpath(String.format("//a[@href='view.php?id=%s']", id))).click();
    }

    @SuppressWarnings("WeakerAccess")
    public void submitContactModification() {
        click(By.name("update"));
    }

    @SuppressWarnings("WeakerAccess")
    public void selectContactById(int id) {
        WebElement contact = wd.findElement(
                By.cssSelector(String.format("input[name=\"selected[]\"][value=\"%s\"]", id))
        );
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
        resetCache();
        returnToContactList();
    }

    public void modify(ContactData contact) {
        editContactById(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        resetCache();
        returnToContactList();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        submitContactDeletion();
        confirmContactDeletion();
        resetCache();
        app.goTo().contactPage();
    }

    public ContactSet all() {
        if (contactSetCache != null) {
            return new ContactSet(contactSetCache);
        }
        contactSetCache = new ContactSet();
        List<WebElement> contactElements = wd.findElements(By.cssSelector("tr[name=\"entry\"]"));
        for (WebElement contactElement : contactElements) {
            int id = Integer.parseInt(contactElement.findElement(By.tagName("input")).getAttribute("value"));
            String lastName = contactElement.findElement(By.xpath(".//td[2]")).getText();
            String firstName = contactElement.findElement(By.xpath(".//td[3]")).getText();
            String address = contactElement.findElement(By.xpath(".//td[4]")).getText();

            String email = "", email2 = "", email3 = "";

            By emailLocator = By.xpath(".//td[5]/a[1]");
            if (isElementPresent(contactElement, emailLocator)) {
                email = contactElement.findElement(emailLocator).getText();
            }

            By emailLocator2 = By.xpath(".//td[5]/a[2]");
            if (isElementPresent(contactElement, emailLocator2)) {
                email2 = contactElement.findElement(emailLocator2).getText();
            }

            By emailLocator3 = By.xpath(".//td[5]/a[3]");
            if (isElementPresent(contactElement, emailLocator3)) {
                email3 = contactElement.findElement(emailLocator3).getText();
            }

            String allPhones = contactElement.findElement(By.xpath(".//td[6]")).getText();
            contactSetCache.add(
                    new ContactData()
                            .withId(id)
                            .withLastName(lastName)
                            .withFirstName(firstName)
                            .withAddress(address)
                            .withEmail(email)
                            .withEmail2(email2)
                            .withEmail3(email3)
                            .withAllPhones(allPhones)
            );
        }
        return new ContactSet(contactSetCache);
    }

    public ContactData fromEditForm(ContactData contact) {
        editContactById(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String midName = wd.findElement(By.name("middlename")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String nickname = wd.findElement(By.name("nickname")).getAttribute("value");
        String company = wd.findElement(By.name("company")).getAttribute("value");
        String title = wd.findElement(By.name("title")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getText();
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String fax = wd.findElement(By.name("fax")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String homepage = wd.findElement(By.name("homepage")).getAttribute("value");
        String address2 = wd.findElement(By.name("address2")).getText();
        String phone2 = wd.findElement(By.name("phone2")).getAttribute("value");
        String notes = wd.findElement(By.name("notes")).getText();


        wd.navigate().back();

        return new ContactData()
                .withFirstName(firstName)
                .withMidName(midName)
                .withLastName(lastName)
                .withNickname(nickname)
                .withCompany(company)
                .withTitle(title)
                .withAddress(address)
                .withHomePhone(home)
                .withMobilePhone(mobile)
                .withWorkPhone(work)
                .withFax(fax)
                .withEmail(email)
                .withEmail2(email2)
                .withEmail3(email3)
                .withHomepage(homepage)
                .withSecondaryAddress(address2)
                .withHomePhone2(phone2)
                .withNotes(notes);
    }

    private void resetCache() {
        contactSetCache = null;
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public String fromDetailPage(ContactData contact) {
        viewContactById(contact.getId());
        String contactDetailInfo = wd.findElement(By.id("content")).getText();

        //Проверка, не показываются ли группы, в которые входит контакт
        By groupEnumerator = By.xpath("//div[@id=\"content\"]/i/a/..");
        if (isElementPresent(groupEnumerator)) {
            String groupEnumeratorText = wd.findElement(groupEnumerator).getText();
            //Удалить из текста описание групп, т.к. добыть эту информацию из формы редактирования нельзя
            contactDetailInfo = contactDetailInfo.replaceAll(groupEnumeratorText, "");
        }

        wd.navigate().back();
        return contactDetailInfo.trim();
    }
}
