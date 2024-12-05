package com.spring.weekthree.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlanRequestDto {
    // [1] 속성
    private String name;
    private String password;
    private String plannedDate;
    private String title;
    private String task;

    // [2] 생성자

    // [3] 기능
}
