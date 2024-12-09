package com.spring.weekthree.service;

import com.spring.weekthree.dto.requestdto.CreatePlanRequestDto;
import com.spring.weekthree.dto.responsedto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import com.spring.weekthree.repository.PlanRepository;
import com.spring.weekthree.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    // 속성
    private final PlanRepository planRepository;

    // 생성자
    public PlanServiceImpl(PlanRepository planRepository) {

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
            String name,
            LocalDate updatedDate
    ) {
        return planRepository.fetchAllPlans(name, updatedDate);
    }

    @Override
    public PlanResponseDto processFetchEach(Long id) {
        Plan plan;

        plan = planRepository.fetchPlanById0rElseThrow(id);

        return new PlanResponseDto(plan);
    }

    /**
     * @param id          :
     * @param name        :
     * @param password    :
     * @param plannedDate :
     * @param title       :
     * @param task        :
     * @return new PlanResponseDto(planById)
     */
    @Override
    public PlanResponseDto processUpdatePatch(
            Long id,
            String name,
            String password,
            LocalDate plannedDate,
            String title,
            String task
    ) {
        Plan plan;

        plan = planRepository.fetchPlanById0rElseThrow(id);

        plan.validatePassword(password);

        LocalDateTime updatedDateTime = TimeUtil.now();

        int updatedRow = planRepository.updatePatchInRepository(
                id,
                name,
                plannedDate,
                title,
                task,
                updatedDateTime
        );

        if (updatedRow >= 1) {
            plan.update(
                    plannedDate,
                    title,
                    task,
                    updatedDateTime
            );
        }
        return new PlanResponseDto(plan);
    }

    @Override
    public void processDelete(Long id, String password) {

        Plan plan;

        plan = planRepository.fetchPlanById0rElseThrow(id);

        plan.validatePassword(password);

        planRepository.deletePlan(id);
    }
}