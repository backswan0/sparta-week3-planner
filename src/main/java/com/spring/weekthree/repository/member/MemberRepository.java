package com.spring.weekthree.repository.member;

import com.spring.weekthree.entity.Member;

/**
 * 도전 과제 C 완료
 * 도전 과제 R 전체 조회 완료
 * 도전 과제 R 단건 조회 리팩토링 완료
 * 도전 과제 U 초안 완료
 * 도전 과제 D 완료
 */

public interface MemberRepository {

    // 유저 조회
    Member findMemberById(Long memberId);

    // 유저 정보 업데이트 (update)
    int updateMemberName(Long memberId, String name);
}
