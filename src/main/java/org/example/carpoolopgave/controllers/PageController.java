package org.example.carpoolopgave.controllers;

import org.example.carpoolopgave.entities.Trip;
import org.example.carpoolopgave.services.TripService;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.example.carpoolopgave.entities.User;
import org.example.carpoolopgave.managers.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    private TripService tripService;

    public PageController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/book")
    public String book() {
        return "bookTrip";
    }

    @GetMapping("/menu")
            public String menu(){
        return "menu";
    }

    @GetMapping("/createTrip")
    public String showCreateTripForm(HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        return "createTrip";
    }

    @PostMapping("/createTrip")
    public String createTrip(@RequestParam String from,
                             @RequestParam String to,
                             @RequestParam String date,
                             @RequestParam String time,
                             @RequestParam int seats,
                             HttpSession session,
                             Model model){
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if(loggedInUser == null){
            return "redirect:/login";
        }

        UserManager userManager = new UserManager();

        if (!userManager.isAdult(loggedInUser)){
            model.addAttribute("error", "Du skal være 18 eller over for at oprette en tur");
            return "createTrip";
        }

        int newId = tripService.getNextTripId();

        Trip createdTrip = new Trip(
                newId, loggedInUser.getUserName(), from, to, date, time, seats
        );

        tripService.saveTrip(createdTrip);
return "createTrip";

    }
}


