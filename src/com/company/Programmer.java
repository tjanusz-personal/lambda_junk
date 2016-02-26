package com.company;

import java.util.Optional;

public class Programmer {
    private String firstName, lastName, job, gender;
    private int salary, age;
    private Optional<Address> address = Optional.empty();

    public boolean isHighlyCompensated() {
        return highlyCompensated;
    }

    public void setHighlyCompensated(boolean highlyCompensated) {
        this.highlyCompensated = highlyCompensated;
    }

    private boolean highlyCompensated;

    public Programmer() { }

    public Programmer(String firstName, String lastName, String job, String gender, int age, int salary)       {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.job = job;
        this.salary = salary;
    }

    public void determineHighlyCompensated() {
        if (salary >= 50000) {
            this.setHighlyCompensated(true);
        } else {
            this.setHighlyCompensated(false);
        }
    }
    public Optional<Address> getAddress() { return address; }
    public void setAddress(Optional<Address> address) { this.address = address; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
