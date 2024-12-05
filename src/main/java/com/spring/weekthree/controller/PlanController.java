package com.spring.weekthree.controller;


import com.spring.weekthree.dto.PlanCreateRequestDto;
import com.spring.weekthree.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedulers")
public class PlanController {
    // 속성
    private final PlanService planService;


    // 생성자
    public PlanController(PlanService planService) {

        this.planService = planService;
    }

    // 기능
    @PostMapping
    public ResponseEntity<PlanCreateRequestDto> testPost (@RequestBody PlanCreateRequestDto requestDto) {

        return new ResponseEntity<>(HttpStatus.CREATED
        );
    }
}
