package com.spring.weekthree.controller;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Create 완료
 * Read 완료 (목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
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
        PlanResponseDto responseDto = planService.processSave(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PlanResponseDto>> readAllPlans(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate updatedDate
            // 두 값은 필수가 아니게 설정 (boolean required() docs 참고)
    ) {
        List<PlanResponseDto> allPlans = planService.processPullList(name, updatedDate);

        return new ResponseEntity<>(allPlans, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDto> readPlanById(@PathVariable Long id) {
        PlanResponseDto responseDto = planService.processPullEach(id);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlanResponseDto> updatePlanPatch(
            @PathVariable Long id,
            @RequestBody PlanRequestDto requestDto
    ) {
        PlanResponseDto responseDto = planService.processUpdatePatch(
                id,
                requestDto.getName(),
                requestDto.getPassword(),
                requestDto.getPlannedDate(),
                requestDto.getTitle(),
                requestDto.getTask()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan (
            @PathVariable Long id, @RequestParam String password
    ) {
        planService.processDelete(id, password);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}