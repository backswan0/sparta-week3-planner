package com.spring.weekthree.dto.member.response;

import com.spring.weekthree.entity.Member;
import lombok.Getter;

// 406: 게터가 없어서 생긴 문제. 게터가 없어 직렬화가 안 됨. 즉, 응답을 할 때 json으로 안 된 거..!
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
}