package org.example.carpoolopgave.services;

import org.example.carpoolopgave.entities.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private String url = "users.csv";

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(url))){
            String line;
            bufferedReader.readLine();

            while((line = bufferedReader.readLine()) != null){
                String [] userData = line.split(",");
                users.add(new User(userData[0], userData[1], Integer.parseInt(userData[2])));
            }
        } catch (IOException e){
            System.out.println("Looking for file at: " + new File(url).getAbsolutePath());
            System.out.println("No users to be found");
        }
        return users;
    }

    public void saveUser(User user) {

        boolean fileExists = new File(url).exists();

        try (FileWriter fw = new FileWriter(url, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            if (!fileExists) {
                bw.write("username,password,userAge");
                bw.newLine();
            }

            bw.write(user.getUserName() + "," + user.getPassword() + "," + user.getAgeOfUser());
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User findUserByUserName(String userNameToFind){
        return getAllUsers().stream()
                .filter(user -> user.getUserName().equalsIgnoreCase(userNameToFind))
                .findAny()
                .orElse(null);
    }
}
