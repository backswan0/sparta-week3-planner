package com.spring.weekthree.repository;

import com.spring.weekthree.entity.Plan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 도전 과제 C 완료
 * 도전 과제 R 전체 조회 완료
 * 도전 과제 R 단건 조회 완료
 *
 *
 */

public interface PlanRepository {
    Plan save(Plan plan);

    List<Plan> fetchAllPlans(
            Long memberId,
            LocalDate updatedDate
    );

    Plan fetchPlanById0rElseThrow(Long id);

    int updatePatchInRepository(
            Long id,
            String name,
            LocalDate plannedDate,
            String title,
            String task,
            LocalDateTime updatedDatetime
    );

    void deletePlan(Long id);
}