package com.spring.weekthree.repository.plan;

import com.spring.weekthree.entity.Plan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// 일정 리포지토리 레이어의 인터페이스
public interface PlanRepository {

    // 일정 저장
    Plan save(Plan plan);

    // 일정 목록 조회
    List<Plan> fetchAllPlans(
            Long memberId,
            LocalDate updatedDate
    );

    // 일정 단건 조회
    Plan fetchPlanById0rElseThrow(Long planId);

    // 일정 수정
    int updatePatchInRepository(
            Long id,
            LocalDate plannedDate,
            String title,
            String task,
            LocalDateTime updatedDatetime
    );

    // 일정 삭제
    void deletePlan(Long id);
}