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

import java.util.List;


@Controller
public class PageController {
    private TripService tripService;

    public PageController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/book")
    public String book() {
        return "bookTrip";
    }

    @PostMapping("/book")
    public String filterTrips(HttpSession session, Model model,
                              @RequestParam(required = false) String from,
                              @RequestParam(required = false) String to,
                              @RequestParam(required = false) String date,
                              @RequestParam(required = false) Integer seats) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) return "redirect:/login";

        List<Trip> allTrips = tripService.getAllTrips();

        List<Trip> filteredTrips = allTrips.stream()
                .filter(trip -> (from == null || from.isEmpty() ||
                        (trip.getStartDestination() != null && trip.getStartDestination().equalsIgnoreCase(from)))
                        && (to == null || to.isEmpty() ||
                        (trip.getEndDestination() != null && trip.getEndDestination().equalsIgnoreCase(to)))
                        && (date == null || date.isEmpty() ||
                        (trip.getDate() != null && trip.getDate().equalsIgnoreCase(date))))
                .toList();

        if (filteredTrips.isEmpty() && from != null && !from.isEmpty()) {
            filteredTrips = allTrips.stream()
                    .filter(trip -> trip.getStartDestination().equalsIgnoreCase(from) ||
                            trip.getEndDestination().equalsIgnoreCase(to))
                    .toList();
        }

        if (seats == null) seats = 1;

        model.addAttribute("trips", filteredTrips);
        model.addAttribute("seats", seats);
        return "bookTrip";
    }

    @PostMapping("/bookTrips")
    public String bookTrips(@RequestParam List<Integer> tripIds,
                            @RequestParam(required = false) Integer seats,
                            HttpSession session,
                            Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) return "redirect:/login";

        if (seats == null) seats = 1;
        if (seats > 12) seats = 12;

        List<Trip> allTrips = tripService.getAllTrips();

        for (Integer id : tripIds) {
            Trip trip = tripService.getTripById(id);
            if (trip.getSeats() >= seats) {
                trip.setSeats(trip.getSeats() - seats);
                tripService.saveBooking(loggedInUser.getUserName(), trip);
            } else {
                model.addAttribute("error", "Ikke nok pladser på tur " + trip.getTripId());
                return "bookTrip";
            }
        }

        tripService.updateTripsFile(allTrips);
        model.addAttribute("success", "Turen(e) blev booket!");
        return "bookTrip";
    }


    @GetMapping("/createTrip")
    public String showCreateTripForm(HttpSession session) {
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
                             Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        UserManager userManager = new UserManager();

        if (!userManager.isAdult(loggedInUser)) {
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


