package com.spring.weekthree.repository;

import com.spring.weekthree.entity.Plan;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 도전 과제 C 완료
 * 도전 과제 R 전체 조회 완료
 * 도전 과제 R 단건 조회 완료
 *
 *
 */

@Repository
public class JdbcTemplatePlanRepository implements PlanRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatePlanRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Plan save(Plan plan) {
        SimpleJdbcInsert jdbcInsert;

        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcInsert.withTableName("planner_challenge_plans").
                usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("member_id", plan.getMemberId());
        parameters.put("password", plan.getPassword());
        parameters.put("planned_date", plan.getPlannedDate());
        parameters.put("title", plan.getTitle());
        parameters.put("task", plan.getTask());
        parameters.put("created_date_time", plan.getCreatedDateTime());
        parameters.put("updated_date_time", plan.getUpdatedDateTime());

        Number key = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource(parameters
                )
        );

        return new Plan(
                key.longValue(),
                plan.getMemberId(),
                plan.getPassword(),
                plan.getPlannedDate(),
                plan.getTitle(),
                plan.getTask(),
                plan.getCreatedDateTime(),
                plan.getUpdatedDateTime()
        );
    }

    @Override
    public List<Plan> fetchAllPlans(
            Long memberId,
            LocalDate updatedDate
    ) {
        StringBuilder sql;

        sql = new StringBuilder("SELECT * FROM planner_challenge_plans WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (memberId != null) {
            sql.append(" AND member_id = ? ");
            params.add(memberId);
        }

        if (updatedDate != null) {
            Date updatedDateSql = Date.valueOf(updatedDate);
            sql.append(" AND DATE(updated_date_time) = ? ");
            params.add(updatedDateSql);
        }

        sql.append(" ORDER BY updated_date_time DESC");

        List<Plan> allPlans;

        allPlans = jdbcTemplate.query(
                sql.toString(),
                plannerRowMapper(),
                params.toArray()
        );

        return allPlans;
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
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Id does no exist id = " + id
                        )
                );
    }

    @Override
    public int updatePatchInRepository(

            Long id,
            String name,
            LocalDate plannedDate,
            String title,
            String task,
            LocalDateTime updatedDateTime
    ) {
        return jdbcTemplate.update(
                "UPDATE planner SET " +
                        "name = ?, " +
                        "plannedDate = ?, " +
                        "title = ?, " +
                        "task = ?, " +
                        "updatedDateTime = ? " +
                        "WHERE id = ?",
                name,
                plannedDate,
                title,
                task,
                updatedDateTime,
                id
        );
    }

    @Override
    public void deletePlan(Long id) {
        jdbcTemplate.update(
                "DELETE FROM planner WHERE id = ?",
                id
        );
    }

    private RowMapper<Plan> plannerRowMapper() {
        return new RowMapper<Plan>() {
            @Override
            public Plan mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Plan(
                        // [주의] rs.getLong("plan_id") 아니다. 컬럼 이름 주의!
                        rs.getLong("id"),
                        rs.getLong("member_id"),
                        rs.getString("password"),
                        rs.getDate("planned_date").toLocalDate(),
                        rs.getString("title"),
                        rs.getString("task"),
                        rs.getTimestamp("created_date_time").toLocalDateTime(),
                        rs.getTimestamp("updated_date_time").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Plan> plannerRowMapperEach() {
        return new RowMapper<Plan>() {
            @Override
            public Plan mapRow(ResultSet rs, int rowNum) throws SQLException {
                // TODO
                return null;
//                return new Plan(
//                        rs.getLong("id"),
//                        rs.getString("password"),
//                        rs.getDate("plannedDate").toLocalDate(),
//                        rs.getString("title"),
//                        rs.getString("task"),
//                        rs.getTimestamp("createdDateTime").toLocalDateTime(),
//                        rs.getTimestamp("updatedDateTime").toLocalDateTime()
//                );
            }
        };
    }
}