package com.example.inclass10;

import java.io.Serializable;

/**
 * Created by Santhosh Reddy Peesu on 10/31/2016.
 */
public class User implements Serializable {
    String fullname,userpassword;


    public User(String fullname, String userpassword) {
        this.fullname=fullname;
        this.userpassword=userpassword;
    }
    public User(){

    }
    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String password) {
        this.fullname = fullname;
    }

}
