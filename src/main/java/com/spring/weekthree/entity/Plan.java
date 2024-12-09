package com.spring.weekthree.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * [리팩토링 완료]
 * 수정이 바로 안 되는 점 해결
 */

@AllArgsConstructor
@Getter
public class Plan {
    // 속성
    private Long id;
    private String name;
    private String password;
    private LocalDate plannedDate;
    private String title;
    private String task;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    /**
     * 생성자
     *
     * @param name        : 사용자 이름
     * @param password    : 사용자 비밀번호
     * @param plannedDate : 사용자가 입력한 일정 날짜
     * @param title       : 사용자가 입력한 일정의 제목
     * @param task        : 사용자가 입력한 일정의 상세 정보
     */
    public Plan(
            String name,
            String password,
            LocalDate plannedDate,
            String title,
            String task
    ) {
        this.name = name;
        this.password = password;
        this.plannedDate = plannedDate;
        this.title = title;
        this.task = task;

        this.createdDateTime = LocalDateTime.now();
        this.updatedDateTime = LocalDateTime.now();
    }
    // 기능
    public void update (
            String name,
            LocalDate plannedDate,
            String title,
            String task,
            LocalDateTime updatedDateTime
    ) {
        this.name = name;
        this.plannedDate = plannedDate;
        this.title = title;
        this.task = task;

        this.updatedDateTime = updatedDateTime;

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
         // this.updatedDateTime = LocalDateTime.parse(updatedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//        this.updatedDateTime = LocalDateTime.parse(updatedDateTime.format(dateTimeFormatter));
    }

    public void validatePassword(String password) {
        if (!Objects.equals(password, this.password))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match"
            );
    }
}