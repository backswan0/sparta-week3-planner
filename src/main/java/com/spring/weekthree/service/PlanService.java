package com.spring.weekthree.service;

import com.spring.weekthree.dto.requestdto.CreatePlanRequestDto;
import com.spring.weekthree.dto.responsedto.PlanResponseDto;

import java.time.LocalDate;
import java.util.List;

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