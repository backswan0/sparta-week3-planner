package com.spring.weekthree.service.plan;

import com.spring.weekthree.dto.plan.request.CreatePlanRequestDto;
import com.spring.weekthree.dto.plan.response.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import com.spring.weekthree.repository.member.MemberRepository;
import com.spring.weekthree.repository.plan.PlanRepository;
import com.spring.weekthree.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 도전 과제 C 완료
 * 도전 과제 R 전체 조회 완료
 * 도전 과제 R 단건 조회 리팩토링 완료
 * 도전 과제 U 초안 완료
 * 도전 과제 D 완료
 */

@Service
public class PlanServiceImpl implements PlanService {
    // 속성
    private final PlanRepository planRepository;

    // 생성자
    public PlanServiceImpl(
            PlanRepository planRepository
    ) {

        this.planRepository = planRepository;
    }

    // 기능
    @Override
    public PlanResponseDto processSave(
            CreatePlanRequestDto requestDto
    ) {
        Plan plan = new Plan(
                requestDto.getMemberId(),
                requestDto.getPassword(),
                requestDto.getPlannedDate(),
                requestDto.getTitle(),
                requestDto.getTask()
        );
        Plan savedPlan = planRepository.save(plan);

        return new PlanResponseDto(savedPlan);
        /*
        TODO
         repository를 in-memory에서 데이터베이스로 갈아끼울 때
         아예 PlanServiceImpl을 건드리지 않는 방법은 없을까?
         */
    }

    @Override
    public List<PlanResponseDto> processFetchList(
            Long memberId,
            LocalDate updatedDate
    ) {
        List<Plan> plans;

        plans = planRepository.fetchAllPlans(
                memberId,
                updatedDate
        );
        // 1. 레포지토리에서 리스트를 타입이 Plan인 리스트를 가져온다.

        List<PlanResponseDto> allPlans = new ArrayList<>();
        // 2. 타입이 PlanResponseDto인 리스트 allPlans를 선언한다.

        for (Plan plan : plans) {
            allPlans.add(new PlanResponseDto(plan));
        }
        // 3. plans에서 plan을 하나씩 꺼내 dto 객체로 생성하여 넣는다.

        return allPlans;
        // 4. 반환한다.
    }

    @Override
    public PlanResponseDto processFetchEach(Long memberId) {

        Plan plan = planRepository.fetchPlanById0rElseThrow(memberId);

        return new PlanResponseDto(plan);
    }

    @Override
    public PlanResponseDto processUpdatePatch(
            Long planId,
            String password,
            LocalDate plannedDate,
            String title,
            String task
    ) {
        Plan plan;

        plan = planRepository.fetchPlanById0rElseThrow(planId);

        plan.validatePassword(password);

        LocalDateTime updatedDateTime = TimeUtil.now();

        plan.update(
                plannedDate,
                title,
                task,
                updatedDateTime
        );

        planRepository.updatePatchInRepository(
                planId,
                plannedDate,
                title,
                task,
                updatedDateTime
        );

        return new PlanResponseDto(plan);
    }

    @Override
    public void processDelete(Long planId, String password) {

        Plan plan;

        plan = planRepository.fetchPlanById0rElseThrow(planId);

        plan.validatePassword(password);

        planRepository.deletePlan(planId);
    }
}