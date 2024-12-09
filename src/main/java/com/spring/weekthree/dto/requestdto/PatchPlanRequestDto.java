package com.spring.weekthree.dto.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * [리팩토링 완료]
 * 수정이 바로 안 되는 점 해결
 */

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