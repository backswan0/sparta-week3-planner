package com.spring.weekthree.service.member;

import com.spring.weekthree.dto.member.response.MemberResponseDto;
import com.spring.weekthree.entity.Member;
import com.spring.weekthree.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

// 사용자 서비스 레이어
@Service
public class MemberServiceImpl implements MemberService {
    // 속성
    private final MemberRepository memberRepository;

    // 생성자
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 기능
     * [1/1] 사용자명 수정
     *
     * @param memberId : 이름을 수정하려는 사용자의 id
     * @param name     : 수정하려는 사용자명
     * @return MemberResponseDto
     */
    @Override
    public MemberResponseDto processUpdateName(
            Long memberId,
            String name
    ) {
        Member member;

        member = memberRepository.fetchMemberByIdOrElseThrow(memberId);

        member.updateName(name);

        memberRepository.updateMemberName(
                memberId,
                name
        );

        return new MemberResponseDto(member);
    }
}