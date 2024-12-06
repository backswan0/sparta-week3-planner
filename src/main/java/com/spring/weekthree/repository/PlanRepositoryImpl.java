package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

/**
 * Create 완료
 * Read 리팩토링 1차 완료 (if문 대신 스트림 사용, 목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
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
    public List<PlanResponseDto> fetchAllPlans(String name, LocalDate updatedDate) {
        Stream<PlanResponseDto> allPlans;

        allPlans = planList.values().stream().map(PlanResponseDto::new);
        /*
        [수정 전]
        allPlans = planList.values().stream().map(x -> new PlanResponseDto(x));
        TODO 스트림을 제대로 공부하지 않았기 때문에 추후에 ::를 비롯하여 스트림을 제대로 공부해야겠다.
         우선 자바가 객체 지향 패러다임이라서, 그 안에서 함수형 패러다임을 쓰고자 스트림을 쓴다고 배웠다.
         */

        if (name != null) {
            allPlans = allPlans.filter(x -> x.getName().equals(name)
            );
        }
        if (updatedDate != null) {
            allPlans = allPlans.filter(y -> {
                        return y.getUpdatedDate().toLocalDate().equals(updatedDate);
                    }
            );
            /*
            [수정 전]
            allPlans.filter(y -> y.getUpdatedDate().toLocalDate().equals(updatedDate)
            );
            [수정 근거]
            한 눈에 보이도록 {}와 return을 사용하고 맨 끝에 세미콜론(;)을 붙임
             */
        }
        return allPlans.toList();
    }

    @Override
    public Plan fetchPlanById(Long id) {
        return planList.get(id);
    }

    @Override
    public void deletePlan(Long id) {
        planList.remove(id);
    }
}