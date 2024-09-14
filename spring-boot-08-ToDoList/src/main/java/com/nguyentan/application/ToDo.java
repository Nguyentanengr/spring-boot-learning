package com.nguyentan.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDo {
    private String title;
    private String description;
    private LocalDate dealine;
}
