package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;

import java.time.LocalDate;
import java.util.List;

/**
 * Create 완료
 * Read 리팩토링 1차 완료 (if문 대신 스트림 사용, 목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
 * JDBC - Create 리팩토링 완료
 * JDBC - Read 리팩토링 중 (목록 조회)
 * JDBC - Read 리팩토링 완료 (예외처리 추가 수정, 단건 조회)
 * JDBC - Update 리팩토링 3차 완료 (클린 업 완료, 수정 날짜 바뀌도록 수정, 일부가 null일 때 예외 처리 전)
 * JDBC - Delete 리팩토링 완료
 */

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