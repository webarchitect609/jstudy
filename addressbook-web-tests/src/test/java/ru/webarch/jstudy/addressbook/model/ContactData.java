package ru.webarch.jstudy.addressbook.model;

public class ContactData {
    private String firstName = null;
    private String midName = null;
    private String lastName = null;
    private String nickname = null;
    private String title = null;
    private String company = null;
    private String address = null;
    private String phoneHome = null;
    private String phoneMobile = null;
    private String phoneWork = null;
    private String fax = null;
    private String email = null;
    private String group = null;

    public ContactData(String lastName, String firstName, String email) {
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setEmail(email);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMidName() {
        return midName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    @SuppressWarnings("WeakerAccess")
    public ContactData setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContactData setMidName(String midName) {
        this.midName = midName;
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public ContactData setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactData setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public ContactData setTitle(String title) {
        this.title = title;
        return this;
    }

    public ContactData setCompany(String company) {
        this.company = company;
        return this;
    }

    public ContactData setAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactData setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
        return this;
    }

    public ContactData setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
        return this;
    }

    public ContactData setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
        return this;
    }

    public ContactData setFax(String fax) {
        this.fax = fax;
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public ContactData setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public ContactData setGroup(String group) {
        this.group = group;
        return this;
    }
}
