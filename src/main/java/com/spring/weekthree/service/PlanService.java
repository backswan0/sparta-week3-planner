package com.spring.weekthree.service;

import com.spring.weekthree.dto.requestdto.CreatePlanRequestDto;
import com.spring.weekthree.dto.responsedto.PlanResponseDto;

import java.time.LocalDate;
import java.util.List;

/**
 * [리팩토링 완료]
 * 수정이 바로 안 되는 점 해결
 */

public interface PlanService {
    PlanResponseDto processSave(CreatePlanRequestDto requestDto);

    List<PlanResponseDto> processFetchList(String name, LocalDate updatedDate);

    PlanResponseDto processFetchEach(Long id);

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