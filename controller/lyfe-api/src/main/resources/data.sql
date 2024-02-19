INSERT INTO policy (content, policy_type, title, version)
VALUES ('개인정보 처리 방침에 대한 내용', 'PERSONAL_INFO_AGREEMENT', '개인정보 처리 방침', '1.0');

INSERT INTO policy (content, policy_type, title, version)
VALUES ('서비스 이용 약관에 대한 내용', 'TERM', '서비스 이용 약관', '1.0');

INSERT INTO user (fcm_registration, notification_consent, created_at, updated_at, withdrawn_at, email,
                  hashed_password, nickname, role, user_status , profile_url)
VALUES (1, 0, NOW(), NOW(), NULL, 'user2@example.com', 'hashedpassword2', 'nickname2', 'ADMIN', 'ACTIVE', 'http://example.com/profile2.jpg');


INSERT INTO topic ( applied_at, created_at, updated_at, content)
VALUES (NOW(), NOW(), NOW(), 'Topic Content 1');

INSERT INTO topic ( applied_at, created_at, updated_at, content)
VALUES ( NOW(), NOW(), NOW(), 'Topic Content 2');

INSERT INTO board ( created_at, updated_at, user_id, topic_id, board_type, content, title , image_url)
VALUES ( NOW(), NOW(), 1, 1, 'BOARD', 'Conent', 'title' , null);

INSERT INTO board ( created_at, updated_at, user_id, topic_id, board_type, content, title , image_url)
VALUES ( NOW(), NOW(), 1, 1, 'BOARD_PICTURE', 'Conent', 'title' , 'http://example.com/image1.jpg');

INSERT INTO comment ( board_id, user_id, content, created_at, updated_at)
VALUES ( 1, 1, '여기에 코멘트 내용을 입력하세요', NOW(), NOW());

INSERT INTO whisky (board_id, user_id, created_at)
VALUES (1, 1, NOW());


INSERT INTO board (created_at, updated_at, user_id, topic_id, board_type, content, title, image_url) VALUES
                                                                                                         (NOW(), NOW(), 1, 1, 'BOARD', 'Content 1', 'Title 1', null),
                                                                                                         (NOW(), NOW(), 1, 1, 'BOARD', 'Content 2', 'Title 2', null),
                                                                                                         (NOW(), NOW(), 1, 1, 'BOARD', 'Content 3', 'Title 3', null),
                                                                                                         (NOW(), NOW(), 1, 1, 'BOARD', 'Content 4', 'Title 4', null),
                                                                                                         (NOW(), NOW(), 1, 1, 'BOARD', 'Content 5', 'Title 5', null),
                                                                                                         (NOW(), NOW(), 1, 1, 'BOARD_PICTURE', 'Content 6', 'Title 6', 'http://example.com/image6.jpg'),
                                                                                                         (NOW(), NOW(), 1, 1, 'BOARD_PICTURE', 'Content 7', 'Title 7', 'http://example.com/image7.jpg'),
                                                                                                         (NOW(), NOW(), 1, 1, 'BOARD_PICTURE', 'Content 8', 'Title 8', 'http://example.com/image8.jpg'),
                                                                                                         (NOW(), NOW(), 1, 1, 'BOARD_PICTURE', 'Content 9', 'Title 9', 'http://example.com/image9.jpg'),
                                                                                                         (NOW(), NOW(), 1, 1, 'BOARD_PICTURE', 'Content 10', 'Title 10', 'http://example.com/image10.jpg');

INSERT INTO comment (board_id, user_id, content, created_at, updated_at) VALUES
                                                                             (1, 1, 'Comment 1 for Board 1', NOW(), NOW()),
                                                                             (1, 1, 'Comment 2 for Board 2', NOW(), NOW()),
                                                                             (1, 1, 'Comment 3 for Board 3', NOW(), NOW()),
                                                                             (1, 1, 'Comment 4 for Board 4', NOW(), NOW()),
                                                                             (1, 1, 'Comment 5 for Board 5', NOW(), NOW()),
                                                                             (2, 1, 'Comment 6 for Board 6', NOW(), NOW()),
                                                                             (2, 1, 'Comment 7 for Board 7', NOW(), NOW()),
                                                                             (2, 1, 'Comment 8 for Board 8', NOW(), NOW()),
                                                                             (3, 1, 'Comment 9 for Board 9', NOW(), NOW()),
                                                                             (3, 1, 'Comment 10 for Board 10', NOW(), NOW());

INSERT INTO whisky (board_id, user_id, created_at) VALUES
                                                       (1, 1, NOW()),
                                                       (1, 1, NOW()),
                                                       (1, 1, NOW()),
                                                       (1, 1, NOW()),
                                                       (2, 1, NOW()),
                                                       (2, 1, NOW()),
                                                       (2, 1, NOW()),
                                                       (2, 1, NOW()),
                                                       (3, 1, NOW()),
                                                       (3, 1, NOW());