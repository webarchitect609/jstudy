package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends HelperBase{

    GroupHelper(WebDriver wd, ApplicationManager app) {
        super(wd, app);
    }

    @SuppressWarnings("WeakerAccess")
    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    @SuppressWarnings("WeakerAccess")
    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    @SuppressWarnings("WeakerAccess")
    public void fillGroupForm(GroupData groupData) {
        type(By.name("group_name"), groupData.getName());
        type(By.name("group_header"), groupData.getHeader());
        type(By.name("group_footer"), groupData.getFooter());
    }

    @SuppressWarnings("WeakerAccess")
    public void initGroupCreation() {
        click(By.name("new"));
    }

    @SuppressWarnings("WeakerAccess")
    public void deleteGroups() {
        click(By.name("delete"));
    }

    @SuppressWarnings("WeakerAccess")
    public void selectGroups(int index) {
        WebElement group = wd.findElements(By.name("selected[]")).get(index);
        if (!group.isSelected()) {
            group.click();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void initGroupModification() {
        click(By.name("edit"));
    }

    @SuppressWarnings("WeakerAccess")
    public void submitGroupModification() {
        click(By.name("update"));
    }

    public void create(GroupData groupData) {
        initGroupCreation();
        fillGroupForm(groupData);
        submitGroupCreation();
        returnToGroupPage();
    }

    public void modify(int groupIndex, GroupData groupData) {
        selectGroups(groupIndex);
        initGroupModification();
        fillGroupForm(groupData);
        submitGroupModification();
        returnToGroupPage();
    }

    public void delete(int groupIndex) {
        selectGroups(groupIndex);
        deleteGroups();
        returnToGroupPage();
    }

    public List<GroupData> list() {
        List<GroupData> groups = new ArrayList<>();
        List<WebElement> groupElements = wd.findElements(By.cssSelector("span.group"));
        for (WebElement groupElement : groupElements) {
            int id = Integer.parseInt(groupElement.findElement(By.tagName("input")).getAttribute("value"));
            String groupName = groupElement.getText();
            groups.add(
                    new GroupData()
                    .withId(id)
                    .withName(groupName)
            );
        }
        return groups;
    }
}
