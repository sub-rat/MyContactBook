package com.subratgyawali.iii.mycontact;

import java.io.Serializable;

/**
 * Created by Subrat Gyawali on 11/26/16.
 */

public class Contact implements Serializable {
    private String name,email,phone;
    private int _id;

    public Contact() {
    }

    public Contact(int _id,String name, String phone, String email) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this._id = _id;
    }

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Contact(String name) {
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

