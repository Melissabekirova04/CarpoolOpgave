package org.example.carpoolopgave.controllers;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.example.carpoolopgave.entities.User;
import org.example.carpoolopgave.managers.UserManager;
import org.example.carpoolopgave.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class AuthorisationController {
    private UserService userService;

    public AuthorisationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUserIn(@RequestParam String userName,
                              @RequestParam String userInputPassword,
                              HttpSession httpSession,
                              Model userModel) {


        //1. Hent unser
        List<User> allUsers = userService.getAllUsers();
        //2. Hent user password
        User foundUser = allUsers.stream()
                .filter(u -> u.getUserName().equals(userName))
                .findFirst()
                .orElse(null);

        if (foundUser == null) {
            userModel.addAttribute("error", "User not found");
            return "/login";
        }

        //3. Check user password mathcer
        UserManager userManager = new UserManager();
        boolean passwordMatches = userManager.checkPassword(foundUser.getPassword(), userInputPassword);

        if (!passwordMatches) {
            userModel.addAttribute("Error", "incorrect pasword");
            return "/login";
        }
        httpSession.setAttribute("loggedInUser", foundUser);
        return "/menu";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "opretBruger";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String userName,
                               @RequestParam String userPassword,
                               @RequestParam int userAge) {

        List<User> allUsers = userService.getAllUsers();

        User existingUser = allUsers.stream()
                .filter(u -> u.getUserName().equals(userName))
                .findFirst()
                .orElse(null);

        if (existingUser != null) {
            return "/register";
        }

        userService.saveUser(new User(userName, userPassword, userAge));
        return "redirect:/login";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}
