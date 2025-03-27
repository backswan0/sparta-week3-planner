package com.spring.weekthree.service.member;

import com.spring.weekthree.dto.member.response.MemberResponseDto;

// 사용자 서비스 레이어의 인터페이스
public interface MemberService {

    // 사용자명 수정
    MemberResponseDto processUpdateName(
            Long memberId,
            String name
    );

    // 페이지네이션 사용
    MemberResponseDto processFetchMemberById(Long memberId);
}