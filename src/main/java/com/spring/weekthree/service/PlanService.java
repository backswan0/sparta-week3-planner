package com.spring.weekthree.service;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;

import java.util.List;

/**
 * Create 완료
 * Read 진행 중 (목록 조회)
 * Read 완료 (단건 조회)
 *
 *
 */

public interface PlanService {
    PlanResponseDto processSave(PlanRequestDto requestDto);

    List<PlanResponseDto> processPullList();

    PlanResponseDto processPullEach (Long id);
}