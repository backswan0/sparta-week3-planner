package com.spring.weekthree.repository.member;

import com.spring.weekthree.entity.Member;

// 사용자 리포지토리 레이어 인터페이스
public interface MemberRepository {

    // [1/2] 사용자 조회
    Member fetchMemberByIdOrElseThrow(Long memberId);

    // [2/2] 사용자명 수정
    int updateMemberName(Long memberId, String name);
}