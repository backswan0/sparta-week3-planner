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
 * Read 진행 중 (목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
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
        PlanResponseDto responseDto = planService.processSave(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PlanResponseDto>> readAllPlans() {
        List<PlanResponseDto> allPlans = planService.processPullList();

        return new ResponseEntity<>(allPlans, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDto> readPlanById(@PathVariable Long id) {
        PlanResponseDto responseDto = planService.processPullEach(id);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlanResponseDto> editPatch(
            @PathVariable Long id,
            @RequestBody PlanRequestDto requestDto
    ) {
        PlanResponseDto responseDto = planService.processEditPatch(
                id,
                requestDto.getName(),
                requestDto.getPassword(),
                requestDto.getPlannedDate(),
                requestDto.getTitle(),
                requestDto.getTask()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
