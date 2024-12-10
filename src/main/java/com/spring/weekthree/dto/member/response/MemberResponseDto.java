package com.spring.weekthree.dto.member.response;

import com.spring.weekthree.entity.Member;
import lombok.Getter;

// 사용자명 수정 후 응답으로 전달할 response dto
@Getter
public class MemberResponseDto {
    // 속성
    private long memberId;
    private String name;

    // 생성자
    public MemberResponseDto(Member member) {
        this.memberId = member.getMemberId();
        this.name = member.getName();
    }

    // 기능
}