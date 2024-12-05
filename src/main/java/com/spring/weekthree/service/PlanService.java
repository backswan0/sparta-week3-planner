package com.spring.weekthree.service;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;

import java.util.List;

/**
 * Create 완료
 * Read 완료 (목록 조회)
 *
 *
 *
 */

public interface PlanService {
    PlanResponseDto processSaveInService(PlanRequestDto requestDto);

    List<PlanResponseDto> processViewService();
}