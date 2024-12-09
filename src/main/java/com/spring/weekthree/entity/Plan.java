package com.spring.weekthree.entity;

import com.spring.weekthree.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 도전 과제 C 완료
 * 도전 과제 R 전체 조회 완료
 * 도전 과제 R 단건 조회 리팩토링 완료
 * 도전 과제 U 초안 완료
 * 도전 과제 D 완료
 */

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

    public void update (
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

    public void validatePassword(String password) {
        if (!Objects.equals(password, this.password))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match"
            );
    }
}