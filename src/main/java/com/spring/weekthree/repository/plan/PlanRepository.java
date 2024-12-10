package com.spring.weekthree.repository.plan;

import com.spring.weekthree.entity.Plan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PlanRepository {
    Plan save(Plan plan);

    List<Plan> fetchAllPlans(
            Long memberId,
            LocalDate updatedDate
    );

    Plan fetchPlanById0rElseThrow(Long planId);

    int updatePatchInRepository(
            Long id,
            LocalDate plannedDate,
            String title,
            String task,
            LocalDateTime updatedDatetime
    );

    void deletePlan(Long id);
}