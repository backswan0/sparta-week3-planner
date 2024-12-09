create table planner
(
    id              bigint auto_increment comment '일정 식별자'
        primary key,
    name            varchar(50)  not null comment '작성자명',
    password        varchar(50)  not null comment '비밀번호',
    plannedDate     date         not null comment '일정 날짜',
    title           varchar(50)  not null comment '일정 제목',
    task            varchar(100) null comment '일정 내용',
    createdDateTime datetime     not null comment '생성 날짜',
    updatedDateTime datetime     not null comment '수정 날짜'
);

create table planner_challenge_members
(
    id                bigint auto_increment comment '사용자 식별자'
        primary key,
    name              varchar(50) not null comment '사용자명',
    email             varchar(50) not null comment '이메일',
    created_date_time datetime    not null comment '사용자 등록일',
    updated_date_time datetime    not null comment '사용자 수정일'
);

CREATE TABLE planner_challenge_plans
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    member_id         BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES planner_challenge_members (id),
    password          VARCHAR(50)  NOT NULL COMMENT '비밀번호',
    planned_date      DATE         NOT NULL COMMENT '일정 날짜',
    title             VARCHAR(50)  NOT NULL COMMENT '일정 제목',
    task              VARCHAR(100) NULL COMMENT '일정 내용',
    created_date_time DATETIME     NOT NULL COMMENT '일정 생성일',
    updated_date_time DATETIME     NOT NULL COMMENT '일정 수정일'
)