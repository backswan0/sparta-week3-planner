package com.spring.weekthree.controller;

import com.spring.weekthree.dto.member.request.PatchMemberRequestDto;
import com.spring.weekthree.dto.member.response.MemberResponseDto;
import com.spring.weekthree.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    // 속성
    private final MemberService memberService;

    // 생성자
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 기능
     * [1/1] UPDATE (PATCH) - 사용자명 수정
     *
     * @param memberId   : 사용자의 id
     * @param requestDto : MemberRequestDto
     * @return MemberResponseDto, HttpStatus 200 OK
     */
    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateName(
            @PathVariable Long memberId,
            @RequestBody PatchMemberRequestDto requestDto
    ) {
        MemberResponseDto responseDto = memberService.processUpdateName(
                memberId,
                requestDto.getName()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}