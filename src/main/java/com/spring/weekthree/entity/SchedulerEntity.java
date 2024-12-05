package com.spring.weekthree.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Scheduler {

    private Long id;
    private String name;
    private String password;
    private String plannedDate;
    private String title;
    private String task;

    public Scheduler(
            String name,
            String password,
            String plannedDate,
            String title,
            String task
    ) {
        this.name = name;
        this.password = password;
        this.plannedDate = plannedDate;
        this.title = title;
        this.task = task;
    }
}
