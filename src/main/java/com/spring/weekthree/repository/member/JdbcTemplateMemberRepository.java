package com.spring.weekthree.repository.member;

import com.spring.weekthree.entity.Member;
import com.spring.weekthree.util.TimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

// 사용자 리포지토리 레이어
@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {
    // 속성
    private final JdbcTemplate jdbcTemplate;

    // 생성자
    public JdbcTemplateMemberRepository(
            DataSource dataSource
    ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 기능
     * [1/2] 사용자 조회
     * @param memberId : 사용자의 id
     * @return 사용자 id에 해당하는 plan list
     */
    @Override
    public Member fetchMemberByIdOrElseThrow(Long memberId) {
        List<Member> result = jdbcTemplate.query(
                "SELECT * FROM planner_challenge_members WHERE id = "
                        + memberId,
                memberRowMapperEach()
        );

        if (result.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Id does no exist id = " + memberId
            );
        }
        return result.get(0);
    }

    private RowMapper<Member> memberRowMapperEach() {
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Member(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("created_date_time").toLocalDateTime(),
                        rs.getTimestamp("updated_date_time").toLocalDateTime()
                );
            }
        };
    }

    /**
     * 기능
     * [2/2]
     * @param memberId : 사용자의 id
     * @param name : 수정하려는 사용자명
     * @return int
     */
    @Override
    public int updateMemberName(Long memberId, String name) {

        LocalDateTime updatedDateTime = TimeUtil.now();

        return jdbcTemplate.update(
                "UPDATE planner_challenge_members SET " +
                        "name = ?, " +
                        "updated_date_time = ? " +
                        "WHERE id = ?",
                name,
                updatedDateTime,
                memberId
        );
    }
}