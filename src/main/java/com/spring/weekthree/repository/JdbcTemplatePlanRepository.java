package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

// @Repository 어노테이션을 절대 잊지 말자!!!
@Repository
public class JdbcTemplatePlanRepository implements PlanRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatePlanRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PlanResponseDto save(Plan plan) {
        return null;
    }

    @Override
    public List<PlanResponseDto> fetchAllPlans(String name, LocalDate updatedDate) {
        return List.of();
    }

    @Override
    public Plan fetchPlanById(Long id) {
        return null;
    }

    @Override
    public void deletePlan(Long id) {

    }
}
