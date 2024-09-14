package com.nguyentan.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class ApplicationController {

    List<ToDo> todoList = new CopyOnWriteArrayList<>();

    @GetMapping("/listToDo")
    public String index(Model model, @RequestParam(value = "limit", required = false) Integer limit) {
        model.addAttribute("todoList", limit != null ? todoList.subList(0, limit) : todoList);

        return "listToDo";
    }

    @GetMapping("/addToDo")
    public String addTodo(Model model) {
        model.addAttribute("todo", new ToDo());
        return "addToDo";
    }

    @PostMapping("/addToDo")
    public String addTodo(@ModelAttribute ToDo todo) {
        todoList.add(todo);
        return "success";
    }
}
