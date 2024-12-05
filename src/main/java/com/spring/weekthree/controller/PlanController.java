package com.spring.weekthree.controller;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create 완료
 * Read 완료 (목록 조회)
 *
 *
 *
 */

@RestController
@RequestMapping("/plans")
public class PlanController {
    // [1] 속성
    private final PlanService planService;

    // [2] 생성자
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    // [3] 기능
    @PostMapping
    public ResponseEntity<PlanResponseDto> createPlan(@RequestBody PlanRequestDto requestDto) {
        PlanResponseDto responseDto = planService.processSaveInService(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PlanResponseDto>> readAllPlans() {
        List<PlanResponseDto> allPlans = planService.processViewService();

        return new ResponseEntity<>(allPlans, HttpStatus.OK);
    }
}
