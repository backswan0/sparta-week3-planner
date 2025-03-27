package com.spring.weekthree.repository.plan;

import com.spring.weekthree.entity.Plan;
import org.springframework.data.domain.Pageable;
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
            LocalDate updatedDate,
            Pageable pageable
    ) {
        StringBuilder sql = new StringBuilder("SELECT * FROM planner_challenge_plans WHERE 1=1");

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

        sql.append(" LIMIT ? OFFSET ? ");
        params.add(pageable.getPageSize());
        // Limit - size - 한 페이지에 데이터 몇 개 가져올지
        params.add(pageable.getOffset());
        // Offset - page - 몇 페이지부터 보여줄 건가

        /* TODO
            1. Member 조회 및 검증 (MemberRepository)
            SELECT * FROM planner_challenge_members WHERE id = ?
         */

        /* TODO
            1-2. Member 가 null 인지 (존재하지 않는건지) 보고 없으면 없다는 예외 (MemberRepository)
            fetchPlanById0rElseThrow 참고
         */
        // TODO 1, 1-2 는 이미 (폭주한) 지현님이 구현을 해놨음 사용만 하면됨 Service 에서

        /* TODO
            2. Member Id 로 Plan 조회 (PlanRepository), Paging을 곁들인
            SELECT * FROM planner_challenge_plans WHERE member_id = ?
             추후에 쿼리 최적화를 할 때 * 말고 추출하고 싶은 것만 어떻게 뽑을지 고민해보자..!
         */

        /* TODO
            memberId가 없을 때 이걸 해야 한다..!
            3. 조회한 Plan 리스트에서 memberId를 추출하여 member 테이블에서 name 조회
            SELECT id, name FROM planner_challenge_members WHERE id IN (?, ?, ?..)
            == 리스트는 WHERE에서 어떻게 조회할까?
            문제: 리스트 안에 데이터가 500개, 1000개면 어떡하지? + 페이지 최댓값을 설정해야 한다. 보통은 1000개까지
            정답: 보통 정렬을 시킨 다음에 최솟값과 최댓값이 있으므로, 범위를 나누어서 각각 조회한다.
               4. planService에서 합쳐야겠지...?
               <join을 쓰면 생기는 또 다른 문제>
                어플리케이션에서 해야 할 일을 데이터베이스에 위임할 수 있다 == 안티패턴
                대용량 트래픽, 대용량 데이터 관련...!!! ㅇㅁㅇ
                개발에서 실버 불릿은 없다. 항상 side effect와 trade-off를 고민해야 한다....!!!!
                Q. 어플리케이션에 모든 로직을 쓰면 되는데(==리치 클라이언트) 왜 백엔드와 데이터베이스가 필요할까??????? == 면접 질문...!
                나중에 꼭 공부해보기...!
                join만 할 줄 아는 것과 둘 다 할 수 있는 건 다르니까...!!!
                "테스트 코드 꼭!!!!!!!!!짜자!!!!!!!" 운영 이슈를 만나고 싶지 않다면:)
                "부하 테스트로도 할 수 있다" (스트레스 테스트)
         */

        // TODO 생각해야할점은 다른 Repository 접근이니까 Repository를 사용하는 PlanService 에서 어떨까?
        // TODO 처음부터 join을 쓰면 Join만 쓰려고 할 수 있다. (카타시안 곱?) 나중에 큰 테이블을 만났을 때 당황할 수 있다. (ps. 쿼리 플랜?)

        // 멤버 테이블에서 아이디랑 이름만 가져오는데, 우리는 id로 이름을 찾아야 함..!
        // 그러면, 아이디 == key
        // 키로 값을 바로 찾아올 수 있는 자료 구조: HashMap에 담고...
        // plan을 "순회"하면서...멤버 아이디로 해시맵을 뒤져서...멤버 네임만 빼서...멤버 네임과 일정을 조립해서 내보내면 끝......??
        // 시간 복잡도가 뭘까????????????
        List<Plan> allPlans = new ArrayList<Plan>();

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
                    "Id does not exist id = " + planId
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