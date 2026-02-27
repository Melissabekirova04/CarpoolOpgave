package org.example.carpoolopgave.entities;

public class User {
    private String userName;
    private String password;
    private int ageOfUser;

    public User(String userName, String password, int ageOfUser) {
        this.userName = userName;
        this.password = password;
        this.ageOfUser = ageOfUser;

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

    public int getAgeOfUser() {
        return ageOfUser;
    }

    public void setAgeOfUser(int ageOfUser) {
        this.ageOfUser = ageOfUser;
    }
}
