package com.spring.weekthree.service.plan;

import com.spring.weekthree.dto.plan.request.CreatePlanRequestDto;
import com.spring.weekthree.dto.plan.response.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import com.spring.weekthree.repository.plan.PlanRepository;
import com.spring.weekthree.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 일정 서비스 레이어
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

    /**
     * 기능
     * [1/5] 일정 저장
     *
     * @param requestDto : 저장하려는 객체를 요청 dto로 변환한 것
     * @return PlanResponseDto
     */
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

    /**
     * 기능
     * [2/5] 일정 목록 조회
     *
     * @param memberId    : 사용자명 대신 필터링으로 쓰려는 사용자 id
     * @param updatedDate : 일정을 수정한 날짜
     * @return List<PlanResponseDto>
     */
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

    /**
     * 기능
     * [3/5] 일정 단건 조회
     *
     * @param planId : 조회하려는 일정의 id
     * @return PlanResponseDto
     */
    @Override
    public PlanResponseDto processFetchEach(Long planId) {

        Plan plan = planRepository.fetchPlanById0rElseThrow(planId);

        return new PlanResponseDto(plan);
    }

    /**
     * 기능
     * [4/5] 일정 수정
     *
     * @param planId      : 수정하려는 일정의 id
     * @param password    : 인증에 필요한 비밀번호
     * @param plannedDate : 일정 날짜
     * @param title       : 일정 제목
     * @param task        : 일정 내용
     * @return PlanResponseDto
     */
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

        plan.updatePlan(
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

    /**
     * 기능
     * [5/5] 일정 삭제
     *
     * @param planId   : 삭제하려는 일정의 id
     * @param password : 인증에 필요한 비밀번호
     */
    @Override
    public void processDelete(Long planId, String password) {

        Plan plan;

        plan = planRepository.fetchPlanById0rElseThrow(planId);

        plan.validatePassword(password);

        planRepository.deletePlan(planId);
    }
}