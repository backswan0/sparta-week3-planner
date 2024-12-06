package com.spring.weekthree.service;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Create 완료
 * Read 진행 중 (목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 *
 */

public interface PlanService {
    PlanResponseDto processSave(PlanRequestDto requestDto);

    List<PlanResponseDto> processPullList();

    PlanResponseDto processPullEach(Long id);

    PlanResponseDto processEditPatch(
            Long id,
            String name,
            String password,
            LocalDateTime plannedDate,
            String title,
            String task
    );
}