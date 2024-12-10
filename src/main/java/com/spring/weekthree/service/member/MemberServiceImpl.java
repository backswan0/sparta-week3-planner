package com.spring.weekthree.service.member;

import com.spring.weekthree.dto.member.response.MemberResponseDto;
import com.spring.weekthree.entity.Member;
import com.spring.weekthree.repository.member.MemberRepository;
import com.spring.weekthree.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberServiceImpl implements MemberService{
    // 속성
    private final MemberRepository memberRepository;


    // 생성자
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

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
