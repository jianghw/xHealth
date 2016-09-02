package com.example.commonlibrary.widget.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nick on 21/04/2016.
 */
public class UserBean implements Serializable {
    public int userId;
    public String userName;
    public String password;
    public String email;
    public String userType;
    public String txtVerified;
    public String firstName;
    public String lastName;
    public String fullName;
    public String gender;
    public Date dateOfBirth;
    public String registerType;
    public String kauriHealthId;
    public boolean allowUnknownMessage;
    public String avatar;
    public double totalCredit;
    public double availableCredit;
    public double registPercentage;
}
