package com.spring.weekthree.service;

import com.spring.weekthree.dto.PlanRequestDto;
import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import com.spring.weekthree.repository.PlanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Create 완료
 * Read 완료 (목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
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
    public PlanResponseDto processSave(PlanRequestDto requestDto) {

        Plan plan = new Plan(
                requestDto.getName(),
                requestDto.getPassword(),
                requestDto.getPlannedDate(),
                requestDto.getTitle(),
                requestDto.getTask()
        );

        Plan savedPlan = planRepository.save(plan);

        return new PlanResponseDto(savedPlan);
         /*
         TODO 실습에서는 아래와 같이 했는데 똑같이 적용하지 못했다. 뭐가 문제일까?
          정답: 반환하는 데이터 타입이 달라서 안 됨
           return memoRepository.saveMemo(memo);
            정답: return new PlanResponseDto(planRepository.save(plan));
          */
    }

    @Override
    public List<PlanResponseDto> processPullList(String name, LocalDate updatedDate) {

        return planRepository.pullAllAsList(name, updatedDate);
    }

    @Override
    public PlanResponseDto processPullEach(Long id) {
        Plan planById = planRepository.pullEachById(id);

        if (planById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id does not exist = " + id);
        }

        return new PlanResponseDto(planById);
    }

    @Override
    public PlanResponseDto processUpdatePatch(
            Long id,
            String name,
            String password,
            LocalDateTime plannedDate,
            String title,
            String task
    ) {
        Plan planById = planRepository.pullEachById(id);

        if (planById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id does not exist = " + id);
        }

        if (!Objects.equals(password, planById.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match");

        planById.edit(name, plannedDate, title, task);

        return new PlanResponseDto(planById);
    }

    @Override
    public void processDelete(Long id, String password) {
            Plan planById = planRepository.pullEachById(id);

            if (planById == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id does not exist = " + id);
            }

            if (!Objects.equals(password, planById.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match");
            }

            planRepository.deletePlan(id);
    }
}