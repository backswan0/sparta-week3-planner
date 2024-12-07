package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;

import java.time.LocalDate;
import java.util.List;

public class JdbcTemplatePlanRepository implements PlanRepository{

    @Override
    public PlanResponseDto save(Plan plan) {
        return null;
    }

    @Override
    public List<PlanResponseDto> fetchAllPlans(String name, LocalDate updatedDate) {
        return List.of();
    }

    @Override
    public Plan fetchPlanById(Long id) {
        return null;
    }

    @Override
    public void deletePlan(Long id) {

    }
}
