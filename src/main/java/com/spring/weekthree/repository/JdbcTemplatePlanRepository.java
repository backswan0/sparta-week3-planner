package com.spring.weekthree.repository;

import com.spring.weekthree.dto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class JdbcTemplatePlanRepository implements PlanRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatePlanRepository(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PlanResponseDto save(Plan plan) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("planner").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("name", plan.getName());
        parameters.put("password", plan.getPassword());
        parameters.put("plannedDate", plan.getPlannedDate());
        parameters.put("title", plan.getTitle());
        parameters.put("task", plan.getTask());
        parameters.put("createdDateTime", plan.getCreatedDateTime());
        parameters.put("updatedDateTime", plan.getUpdatedDateTime());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

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
        Stream<PlanResponseDto> allPlans = Stream.empty();

        if ((name != null) && (updatedDate != null)) {
            Date updatedDateSql = Date.valueOf(updatedDate);
            allPlans = jdbcTemplate.queryForStream(
                    "SELECT * FROM planner " +
                            "WHERE BINARY name = ? " +
                            "AND DATE(updatedDateTime) = ? " +
                            "ORDER BY updatedDateTime DESC",
                    plannerRowMapper(),
                    name,
                    updatedDateSql
            );
        } else if (name != null) {
            allPlans = jdbcTemplate.queryForStream(
                    "SELECT * FROM planner " +
                            "WHERE BINARY name = ? " +
                            "ORDER BY updatedDateTime DESC",
                    plannerRowMapper(),
                    name
            );
        } else if (updatedDate != null) {
            Date updatedDateSql = Date.valueOf(updatedDate);
            allPlans = jdbcTemplate.queryForStream(
                    "SELECT * FROM planner " +
                            "WHERE DATE(updatedDateTime) = ? " +
                            "ORDER BY updatedDateTime DESC",
                    plannerRowMapper(),
                    updatedDateSql
            );
        } else {
            allPlans = jdbcTemplate.queryForStream(
                    "SELECT * FROM planner " +
                            "ORDER BY updatedDateTime DESC",
                    plannerRowMapper()
            );
        }

        return allPlans.collect(Collectors.toList());
    }

    @Override
    public Plan fetchPlanById0rElseThrow(Long id) {

        List<Plan> result = jdbcTemplate.query(
                "SELECT * FROM planner WHERE id =?",
                plannerRowMapperEach(),
                id
        );
        return result.stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Id does no exist id = " + id)
                );
    }

    @Override
    public void updatePatchInRepository(
            Long id,
            String name,
            LocalDate plannedDate,
            String title,
            String task
    ) {
        jdbcTemplate.update(
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
                id
        );
    }

    @Override
    public void deletePlan(Long id) {
        jdbcTemplate.update("DELETE FROM planner WHERE id = ?",
                id
        );
    }

    private RowMapper<PlanResponseDto> plannerRowMapper() {
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