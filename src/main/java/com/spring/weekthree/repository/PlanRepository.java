package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;

import java.time.LocalDate;
import java.util.List;

/**
 * Create 완료
 * Read 완료 (목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
 */

public interface PlanRepository {
    Plan save(Plan plan);

    List<PlanResponseDto> pullAllAsList(String name, LocalDate updatedDate);

    Plan pullEachById (Long id);

    void deletePlan (Long id);
}