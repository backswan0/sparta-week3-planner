package com.spring.weekthree.service;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import com.spring.weekthree.repository.PlanRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService {
    // [1] 속성
    private final PlanRepository planRepository;

    // [2] 생성자
    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    // [3] 기능
    @Override
    public PlanResponseDto processSaveInService(PlanRequestDto requestDto) {

        Plan plan = new Plan(
                requestDto.getName(),
                requestDto.getPassword(),
                requestDto.getPlannedDate(),
                requestDto.getTitle(),
                requestDto.getTask()
        );

        Plan savedPlan = planRepository.savePlanInRepository(plan);

        return new PlanResponseDto(savedPlan);
         /*
         TODO 실습에서는 아래와 같이 했는데 똑같이 적용하지 못했다. 뭐가 문제일까?
          return memoRepository.saveMemo(memo);
          */

    }
}