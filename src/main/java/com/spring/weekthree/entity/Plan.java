package com.spring.weekthree.entity;

import com.spring.weekthree.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

// 일정 엔티티
@AllArgsConstructor
@Getter
public class Plan {
    // 속성
    private Long planId;
    private Long memberId;
    private String password;
    private LocalDate plannedDate;
    private String title;
    private String task;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    /**
     * 생성자
     *
     * @param password    : 사용자 비밀번호
     * @param plannedDate : 사용자가 입력한 일정 날짜
     * @param title       : 사용자가 입력한 일정의 제목
     * @param task        : 사용자가 입력한 일정의 상세 정보
     */
    public Plan(
            Long memberId,
            String password,
            LocalDate plannedDate,
            String title,
            String task
    ) {
        this.memberId = memberId;
        this.password = password;
        this.plannedDate = plannedDate;
        this.title = title;
        this.task = task;

        this.createdDateTime = TimeUtil.now();
        this.updatedDateTime = TimeUtil.now();
    }

    // 기능 [1/2] 일정 수정
    public void updatePlan(
            LocalDate plannedDate,
            String title,
            String task,
            LocalDateTime updatedDateTime
    ) {
        this.plannedDate = plannedDate;
        this.title = title;
        this.task = task;

        this.updatedDateTime = updatedDateTime;
    }

    // 기능 [2/2] 비밀번호 인증
    public void validatePassword(String password) {
        if (!Objects.equals(password, this.password))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match"
            );
    }
}