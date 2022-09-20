package com.owasptesting.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="User", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NonNull
    @Column(name = "user_name")
    private String userName;
    @NonNull
    @Column(name = "email")
    private String email;
    @NonNull
    @Column(name = "mobile_number")
    private String mobileNumber;
    @NonNull
    @Column(name = "password")
    private String passWord;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public User() {
    }
    public User(int id, String userName, String email, String mobileNumber, String passWord) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.passWord = passWord;
    }
    public User(String userName, String email, String mobileNumber, String passWord) {
        this.userName = userName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.passWord = passWord;
    }


}