package com.spring.weekthree.service;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;

public interface PlanService {
    PlanResponseDto processSaveInService(PlanRequestDto requestDto);
}