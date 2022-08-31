package com.example.leaveoutsystem;

public class Account {
    int id;
    String firstName;
    String lastName;
    String email;
    int phone;
    String type;
    int leaveDays;

    public Account () {

    }

    public Account (int id, String firstName, String lastName, String email, int phone, String type, int leaveDays) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.leaveDays = leaveDays;
    }
}
