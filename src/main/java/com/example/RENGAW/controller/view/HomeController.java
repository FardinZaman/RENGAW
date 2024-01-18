package com.example.RENGAW.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rng")
public class HomeController {

    @GetMapping("/")
    public String viewHomePage(){
        return "homePage";
    }
}
