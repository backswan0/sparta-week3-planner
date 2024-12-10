package com.spring.weekthree.service.member;

import com.spring.weekthree.dto.member.response.MemberResponseDto;

public interface MemberService {

    MemberResponseDto processUpdateName(
            Long memberId,
            String name
    );
}
