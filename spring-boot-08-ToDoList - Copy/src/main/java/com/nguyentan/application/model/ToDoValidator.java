package com.nguyentan.application.model;

import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

public class ToDoValidator {

    public boolean isValid(ToDo toDo) {

        return Optional.ofNullable(toDo)
                .filter(t -> !StringUtils.isEmpty(t.getTitle()))
                .filter(t -> t.getDeadline() != null && !t.getDeadline().isBefore(LocalDate.now()))
                .isPresent();
    }
}
