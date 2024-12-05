package com.spring.weekthree.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Plan {
    // [1-a] 속성 중 사용자의 입력값을 저장하는 필드
    @Setter
    private Long id;
    private String name;
    private String password;
    private String plannedDate;
    private String title;
    private String task;
    /*
    TODO plannedDate는 어떤 데이터 타입이 좋을까?
     만약 LocalDateTime이라면 null 문제를 어떻게 해결할 수 있을까?
     */

    // [1-b] 속성 중 사용자의 입력값을 저장하지 않는 필드
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    /**
     * [2] 생성자
     * @param name : 사용자 이름
     * @param password : 사용자 비밀번호
     * @param plannedDate : 사용자가 입력한 일정 날짜
     * @param title : 사용자가 입력한 일정의 제목
     * @param task : 사용자가 입력한 일정의 상세 정보
     */
    public Plan(
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

        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }
    // [3] 기능
}