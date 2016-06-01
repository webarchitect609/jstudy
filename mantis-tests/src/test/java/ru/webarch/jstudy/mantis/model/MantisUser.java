package ru.webarch.jstudy.mantis.model;

import javax.persistence.*;

@Entity
@Table(name = "mantis_user_table")
public class MantisUser {

    @Id
    @Column(name = "id")
    protected int id;

    @Column(name = "username")
    protected String username = "";

    @Column(name = "email")
    protected String email = "";

    @Transient
    protected String password = "";

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void withId(int id) {
        this.id = id;
    }

    public MantisUser withUsername(String username) {
        this.username = username;
        return this;
    }

    public MantisUser withEmail(String email) {
        this.email = email;
        return this;
    }

    public MantisUser withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "MantisUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
