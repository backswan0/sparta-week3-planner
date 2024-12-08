package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;

import java.time.LocalDate;
import java.util.List;

public interface PlanRepository {
    PlanResponseDto save(Plan plan);

    List<PlanResponseDto> fetchAllPlans(String name, LocalDate updatedDate);

    Plan fetchPlanById0rElseThrow (Long id);

    void updatePatchInRepository (
            Long id,
            String name,
            LocalDate plannedDate,
            String title,
            String task
    );

    void deletePlan (Long id);
}