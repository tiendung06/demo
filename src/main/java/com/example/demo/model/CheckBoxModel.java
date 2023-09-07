package com.example.demo.model;

import lombok.Getter;

@Getter
public class CheckBoxModel {
    private boolean value1;
    private boolean value2;
    private boolean value3;

    public void setValue1(boolean value1) {
        this.value1 = value1;
    }

    public void setValue2(boolean value2) {
        this.value2 = value2;
    }

    public void setValue3(boolean value3) {
        this.value3 = value3;
    }
}
