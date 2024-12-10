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
    public MemberController (MemberService memberService) {
        this.memberService = memberService;
    }

    // 기능
    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateName(
            @PathVariable Long memberId,
            @RequestBody PatchMemberRequestDto requestDto
    ) {
        MemberResponseDto responseDto;

        responseDto = memberService.processUpdateName(
                memberId,
                requestDto.getName()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}