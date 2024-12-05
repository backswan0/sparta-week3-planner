package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;

import java.util.List;

/**
 * Create 완료
 * Read 완료 (목록 조회)
 *
 *
 *
 */

public interface PlanRepository {
    Plan saveEachPlan(Plan plan);

    List<PlanResponseDto> pullAllPlans();
}