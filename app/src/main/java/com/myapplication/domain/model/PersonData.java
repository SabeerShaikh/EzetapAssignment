package com.myapplication.domain.model;

import java.io.Serializable;

public class PersonData implements Serializable {
    String name;
    String city;
    String phoneNo;

    public PersonData() {
    }

    public PersonData(String name, String city, String phoneNo) {
        this.name = name;
        this.city = city;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
}
