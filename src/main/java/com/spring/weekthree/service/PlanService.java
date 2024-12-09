package com.spring.weekthree.service;

import com.spring.weekthree.dto.requestdto.CreatePlanRequestDto;
import com.spring.weekthree.dto.responsedto.PlanResponseDto;

import java.time.LocalDate;
import java.util.List;

/**
 * 도전 과제 C 완료
 * 도전 과제 R 전체 조회 완료
 * 도전 과제 R 단건 조회 리팩토링 완료
 *
 * 도전 과제 D 완료
 */

public interface PlanService {
    PlanResponseDto processSave(CreatePlanRequestDto requestDto);

    List<PlanResponseDto> processFetchList(
            Long memberId,
            LocalDate updatedDate
    );

    PlanResponseDto processFetchEach(Long planId);

    /**
     * @param id          :
     * @param name        :
     * @param password    :
     * @param plannedDate :
     * @param title       :
     * @param task        :
     * @return PlanResponseDto
     */
    PlanResponseDto processUpdatePatch(
            Long id,
            String name,
            String password,
            LocalDate plannedDate,
            String title,
            String task
    );

    void processDelete(Long id, String password);
}