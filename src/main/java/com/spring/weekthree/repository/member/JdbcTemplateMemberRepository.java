package com.spring.weekthree.repository.member;

import com.spring.weekthree.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 도전 과제 C 완료
 * 도전 과제 R 전체 조회 완료
 * 도전 과제 R 단건 조회 리팩토링 완료
 * 도전 과제 U 초안 완료
 * 도전 과제 D 완료
 */

@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(
            DataSource dataSource
    ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member findMemberById(Long memberId) {
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
                        rs.getTimestamp("create_date_time").toLocalDateTime(),
                        rs.getTimestamp("updated_date_time").toLocalDateTime()
                );
            }
        };
    }

    @Override
    public int updateMemberName(Long memberId, String name) {

        return jdbcTemplate.update(
                "UPDATE planner_challenge_members SET " +
                        "name = ?" +
                        "WHERE id = ?",

                name,
                memberId
        );
    }
}
