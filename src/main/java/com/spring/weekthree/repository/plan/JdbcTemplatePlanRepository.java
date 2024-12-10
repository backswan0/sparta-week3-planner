package com.spring.weekthree.repository.plan;

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

// 일정 리포지토리 레이어
@Repository
public class JdbcTemplatePlanRepository implements PlanRepository {
    // 속성
    private final JdbcTemplate jdbcTemplate;

    // 생성자
    public JdbcTemplatePlanRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 기능
     * [1/5] 생성된 일정 저장
     *
     * @param plan : 저장하려는 일정
     * @return Plan
     */
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

    /**
     * 기능
     * [2/5] 일정 전체 조회
     *
     * @param memberId    : 사용자의 id
     * @param updatedDate : 일정이 수정된 날짜
     * @return List<Plan>
     */
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

    /**
     * 기능
     * [3/5] 일정 단건 조회
     *
     * @param planId : 조회하려는 일정의 id
     * @return Plan
     */
    @Override
    public Plan fetchPlanById0rElseThrow(Long planId) {

        List<Plan> result = jdbcTemplate.query(
                "SELECT * FROM planner_challenge_plans WHERE id = "
                        + planId,
                plannerRowMapperEach()
        );

        if (result.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Id does no exist id = " + planId
            );
        }
        return result.get(0);
    }

    /**
     * 기능
     * [4/5] 일정 수정
     *
     * @param planId          : 수정하려는 일정의 id
     * @param plannedDate     : 수정하려는 일정 날짜
     * @param title           : 수정하려는 일정 제목
     * @param task            : 수정하려는 일정 할일
     * @param updatedDateTime : 일정이 수정된  날짜
     * @return int
     */
    @Override
    public int updatePatchInRepository(
            Long planId,
            LocalDate plannedDate,
            String title,
            String task,
            LocalDateTime updatedDateTime
    ) {
        return jdbcTemplate.update(
                "UPDATE planner_challenge_plans SET " +
                        "planned_date = ?, " +
                        "title = ?, " +
                        "task = ?, " +
                        "updated_date_time = ? " +
                        "WHERE id = ?",
                plannedDate,
                title,
                task,
                updatedDateTime,
                planId
        );
    }

    /**
     * 기능
     * [5/5] 일정 삭제
     *
     * @param planId : 삭제하려는 일정의 Id
     */
    @Override
    public void deletePlan(Long planId) {
        jdbcTemplate.update(
                "DELETE FROM planner_challenge_plans WHERE id = ?",
                planId
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
                return new Plan(
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
}