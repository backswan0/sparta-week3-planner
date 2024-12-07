package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * JDBC - Update 리팩토링 2차 완료 (수정 날짜 바뀌도록 수정, 일부가 null일 때 예외 처리 전)
 * JDBC - Delete 리팩토링 완료
 */

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
        작성하면 빨간 줄이 뜬다.
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
        return jdbcTemplate.query("SELECT * FROM planner", plannerRowMapper());
    }

    @Override
    public Optional<Plan> fetchPlanById(Long id) {
        List<Plan> result = jdbcTemplate.query("SELECT * FROM planner WHERE id = ?", plannerRowMapperEach(), id);
        return result.stream().findAny();
    }

    @Override
    public int updatePatchInRepository(
            Long id,
            String name,
            LocalDate plannedDate,
            String title,
            String task
    ) {
        return jdbcTemplate.update(
                "UPDATE planner SET " +
                        "name = ?, " +
                        "plannedDate = ?, " +
                        "title = ?, " +
                        "task = ?, " +
                        "updatedDateTime = CURRENT_TIMESTAMP " +
                        "WHERE id = ?",
                name,
                plannedDate,
                title,
                task,
                id);
    }

    @Override
    public int deletePlan(Long id) {
        int updatedRow = jdbcTemplate.update("DELETE FROM planner WHERE id = ?", id);

        return updatedRow;
    }

    private RowMapper<PlanResponseDto> plannerRowMapper() {
        // return new까지만 입력해도 RowMapper가 나오는데, 그걸 입력하면 자동 생성된다.
        return new RowMapper<PlanResponseDto>() {
            @Override
            public PlanResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new PlanResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("plannedDate").toLocalDate(),
                        rs.getString("title"),
                        rs.getString("task"),
                        rs.getTimestamp("createdDateTime").toLocalDateTime(),
                        rs.getTimestamp("updatedDateTime").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Plan> plannerRowMapperEach() {
        return new RowMapper<Plan>() {
            @Override
            public Plan mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Plan(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getDate("plannedDate").toLocalDate(),
                        rs.getString("title"),
                        rs.getString("task"),
                        rs.getTimestamp("createdDateTime").toLocalDateTime(),
                        rs.getTimestamp("updatedDateTime").toLocalDateTime()
                );
            }
        };
    }
}