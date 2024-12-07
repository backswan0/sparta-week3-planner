package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @Repository 어노테이션을 절대 잊지 말자!!!
@Repository
public class JdbcTemplatePlanRepository implements PlanRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatePlanRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PlanResponseDto save(Plan plan) {
        // Insert Query를 직접 문자열로 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("planner").usingGeneratedKeyColumns("id");

        /*
        Map은 인터페이스이기 때문에 항상 구현체를 생성하여 초기화해야 한다.
        == new HashMap<>()을 입력하는 이유
         */
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("name", plan.getName());
        parameters.put("password", plan.getPassword());
        parameters.put("plannedDate", plan.getPlannedDate());
        parameters.put("title", plan.getTitle());
        parameters.put("task", plan.getTask());
        parameters.put("createdDateTime", plan.getCreatedDateTime());
        parameters.put("updatedDateTime", plan.getUpdatedDateTime());

        // 저장 후 생성될 key값, 즉 id값을 Number 타입으로 반환하는 메서드이다.
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        /*
        작성하면 빨간 줄이 뜰 텐다.
        PlanResponseDto 클래스에 가서 @AllArgsConstructor를 사용해야 빨간 줄이 사라진다.
         */
        return new PlanResponseDto(
                key.longValue(),
                plan.getName(),
                plan.getPlannedDate(),
                plan.getTitle(),
                plan.getTask(),
                plan.getCreatedDateTime(),
                plan.getUpdatedDateTime());
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
