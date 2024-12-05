package com.spring.weekthree.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Plan {
    // 속성
    @Setter
    private Long id;
    private String name;
    private String password;
    private String plannedDate;
    private String title;
    private String task;

    // 생성자
    public Plan(
            String name,
            String password,
            String plannedDate,
            String title,
            String task) {

        this.name = name;
        this.password = password;
        this.plannedDate = plannedDate;
        this.title = title;
        this.task = task;
    }
}
