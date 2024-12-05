package com.spring.weekthree.service;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import com.spring.weekthree.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create 완료
 * Read 완료 (목록 조회)
 *
 *
 *
 */

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

        Plan savedPlan = planRepository.saveEachPlan(plan);

        return new PlanResponseDto(savedPlan);
         /*
         TODO 실습에서는 아래와 같이 했는데 똑같이 적용하지 못했다. 뭐가 문제일까?
          return memoRepository.saveMemo(memo);
          */

    }

    @Override
    public List<PlanResponseDto> processViewService() {

        return planRepository.pullAllPlans();
        /*
        TODO
         [수정 전]
         List<PlanResponseDto> allPlans = planRepository.pullAllPlans();
         return allPlans;
         [고민]
         어떤 형태가 비즈니스 로직이 더 한 눈에 잘 들어올까?
         */
    }
}