package com.smart.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String image;
    @Column(length = 500)
    private String about;
    private String role;
    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user") // by using mappedBy="user" : user_contacts table is not created
    private List<Contact> contacts = new ArrayList<Contact>();

    public User(int id, String name, String email, String password, String image, String about, String role, boolean enabled, List<Contact> contacts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.about = about;
        this.role = role;
        this.enabled = enabled;
        this.contacts = contacts;
    }

    public User() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", about='" + about + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", contacts=" + contacts +
                '}';
    }
}
