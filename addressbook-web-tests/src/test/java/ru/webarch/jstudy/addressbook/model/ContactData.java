package ru.webarch.jstudy.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;

@XStreamAlias("contact")
@Entity
@Table(name = "addressbook")
public class ContactData {

    @XStreamOmitField
    @Id
    @Column(name = "id")
    private int id;

    @Expose
    @Column(name = "firstname")
    private String firstName = "";

    @Expose
    @Column(name = "middlename")
    private String midName = "";

    @Expose
    @Column(name = "lastname")
    private String lastName = "";

    @Expose
    @Column(name = "nickname")
    private String nickname = "";

    @Expose
    @Column(name = "title")
    private String title = "";

    @Expose
    @Column(name = "company")
    private String company = "";

    @Expose
    @Column(name = "address")
    @Type(type = "text")
    private String address = "";

    @Expose
    @Column(name = "home")
    @Type(type = "text")
    private String homePhone = "";

    @Expose
    @Column(name = "mobile")
    @Type(type = "text")
    private String mobilePhone = "";

    @Expose
    @Column(name = "work")
    @Type(type = "text")
    private String workPhone = "";

    @Expose
    @Transient
    private String allPhones = null;

    @Expose
    @Column(name = "fax")
    @Type(type = "text")
    private String fax = "";

    @Expose
    @Column(name = "email")
    @Type(type = "text")
    private String email = "";

    @Expose
    @Column(name = "email2")
    @Type(type = "text")
    private String email2 = "";

    @Expose
    @Column(name = "email3")
    @Type(type = "text")
    private String email3 = "";

    @Expose
    @Transient
    private String group = null;

    @Expose
    @Column(name = "address2")
    @Type(type = "text")
    private String secondaryAddress = "";

    @Expose
    @Column(name = "homepage")
    @Type(type = "text")
    private String homepage = "";

    @Expose
    @Column(name = "phone2")
    @Type(type = "text")
    private String homePhone2 = "";

    @Expose
    @Column(name = "notes")
    @Type(type = "text")
    private String notes = "";

    @Column(name = "photo")
    @Type(type = "text")
    private String photo = "";


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

    public String getHomePhone() {
        return homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    @SuppressWarnings("WeakerAccess")
    public ContactData withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContactData withMidName(String midName) {
        this.midName = midName;
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public ContactData withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactData withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public ContactData withTitle(String title) {
        this.title = title;
        return this;
    }

    public ContactData withCompany(String company) {
        this.company = company;
        return this;
    }

    public ContactData withAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactData withHomePhone(String phoneHome) {
        this.homePhone = phoneHome;
        return this;
    }

    public ContactData withMobilePhone(String phoneMobile) {
        this.mobilePhone = phoneMobile;
        return this;
    }

    public ContactData withWorkPhone(String phoneWork) {
        this.workPhone = phoneWork;
        return this;
    }

    public ContactData withFax(String fax) {
        this.fax = fax;
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public ContactData withEmail(String email) {
        this.email = email;
        return this;
    }

    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public ContactData withGroup(String group) {
        this.group = group;
        return this;
    }


    public int getId() {
        return id;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public ContactData withEmail2(String email2) {
        this.email2 = email2;
        return this;
    }

    public String getEmail2() {
        return email2;
    }

    public ContactData withEmail3(String email3) {
        this.email3 = email3;
        return this;
    }

    public String getEmail3() {
        return email3;
    }

    public ContactData withSecondaryAddress(String secondaryAddress) {
        this.secondaryAddress = secondaryAddress;
        return this;
    }

    public String getSecondaryAddress() {
        return secondaryAddress;
    }

    public ContactData withHomepage(String homepage) {
        this.homepage = homepage;
        return this;
    }

    public String getHomepage() {
        return homepage;
    }

    public ContactData withHomePhone2(String homePhone2) {
        this.homePhone2 = homePhone2;
        return this;
    }

    public String getHomePhone2() {
        return homePhone2;
    }

    public ContactData withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public File getPhoto() {
        if (photo == null) {
            photo = "";
        }
        return new File(photo);
    }

    public ContactData withPhoto(File photo) {
        this.photo = photo.getAbsolutePath();
        return this;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (midName != null ? !midName.equals(that.midName) : that.midName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (homePhone != null ? !homePhone.equals(that.homePhone) : that.homePhone != null) return false;
        if (mobilePhone != null ? !mobilePhone.equals(that.mobilePhone) : that.mobilePhone != null) return false;
        if (workPhone != null ? !workPhone.equals(that.workPhone) : that.workPhone != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (email2 != null ? !email2.equals(that.email2) : that.email2 != null) return false;
        if (email3 != null ? !email3.equals(that.email3) : that.email3 != null) return false;
        if (secondaryAddress != null ? !secondaryAddress.equals(that.secondaryAddress) : that.secondaryAddress != null)
            return false;
        if (homepage != null ? !homepage.equals(that.homepage) : that.homepage != null) return false;
        if (homePhone2 != null ? !homePhone2.equals(that.homePhone2) : that.homePhone2 != null) return false;
        return notes != null ? notes.equals(that.notes) : that.notes == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (midName != null ? midName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (homePhone != null ? homePhone.hashCode() : 0);
        result = 31 * result + (mobilePhone != null ? mobilePhone.hashCode() : 0);
        result = 31 * result + (workPhone != null ? workPhone.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (email2 != null ? email2.hashCode() : 0);
        result = 31 * result + (email3 != null ? email3.hashCode() : 0);
        result = 31 * result + (secondaryAddress != null ? secondaryAddress.hashCode() : 0);
        result = 31 * result + (homepage != null ? homepage.hashCode() : 0);
        result = 31 * result + (homePhone2 != null ? homePhone2.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
