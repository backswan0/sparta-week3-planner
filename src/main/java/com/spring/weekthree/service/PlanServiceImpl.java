package com.spring.weekthree.service;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import com.spring.weekthree.repository.PlanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Create 완료
 * Read 리팩토링 1차 완료 (if문 대신 스트림 사용, 목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
 * JDBC - Create 리팩토링 완료
 * JDBC - Read 리팩토링 중 (목록 조회)
 * JDBC - Read 리팩토링 완료 (단건 조회)
 * JDBC - Update 리팩토링 1차 완료 (일부가 null일 때 예외 처리 전?)
 *
 */

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
    public PlanResponseDto processSave(PlanRequestDto requestDto) {

        Plan plan = new Plan(
                requestDto.getName(),
                requestDto.getPassword(),
                requestDto.getPlannedDate(),
                requestDto.getTitle(),
                requestDto.getTask()
        );

        return planRepository.save(plan);
        /*
        [수정 전]
        Plan savedPlan = planRepository.save(plan);
        return new PlanResponseDto(savedPlan);
        [수정 후 1]
        PlanResponseDto savedPlan = planRepository.save(plan);
        return new PlanResponseDto(savedPlan);
        [수정 후 2]
        return planRepository.save(plan);
        TODO 갈아끼울 때 아예 PlanServiceImpl을 건드리지 않는 방법은 없을까?
         */
    }

    @Override
    public List<PlanResponseDto> processFetchList(String name, LocalDate updatedDate) {

        return planRepository.fetchAllPlans(name, updatedDate);
    }

    @Override
    public PlanResponseDto processFetchEach(Long id) {
        Optional<Plan> optionalPlan = planRepository.fetchPlanById(id);
        // [수정 전] Plan planById = planRepository.fetchPlanById(id);

        if (optionalPlan.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id does not exist = " + id);
        }
        // [수정 전] (planById == null)

        return new PlanResponseDto(optionalPlan.get());
        // [수정 전] return new PlanResponseDto(planById);
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

        Optional<Plan> optionalPlan = planRepository.fetchPlanById(id);

        if (!Objects.equals(password, optionalPlan.get().getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match");

        int updatedRow = planRepository.updatePatchInRepository(id, name, plannedDate, title, task);

        if (updatedRow == 0) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id does not exist = " + id);
        }
        return new PlanResponseDto(optionalPlan.get());
    }

    @Override
    public void processDelete(Long id, String password) {
//        Plan planById = planRepository.fetchPlanById(id);
//
//        if (planById == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id does not exist = " + id);
//        }
//
//        if (!Objects.equals(password, planById.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match");
//        }
//
//        planRepository.deletePlan(id);
    }
}