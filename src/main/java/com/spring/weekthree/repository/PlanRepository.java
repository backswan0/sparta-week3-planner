package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;

import java.util.List;

/**
 * Create 완료
 * Read 진행 중 (목록 조회)
 * Read 완료 (단건 조회)
 *
 *
 */

public interface PlanRepository {
    Plan save(Plan plan);

    List<PlanResponseDto> pullAllAsList();

    Plan pullEachById (Long id);
}