package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Create 완료
 * Read 리팩토링 1차 완료 (if문 대신 스트림 사용, 목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
 * JDBC - Create 리팩토링 완료
 * JDBC - Read 리팩토링 중 (목록 조회)
 * JDBC - Read 리팩토링 완료 (단건 조회)
 * JDBC - Update 리팩토링 2차 완료 (수정 날짜 바뀌도록 수정, 일부가 null일 때 예외 처리 전)
 * JDBC - Delete 리팩토링 완료
 */

public interface PlanRepository {
    PlanResponseDto save(Plan plan);

    List<PlanResponseDto> fetchAllPlans(String name, LocalDate updatedDate);
    /*
    [수정 이유]
    단어 fetch가 '(어디를 가서) 가지고 오다' 또는 '불러오다'라서 의미가 더 잘 보였다.
    또한, All에 이미 소문자 l이 두 개나 있어서 pullAllAsList는 각 단어가 눈에 잘 안 들어왔다.
    l이 없으면서 의미를 명확하게 전달하고자 pull 대신 동사 fetch를 사용했다.
     */

    Optional<Plan> fetchPlanById(Long id);

    int updatePatchInRepository (
            Long id,
            String name,
            LocalDate plannedDate,
            String title,
            String task
    );

    int deletePlan (Long id);
}