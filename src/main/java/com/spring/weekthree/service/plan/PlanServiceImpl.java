package com.spring.weekthree.service.plan;

import com.spring.weekthree.dto.plan.request.CreatePlanRequestDto;
import com.spring.weekthree.dto.plan.response.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import com.spring.weekthree.repository.member.MemberRepository;
import com.spring.weekthree.repository.plan.PlanRepository;
import com.spring.weekthree.util.TimeUtil;
import org.springframework.data.domain.Pageable;
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
    private final MemberRepository memberRepository;

    // 생성자
    public PlanServiceImpl(
            PlanRepository planRepository, MemberRepository memberRepository) {
        this.planRepository = planRepository;
        this.memberRepository = memberRepository;
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
            LocalDate updatedDate,
            Pageable pageable
    ) {
        /*
        [1. 언제 호출해야 하는가? == memberId가 null이 아닐 때..!]
        1. member id -> 호출할 수 있음 (== 매개 변수)
        2. updatedDate -> 호출할 수 없음, 근데 memberRepository를 호출하면? 당연히 에러가 발생하겠지?
           2번에서는 조회하면 안 됨
        3. member id && updatedDate
        4. null일 때도 호출하면? 에러 발생하겠지?
        [2. 어디서 호출해야 하는가?]
         */
        List<Plan> plans = planRepository.fetchAllPlans(
                memberId,
                updatedDate,
                pageable
        );

        List<PlanResponseDto> allPlans = new ArrayList<>();

        for (Plan plan : plans) {
            allPlans.add(new PlanResponseDto(plan));
        }

        return allPlans;
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
        Plan plan = planRepository.fetchPlanById0rElseThrow(planId);

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

        Plan plan = planRepository.fetchPlanById0rElseThrow(planId);

        plan.validatePassword(password);

        planRepository.deletePlan(planId);
    }
}