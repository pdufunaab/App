package com.staaworks.search;

/**
 * Created by ADEKOYA759 on 01-Sep-16
 */
public class Person {

    private String name = "unnamed";
    private String department = "unset";
    private String phoneNumber = "notSet";
    private String email = "notInserted";


    public Person(String name, String department, String phoneNumber, String email){
        this.name = name;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {


            Person other = (Person) o;

            boolean emailNull = email == null, oEmailNull = other.getEmail() == null,
                    phoneNull = phoneNumber == null,oPhoneNull = other.getPhoneNumber() == null;


            if (!phoneNull && !oPhoneNull) {
                return phoneNumber.equals(other.getPhoneNumber());
            }

            else if (!emailNull && !oEmailNull) {
                return email.equals(other.getEmail());
            }

            else {
                return other.getName().equals(name) && other.getDepartment().equals(department);
            }

        }
        else return false;
    }


    @Override
    public String toString() {
        return "\n\nPerson Details:-\n_____________\nName: " + name + "\nDepartment: " + department + "\nPhone: " + phoneNumber + "\nE-mail: " + email + "\n____________\n\n";
    }
}