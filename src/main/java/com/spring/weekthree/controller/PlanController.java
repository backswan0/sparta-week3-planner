package com.spring.weekthree.controller;

import com.spring.weekthree.dto.plan.request.CreatePlanRequestDto;
import com.spring.weekthree.dto.plan.request.PatchPlanRequestDto;
import com.spring.weekthree.dto.plan.response.PlanResponseDto;
import com.spring.weekthree.service.plan.PlanService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {
    // 속성
    private final PlanService planService;

    // 생성자
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    /**
     * 기능
     * [1/5] CREATE - 일정 생성
     *
     * @param requestDto : CreatePlanRequestDto
     * @return PlanResponseDto, HttpStatus 200 OK
     */
    @PostMapping
    public ResponseEntity<PlanResponseDto> createPlan(
            @RequestBody CreatePlanRequestDto requestDto
    ) {
        PlanResponseDto responseDto;

        responseDto = planService.processSave(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * 기능
     * [2/5] GET - 일정 목록 조회
     *
     * @param memberId    : 사용자의 id로, 필수 단계의 사용자명 대신 사용
     * @param updatedDate : 일정이 수정된 날
     * @return List<PlanResponseDto>, HttpStatus 200 OK
     */
    @GetMapping
    public ResponseEntity<List<PlanResponseDto>> readAllPlans(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) LocalDate updatedDate,
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size
            /*
            스프링에서 제공하는 인터페이스라,
            @RequestParam(required = false)을 명시하면 오류가 난다...? 왜????
            @PageableDefault 어노테이션이 따로 있다.
            만약 requestparam으로 받으려면 아래와 같이 해야 한다...!

             */
    ) {
        Pageable pageable = PageRequest.of(page, size);

        List<PlanResponseDto> allPlans;

//        System.out.println(pageable.getOffset());
//
//        System.out.println(pageable.getPageSize());

        allPlans = planService.processFetchList(
                memberId,
                updatedDate
        );

        return new ResponseEntity<>(allPlans, HttpStatus.OK);
    }

    /**
     * 기능
     * [3/5] GET - 일정 단건 조회
     *
     * @param planId : 조회하려는 단건 일정의 id
     * @return PlanResponseDto, HttpStatus 200 OK
     */
    @GetMapping("/{planId}")
    public ResponseEntity<PlanResponseDto> readPlanByPlanId(
            @PathVariable Long planId
    ) {
        PlanResponseDto responseDto;

        responseDto = planService.processFetchEach(planId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 기능
     * [4/5] UPDATE (PATCH) - 일정 수정 (일정 날짜, 일정 제목, 일정 내용만)
     *
     * @param planId     : 수정하려는 일정의 id
     * @param requestDto : PatchPlanRequestDto, 인증에 필요한 password 포함
     * @return PlanResponseDto, HttpStatus 200 OK
     */
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

    /**
     * 기능
     * [5/5] DELETE - 일정 삭제
     *
     * @param planId    : 수정하려는 일정의 id
     * @param password  : 인증에 필요한 password
     * @return HttpStatus 200 OK
     */
    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(
            @PathVariable Long planId,
            @RequestParam String password
    ) {
        planService.processDelete(planId, password);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}