package com.spring.weekthree.service;

import com.spring.weekthree.dto.PlanCreateRequestDto;
import com.spring.weekthree.dto.PlanGetResponseDto;

public interface PlanService {
    PlanGetResponseDto save(PlanCreateRequestDto requestDto);
}
