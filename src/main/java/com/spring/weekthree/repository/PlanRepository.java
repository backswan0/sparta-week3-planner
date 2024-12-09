package com.spring.weekthree.repository;

import com.spring.weekthree.dto.responsedto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PlanRepository {
    Plan save(Plan plan);

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
            LocalDateTime updatedDatetime
    );

    void deletePlan(Long id);
}