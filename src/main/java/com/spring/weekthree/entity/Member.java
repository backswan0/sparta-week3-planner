package com.spring.weekthree.entity;

import com.spring.weekthree.util.TimeUtil;
import lombok.Getter;

import java.time.LocalDateTime;

// 사용자 엔티티
@Getter
public class Member {
    // 속성
    private Long memberId;
    private String name;
    private String email;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    // 생성자
    public Member(
            Long memberId,
            String name,
            String email,
            LocalDateTime createdDateTime,
            LocalDateTime updatedDateTime
    ) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    // 기능
    public void updateName(
            String name
    ) {
        if (!this.name.equals(name)) {
            this.name = name;
            this.updatedDateTime = TimeUtil.now();
        }
        // 기존 이름과 수정하려는 이름이 다를 때만 수정
    }
}