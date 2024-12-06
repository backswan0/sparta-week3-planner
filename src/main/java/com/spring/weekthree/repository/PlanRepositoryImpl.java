package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Create 완료
 * Read 진행 중 (목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 *
 */

@Repository
public class PlanRepositoryImpl implements PlanRepository {
    // [1] 속성
    private final Map<Long, Plan> planList = new HashMap<>();

    // [2] 생성자

    // [3] 기능
    @Override
    public Plan save(Plan plan) {
        Long planId = planList.isEmpty() ? 1 : Collections.max(planList.keySet()) + 1;

        plan.setId(planId);

        planList.put(planId, plan);

        return plan;
    }

    @Override
    public List<PlanResponseDto> pullAllAsList() {
        List<PlanResponseDto> allPlans = new ArrayList<>();

        for (Plan plan : planList.values()) {
            PlanResponseDto responseDto = new PlanResponseDto(plan);
            allPlans.add(responseDto);
        }

        return allPlans;
    }

    @Override
    public Plan pullEachById(Long id) {
        return planList.get(id);
    }
}