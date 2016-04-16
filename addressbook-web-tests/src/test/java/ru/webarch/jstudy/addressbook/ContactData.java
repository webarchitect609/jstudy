package ru.webarch.jstudy.addressbook;

public class ContactData {
    private final String firstName;
    private final String midName;
    private final String lastName;
    private final String nickname;
    private final String title;
    private final String company;
    private final String address;
    private final String phoneHome;
    private final String phoneMobile;
    private final String phoneWork;
    private final String fax;
    private final String email;

    public ContactData(String firstName, String midName, String lastName, String nickname, String title, String company, String address, String phoneHome, String phoneMobile, String phoneWork, String fax, String email) {
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.title = title;
        this.company = company;
        this.address = address;
        this.phoneHome = phoneHome;
        this.phoneMobile = phoneMobile;
        this.phoneWork = phoneWork;
        this.fax = fax;
        this.email = email;
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
}
