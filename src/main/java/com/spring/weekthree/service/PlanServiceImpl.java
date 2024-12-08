package com.spring.weekthree.service;

import com.spring.weekthree.dto.requestdto.CreatePlanRequestDto;
import com.spring.weekthree.dto.responsedto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import com.spring.weekthree.repository.PlanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
                requestDto.getName(),
                requestDto.getPassword(),
                requestDto.getPlannedDate(),
                requestDto.getTitle(),
                requestDto.getTask()
        );
        return planRepository.save(plan);
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

        if (!Objects.equals(password, plan.getPassword()))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match"
            );

        planRepository.updatePatchInRepository(
                id,
                name,
                plannedDate,
                title,
                task
        );
        return new PlanResponseDto(plan);
    }

    @Override
    public void processDelete(Long id, String password) {

        Plan plan;

        plan = planRepository.fetchPlanById0rElseThrow(id);

        if (!Objects.equals(password, plan.getPassword()))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match"
            );
        planRepository.deletePlan(id);
    }
}