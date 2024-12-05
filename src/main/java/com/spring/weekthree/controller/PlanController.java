package com.spring.weekthree.controller;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
public class PlanController {
    // 속성
    private final PlanService planService;

    // 생성자
    public PlanController(PlanService planService) {

        this.planService = planService;
    }

    // 기능
    @PostMapping
    public ResponseEntity<PlanResponseDto> createPlan(@RequestBody PlanRequestDto requestDto) {

        PlanResponseDto responseDto = planService.savePlan(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
