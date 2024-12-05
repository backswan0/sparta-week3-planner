package com.spring.weekthree.repository;

import com.spring.weekthree.entity.Plan;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PlanRepositoryImpl implements PlanRepository {
    // 속성
    private final Map<Long, Plan> planList = new HashMap<>();

    @Override
    public Plan savePlan(Plan plan) {
        Long planId = planList.isEmpty() ? 1 : Collections.max(planList.keySet()) + 1;

        plan.setId(planId);

        planList.put(planId, plan);

        return plan;
    }

    // 생성자

    // 기능
}
