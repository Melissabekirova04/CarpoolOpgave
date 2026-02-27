package org.example.carpoolopgave.managers;

import org.example.carpoolopgave.entities.User;

public class UserManager {
    public boolean checkPassword(String savedPassword, String input){
        return savedPassword.equals(input);
    }

    public boolean isAdult(User user){
        return user.getAgeOfUser() >= 18;

    }
}

