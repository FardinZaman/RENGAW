package com.example.RENGAW.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {

    @GetMapping("/rengaw/home")
    public String introLine(){
        return "WE GET DIRTY AND THE WORLD STAYS CLEAN";
    }
}
