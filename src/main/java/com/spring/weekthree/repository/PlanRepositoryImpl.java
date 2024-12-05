package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Create 완료
 * Read 완료 (목록 조회)
 *
 *
 *
 */

@Repository
public class PlanRepositoryImpl implements PlanRepository {
    // [1] 속성
    private final Map<Long, Plan> planList = new HashMap<>();

    // [2] 생성자

    // [3] 기능
    @Override
    public Plan saveEachPlan(Plan plan) {
        Long planId = planList.isEmpty() ? 1 : Collections.max(planList.keySet()) + 1;

        plan.setId(planId);

        planList.put(planId, plan);

        return plan;
    }

    @Override
    public List<PlanResponseDto> pullAllPlans() {
        List<PlanResponseDto> allPlans = new ArrayList<>();

        for (Plan plan : planList.values()) {
            PlanResponseDto responseDto = new PlanResponseDto(plan);
            allPlans.add(responseDto);
        }

        return allPlans;
        // TODO 오버로드한 후에 List.of()로 적혔는지 확인하고 고치자.
    }
}