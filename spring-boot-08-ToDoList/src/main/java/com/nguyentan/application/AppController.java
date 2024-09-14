package com.nguyentan.application;

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
    private ListToDo listToDo;

    @GetMapping("/index")
    public String page(Model model) {
        List<ToDo> sortedList = new ArrayList<>(listToDo.getListToDo()); // copy list
        sortedList.sort((o1, o2) -> o1.getDealine().compareTo(o2.getDealine()));
        model.addAttribute("list", sortedList);
        return "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("toDo", new ToDo());

        List<ToDo> sortedList = new ArrayList<>(listToDo.getListToDo()); // copy list
        sortedList.sort((o1, o2) -> o1.getDealine().compareTo(o2.getDealine()));
        model.addAttribute("list", sortedList.subList(sortedList.size() - 10, sortedList.size()));
        System.out.println("check2");
        return "add";
    }

    @PostMapping("/add")
    public void addToDo(@ModelAttribute ToDo toDo, Model model) {
        List<ToDo> sortedList = new ArrayList<>(listToDo.getListToDo()); // copy list
        sortedList.sort((o1, o2) -> o1.getDealine().compareTo(o2.getDealine()));
        listToDo.addToDo(toDo);
        sortedList.add(toDo);

        model.addAttribute("toDo", new ToDo());
        model.addAttribute("list", sortedList.subList(sortedList.size() - 10, sortedList.size()));

        System.out.println("check");
    }

    @GetMapping("/dealine")
    public String dealine(Model model) {
        List<ToDo> sortedList = new ArrayList<>(listToDo.getListToDo()); // copy list
        sortedList.sort((o1, o2)-> o1.getDealine().compareTo(o2.getDealine()));

        model.addAttribute("list", sortedList.subList(0, 5));
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
