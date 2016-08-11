package com.oadex.authenticatedUsers.student;

import java.util.ArrayList;

/**
 * Created by Dr FatBoySlymPG on 8/8/2016.
 */
public class UnderGraduate extends Student {
    public UnderGraduate(String token, String matricNo) {
        super(token, matricNo);
    }

    @Override
    public void checkResult(String token, String matricNo, String Session) {

    }

    @Override
    public void Registration(String token, String matricNo, String Session) {

    }

    @Override
    public String getUserName() {
        return super.getUserName();
    }

    @Override
    public String getUserAddress() {
        return null;
    }

    @Override
    public String getHomeAddress() {
        return super.getHomeAddress();
    }

    @Override
    public ArrayList<String> getSupervisors() {
        return null;
    }
}
