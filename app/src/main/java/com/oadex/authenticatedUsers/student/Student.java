package com.oadex.authenticatedUsers.student;

import com.oadex.User;

import java.util.ArrayList;

/**
 * Created by Dr FatBoySlymPG on 8/8/2016.
 */
public abstract class Student implements User {
    private String fullName;
    private String matricNo;
    private String level;
    private String homeAddress;
    private String phoneNo;
    private String emailAddress;
    private String[] supervisors;
    private String option;
    private UserType userType;

    public Student(String token, String matricNo){
        getUserInfo(token, matricNo);
    }

    private void getUserInfo(String token, String matricNo) {
        //TODO: Request User details from server -- method
        this.matricNo = matricNo;
        //TODO: Assign User details
    }

    public String getUserName(){
        return fullName;
    }

    public String getLevel(){
        return level;
    }
    public String getHomeAddress(){
        return homeAddress;
    }

    public String getPhone(){
        return phoneNo;
    }
    public String getEmailAddress(){
        return emailAddress;
    }

    public String getOption(){
        return option;
    }

    public UserType getUserType(){
        return userType;
    }

    public abstract ArrayList<String> getSupervisors();
    public  abstract void checkResult(String token, String matricNo, String Session);
    public abstract void Registration(String token, String matricNo, String Session);
}
