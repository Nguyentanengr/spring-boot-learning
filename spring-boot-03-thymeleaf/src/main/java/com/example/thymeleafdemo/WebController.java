package com.example.thymeleafdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    @GetMapping("/profile")
    public String profile(Model model) {

        List<Info> profiles = new ArrayList<>();
        profiles.add(new Info("fullname", "Tan Nguyen"));
        profiles.add(new Info("nickname", "Loda"));
        profiles.add(new Info("gmail", "loda.namnh@gmail.com"));
        profiles.add(new Info("facebook", "https://www.facebook.com/nam.tehee"));
        profiles.add(new Info("website", "https://loda.me"));

        model.addAttribute("profile", profiles);

        return "profile";
    }

    @GetMapping("/task")
    public String task(Model model) {
        return "task";
    }
}
