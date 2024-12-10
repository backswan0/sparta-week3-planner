package com.spring.weekthree.controller;

import com.spring.weekthree.dto.plan.request.CreatePlanRequestDto;
import com.spring.weekthree.dto.plan.request.PatchPlanRequestDto;
import com.spring.weekthree.dto.plan.response.PlanResponseDto;
import com.spring.weekthree.service.plan.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 도전 과제 C 완료
 * 도전 과제 R 전체 조회 완료
 * 도전 과제 R 단건 조회 리팩토링 완료
 * 도전 과제 U 초안 완료
 * 도전 과제 D 완료
 */

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
    public ResponseEntity<PlanResponseDto> createPlan(
            @RequestBody CreatePlanRequestDto requestDto
    ) {
        PlanResponseDto responseDto;

        responseDto = planService.processSave(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PlanResponseDto>> readAllPlans(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) LocalDate updatedDate
    ) {
        List<PlanResponseDto> allPlans;

        allPlans = planService.processFetchList(
                memberId,
                updatedDate
        );

        return new ResponseEntity<>(allPlans, HttpStatus.OK);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanResponseDto> readPlanByPlanId(
            @PathVariable Long planId
    ) {
        PlanResponseDto responseDto;

        responseDto = planService.processFetchEach(planId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{planId}")
    public ResponseEntity<PlanResponseDto> updatePatch(
            @PathVariable Long planId,
            @RequestBody PatchPlanRequestDto requestDto
    ) {
        PlanResponseDto responseDto;

        responseDto = planService.processUpdatePatch(
                planId,
                requestDto.getPassword(),
                requestDto.getPlannedDate(),
                requestDto.getTitle(),
                requestDto.getTask()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(
            @PathVariable Long planId,
            @RequestParam String password
    ) {
        planService.processDelete(planId, password);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}