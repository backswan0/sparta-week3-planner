package com.spring.weekthree.service.plan;

import com.spring.weekthree.dto.plan.request.CreatePlanRequestDto;
import com.spring.weekthree.dto.plan.response.PlanResponseDto;

import java.time.LocalDate;
import java.util.List;

// 일정 서비스 레이어의 인터페이스
public interface PlanService {
    // 일정 저장
    PlanResponseDto processSave(CreatePlanRequestDto requestDto);

    // 일정 목록 조회
    List<PlanResponseDto> processFetchList(
            Long memberId,
            LocalDate updatedDate
    );

    // 일정 단건 조회
    PlanResponseDto processFetchEach(Long planId);

    /**
     * 일정 수정
     * @param planId          : 수정하려는 일정의 id
     * @param password        : 비밀번호
     * @param plannedDate     : 일정 날짜
     * @param title           : 일정 제목
     * @param task            : 일정 내용
     * @return PlanResponseDto
     */
    PlanResponseDto processUpdatePatch(
            Long planId,
            String password,
            LocalDate plannedDate,
            String title,
            String task
    );

    // 일정 삭제
    void processDelete(Long id, String password);
}