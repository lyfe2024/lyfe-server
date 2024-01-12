create table topic
(
    id         bigint auto_increment
        primary key,
    content    varchar(255) null,
    applied_at datetime(6)  null,

    created_at datetime(6)  null,
    updated_at datetime(6)  null
);

create table user
(
    id                   bigint auto_increment
        primary key,
    email                varchar(255)                 null,
    hashed_password      varchar(255)                 null,
    nickname             varchar(255)                 null,
    role                 enum ('ADMIN', 'USER')       null,
    user_status          enum ('ACTIVE', 'WITHDRAWN') null,
    fcm_registration     bit                          not null,
    notification_consent bit                          not null,
    created_at           datetime(6)                  null,
    updated_at           datetime(6)                  null,
    withdrawn_at         datetime(6)                  null
);


CREATE TABLE profile_image
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    url        VARCHAR(1024) NOT NULL,
    width      INT,
    height     INT,
    user_id    BIGINT,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

create table board
(
    id         bigint auto_increment
        primary key,
    title      varchar(255)                    null,
    content    varchar(255)                    null,
    picture    json                            null,
    board_type enum ('BOARD', 'BOARD_PICTURE') null,
    topic_id   bigint                          null,
    user_id    bigint                          null,
    created_at datetime(6)                     null,
    updated_at datetime(6)                     null,
    deleted_at datetime(6)                     null,
    constraint fk_board_topic_id
        foreign key (topic_id) references topic (id),
    constraint fk_board_user_id
        foreign key (user_id) references user (id)
);



CREATE TABLE board_image
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    url        VARCHAR(1024) NOT NULL,
    width      INT,
    height     INT,
    board_id   BIGINT,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    FOREIGN KEY (board_id) REFERENCES board (id)
);


create table whisky
(
    id         bigint auto_increment
        primary key,
    board_id   bigint      null,
    user_id    bigint      null,
    created_at datetime(6) null,
    constraint fk_whisky_board_id
        foreign key (board_id) references board (id),
    constraint fk_whisky_user_id
        foreign key (user_id) references user (id)
);




create table comment
(
    id               bigint auto_increment
        primary key,
    content          varchar(255) null,
    comment_group_id bigint       null,
    board_id         bigint       null,
    user_id          bigint       null,
    created_at       datetime(6)  null,
    updated_at       datetime(6)  null,
    deleted_at       datetime(6)  null,
    constraint FK8kcum44fvpupyw6f5baccx25c
        foreign key (user_id) references user (id),
    constraint FKlij9oor1nav89jeat35s6kbp1
        foreign key (board_id) references board (id)
);


create table feedback
(
    id         bigint auto_increment
        primary key,
    user_id    bigint               null,
    content    varchar(255)         null,
    checked    tinyint(1) default 0 null,
    created_at datetime(6)          null,
    constraint fk_feedback_user_id
        foreign key (user_id) references user (id)
);


create table notification_history
(
    id                bigint auto_increment
        primary key,
    content           varchar(255)                                         null,
    notification_type enum ('BOARD', 'BOARD_PICTURE', 'COMMENT', 'WHISKY') null,
    user_id           bigint                                               null,
    created_at        datetime(6)                                          null,
    updated_at        datetime(6)                                          null,
    constraint fk_notification_history_user_id
        foreign key (user_id) references user (id)
);

create table policy
(
    id      bigint auto_increment
        primary key,
    title   varchar(255) null,
    content varchar(255) null,
    version varchar(255) null
);





