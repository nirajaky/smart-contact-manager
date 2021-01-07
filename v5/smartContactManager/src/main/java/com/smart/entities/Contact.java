package com.smart.entities;

import com.smart.entities.User;

import javax.persistence.*;

@Entity
@Table(name = "CONTACT")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cId;
    private String firstName;
    private String secondName;
    private String phone;
    private String nickName;
    private String work;
    private String email;
    private String image;
    @Column(length = 1000)
    private String description;

    @ManyToOne
    private User user;

    public Contact() {
        super();
    }

    public Contact(int cId, String firstName, String secondName, String phone, String nickName, String work, String email, String image, String description, User user) {
        this.cId = cId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.nickName = nickName;
        this.work = work;
        this.email = email;
        this.image = image;
        this.description = description;
        this.user = user;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    @Override
//    public String toString() {
//        return "Contact{" +
//                "cId=" + cId +
//                ", firstName='" + firstName + '\'' +
//                ", secondName='" + secondName + '\'' +
//                ", phone='" + phone + '\'' +
//                ", nickName='" + nickName + '\'' +
//                ", work='" + work + '\'' +
//                ", email='" + email + '\'' +
//                ", image='" + image + '\'' +
//                ", description='" + description + '\'' +
//                ", user=" + user +
//                '}';
//    }
}
