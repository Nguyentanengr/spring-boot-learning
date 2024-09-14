package com.nguyentan.application.controller;

import com.nguyentan.application.model.Helper;
import com.nguyentan.application.model.ToDo;
import com.nguyentan.application.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AppController {

    public AppController() {
        System.out.println("new app controller!");
    }

    @Autowired
    private ToDoService toDoService;

    @GetMapping("/index")
    public String page(Model model) {
        model.addAttribute("list", toDoService.findAll(null));
        return "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("toDo", new ToDo());

        model.addAttribute("list", toDoService.findAll(10));

        System.out.println("check2");
        return "add";
    }

    @PostMapping("/add")
    public void addToDo(@ModelAttribute ToDo toDo, Model model) {
        List<ToDo> sortedList = new ArrayList<>(toDoService.findAll(10));
        if (toDoService.add(toDo) != null) {
            sortedList.add(toDo);
        }

        model.addAttribute("toDo", new ToDo());
        model.addAttribute("list", sortedList.subList(sortedList.size() - 10, sortedList.size()));

    }

    @GetMapping("/dealine")
    public String dealine(Model model) {

        model.addAttribute("list", toDoService.findAll(5));

        return "dealine";
    }

    @GetMapping("/help")
    public String help(Model model) {
        List<Helper> helpers = new ArrayList<>();
        helpers.add(new Helper("Can't add a task?", "Are you encountering an error when adding a new task or can't press the add button?", "/help/add-task"));
        helpers.add(new Helper("Can't delete a task?", "Tried to remove a task but it’s not working?", "/help/delete-task"));
        helpers.add(new Helper("Need to talk to us?", "Have a few questions or need assistance with your to-do list?", "/help/support"));

        model.addAttribute("helpersList", helpers);

        return "help";
    }
}
