package com.spring.weekthree.repository;

import com.spring.weekthree.dto.responsedto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * [리팩토링 완료]
 * 수정이 바로 안 되는 점 해결
 */

public interface PlanRepository {
    PlanResponseDto save(Plan plan);

    List<PlanResponseDto> fetchAllPlans(
            String name,
            LocalDate updatedDate
    );

    Plan fetchPlanById0rElseThrow(Long id);

    int updatePatchInRepository(
            Long id,
            String name,
            LocalDate plannedDate,
            String title,
            String task,
            LocalDateTime updatedDateTime
    );

    void deletePlan(Long id);
}