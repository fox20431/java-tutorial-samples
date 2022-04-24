package com.hihusky.reflectiondemo.entity;


public class Base {
    private int value;

    public Base() {
    }

    public Base(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Base{" +
                "value=" + value +
                '}';
    }
}
