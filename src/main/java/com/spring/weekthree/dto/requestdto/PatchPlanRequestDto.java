package com.spring.weekthree.dto.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class PatchPlanRequestDto {
    // 속성
    private String name;
    private String password;
    private LocalDate plannedDate;
    private String title;
    private String task;

    // 생성자

    // 기능
}