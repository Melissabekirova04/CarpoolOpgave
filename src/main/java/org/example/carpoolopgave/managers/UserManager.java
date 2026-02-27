package org.example.carpoolopgave.managers;

public class UserManager {
    public boolean checkPassword(String savedPassword, String input){
        return savedPassword.equals(input);

    }
}

