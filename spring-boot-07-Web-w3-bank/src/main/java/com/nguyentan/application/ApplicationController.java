package com.nguyentan.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController {

    @GetMapping("/help")
    public String help(Model model
    ) {
        List<Helper> helpers = new ArrayList<>();
        helpers.add(new Helper("Can't buy ticket?", "There are some error when paying or you can't press the pay button!", "/help/buy"));
        helpers.add(new Helper("Can't take back money?", "You would retried money after bought the ticket and that is not!", "/help/take"));
        helpers.add(new Helper("Need to talk to us?", "Have a few questions or you want something to happen that day!", "/help/support"));

        model.addAttribute("helpersList", helpers);

        return "help";
    }
}
