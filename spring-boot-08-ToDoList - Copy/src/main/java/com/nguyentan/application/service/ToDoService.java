package com.nguyentan.application.service;

import com.nguyentan.application.model.ToDo;
import com.nguyentan.application.model.ToDoValidator;
import com.nguyentan.application.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private ToDoValidator toDoValidator;

    public List<ToDo> findAll(Integer limit) {
        return Optional.ofNullable(limit)
                .map(l -> toDoRepository.findAll(PageRequest.of(0, l, Sort.by("deadline"))).getContent())
                .orElseGet(() -> toDoRepository.findAll(Sort.by("deadline")));
    }

    public ToDo add(ToDo toDo) {
        if (toDoValidator.isValid(toDo)) {
            return toDoRepository.save(toDo);
        }
        return null;
    }
}
