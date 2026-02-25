package org.example.carpoolopgave.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/book")
    public String book() {
        return "bookTrip";
    }

    @GetMapping("/create")
    public String create() {
        return "createTrip";
    }
}