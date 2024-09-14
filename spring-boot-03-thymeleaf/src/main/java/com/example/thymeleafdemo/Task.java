package com.example.thymeleafdemo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {

    String taskID;
    String taskDescription;
}
