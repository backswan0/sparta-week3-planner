package com.spring.weekthree.repository;

import com.spring.weekthree.dto.responsedto.PlanResponseDto;
import com.spring.weekthree.entity.Plan;
import org.springframework.data.relational.core.sql.SQL;
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
import java.util.ArrayList;
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

        jdbcInsert.withTableName("planner").
                usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("name", plan.getName());
        parameters.put("password", plan.getPassword());
        parameters.put("plannedDate", plan.getPlannedDate());
        parameters.put("title", plan.getTitle());
        parameters.put("task", plan.getTask());
        parameters.put("createdDateTime", plan.getCreatedDateTime());
        parameters.put("updatedDateTime", plan.getUpdatedDateTime());

        Number key = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource(parameters)
        );

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
    public List<PlanResponseDto> fetchAllPlans(
            String name,
            LocalDate updatedDate
    ) {
        StringBuilder sql = new StringBuilder("SELECT * FROM planner WHERE 1=1");
        /*
        [StringBuilder]
        - 기본 SQL 쿼리인 "SELECT * FROM planner WHERE 1=1 "로 초기화
        - WHERE 1=1은 조건이 항상 참이므로,
        나중에 동적으로 조건을 추가할 때 AND와 함께 쉽게 연결할 수 있게 도와준다.
         */

        List<Object> params = new ArrayList<>();
        /*
        - SQL 파라미터를 담을 리스트를 생성
         */

        if (name != null) {
            sql.append(" AND BINARY name = ? ");
            params.add(name);
        }
        /*
        [name이 null이 아니면]
         - "AND BINARY name = ?" 조건을 SQL 쿼리에 추가
         - 조건에 해당하는 name 값을 params 리스트에 추가
         - [수정 전] sql.append("AND BINARY name = ? ");
         */

        if (updatedDate != null) {
            Date updatedDateSql = Date.valueOf(updatedDate);
            sql.append(" AND DATE(updatedDateTime) = ? ");
            params.add(updatedDateSql);
        }
        /*
        [updatedDate가 null이 아니면]
         - LocalDate 데이터 타입을 SQL Date(java.sql.Date)로 변환
         - "AND DATE(updatedDateTime) = ?" 조건을 SQL 쿼리에 추가
         - 조건에 해당하는 updatedDate 값을 params 리스트에 추가
         - [수정 전] sql.append("AND BINARY name = ? ");
         */

        sql.append(" ORDER BY updatedDateTime DESC");
        // SQL 쿼리의 끝에 내림차순 추가

        List<PlanResponseDto> allPlans = jdbcTemplate.query(
                sql.toString(),
                // StringBuilder 객체 sql에 저장된 문자열 반환

                plannerRowMapper(),
                // SQL 결과를 PlanResponseDto 객체로 변환

                params.toArray()
                /*
                [1] params 리스트를 Object[] 배열로 변환
                [2] jdbcTemplate.query 메서드에 전달
                 */
        );
        // 완성된 SQL 쿼리와 파라미터로 쿼리 실행 결과를 List<PlanResponseDto>로 반환
        // [수정 전] "ORDER BY updatedDateTime DESC"
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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
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
        jdbcTemplate.update(
                "DELETE FROM planner WHERE id = ?",
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