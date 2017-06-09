package com.mjb.projectexperts.Domain;

import org.json.JSONObject;

/**
 * Created by mm on 07/06/2017.
 */
public class User {


    private String name;
    private String email;
    private String userName;
    private String password;
    private String idUser;
    private String isAdmin;

    public User(String name, String email, String userName, String password) {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }


    public User(JSONObject user) {
        try {
            this.name = user.getString("fullName");
            this.email = user.getString("email");
            this.userName = user.getString("userName");
            this.password = user.getString("userPassword");
            this.idUser = user.getString("idUser");
            this.isAdmin = user.getString("admin");
        }catch (Exception e){}
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }


}
