package com.spring.weekthree.dto.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CreatePlanRequestDto {
    // 속성
    private Long memberId;
    // 멤버 아이디
    private String password;
    private LocalDate plannedDate;
    private String title;
    private String task;

    // 생성자

    // 기능
}