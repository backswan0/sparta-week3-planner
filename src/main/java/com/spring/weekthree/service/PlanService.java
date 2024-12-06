package com.spring.weekthree.service;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Create 완료
 * Read 리팩토링 1차 완료 (if문 대신 스트림 사용, 목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
 */

public interface PlanService {
    PlanResponseDto processSave(PlanRequestDto requestDto);

    List<PlanResponseDto> processPullList(String name, LocalDate updatedDate);

    PlanResponseDto processPullEach(Long id);

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
            LocalDateTime plannedDate,
            String title,
            String task
    );

    void processDelete(Long id, String password);
}