package com.spring.weekthree.service;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import com.spring.weekthree.repository.PlanRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService {
    // 속성
    private final PlanRepository planRepository;

    // 생성자
    public PlanServiceImpl (PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public PlanResponseDto savePlan(PlanRequestDto requestDto) {

        Plan plan = new Plan(
                requestDto.getName(),
                requestDto.getPassword(),
                requestDto.getPlannedDate(),
                requestDto.getTitle(),
                requestDto.getTask()
        );

        Plan savedPlan = planRepository.savePlan(plan);

        return new PlanResponseDto(savedPlan);
    }

    // 기능
}
