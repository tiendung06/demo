package com.example.demo.model;

import lombok.Getter;

@Getter
public class PersonModel {
    private String gender;

    public void setGender(String gender) {
        this.gender = gender;
    }
}
