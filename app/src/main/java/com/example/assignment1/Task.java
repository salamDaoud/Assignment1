package com.example.assignment1;

public class Task {

    String name;
    Boolean check;

    public Task(String name, Boolean check) {
        this.name = name;
        this.check = check;
    }

    String getName(){
        return name;
    }

    Boolean getCheck(){
        return check;
    }
}
