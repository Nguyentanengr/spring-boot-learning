package com.nguyentan.application;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListToDo {

    private List<ToDo> listToDo;

    public ListToDo() {
        System.out.println("new list to do !");
        listToDo = new ArrayList<>();
        listToDo.add(new ToDo("Clean", "Clean the house", LocalDate.parse("2024-08-27")));
        listToDo.add(new ToDo("Work", "Work at company", LocalDate.parse("2024-02-12")));
        listToDo.add(new ToDo("Plant", "Plant some trees in the garden", LocalDate.parse("2024-05-16")));
        listToDo.add(new ToDo("Teach", "Teaching for poor children", LocalDate.parse("2024-03-22")));
        listToDo.add(new ToDo("Travel", "Travel to Japan", LocalDate.parse("2024-12-12")));
        listToDo.add(new ToDo("Exercise", "Go to the gym", LocalDate.parse("2024-01-10")));
        listToDo.add(new ToDo("Shop", "Buy groceries", LocalDate.parse("2024-04-05")));
        listToDo.add(new ToDo("Read", "Finish reading a book", LocalDate.parse("2024-06-21")));
        listToDo.add(new ToDo("Cook", "Prepare a special dinner", LocalDate.parse("2024-07-15")));
        listToDo.add(new ToDo("Visit", "Visit grandparents", LocalDate.parse("2024-09-30")));
        listToDo.add(new ToDo("Study", "Complete Java assignment", LocalDate.parse("2024-02-20")));
        listToDo.add(new ToDo("Meditate", "Attend a meditation class", LocalDate.parse("2024-03-10")));
        listToDo.add(new ToDo("Volunteer", "Help at the local animal shelter", LocalDate.parse("2024-04-18")));
        listToDo.add(new ToDo("Repair", "Fix the kitchen sink", LocalDate.parse("2024-05-05")));
        listToDo.add(new ToDo("Organize", "Organize a family reunion", LocalDate.parse("2024-06-25")));
        listToDo.add(new ToDo("Concert", "Attend a music concert", LocalDate.parse("2024-07-30")));
        listToDo.add(new ToDo("Beach", "Day trip to the beach", LocalDate.parse("2024-08-22")));
        listToDo.add(new ToDo("Movie", "Watch a new movie release", LocalDate.parse("2024-09-12")));
        listToDo.add(new ToDo("Hike", "Go hiking in the mountains", LocalDate.parse("2024-10-19")));
    }

    public List<ToDo> getListToDo() {
        return listToDo;
    }

    public void addToDo(ToDo toDo) {
        listToDo.add(toDo);
    }
}
