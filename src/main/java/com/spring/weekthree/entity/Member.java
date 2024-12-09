package com.spring.weekthree.entity;

import com.spring.weekthree.util.TimeUtil;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 도전 과제 C 완료
 * 도전 과제 R 전체 조회 완료
 * 도전 과제 R 단건 조회 리팩토링 완료
 * 도전 과제 U 초안 완료
 * 도전 과제 D 완료
 */

@Getter
public class Member {
    private Long memberId;
    private String name;
    private String email;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public Member(Long memberId, String name, String email, LocalDateTime createdDateTime, LocalDateTime updatedDateTime) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public void updateName(
            String name
    ) {
        if (!this.name.equals(name)) {
            this.name = name;
            this.updatedDateTime = TimeUtil.now();
        }
    }
}