INSERT INTO policy (content, policy_type, title, version)
VALUES ('개인정보 처리 방침에 대한 내용', 'PERSONAL_INFO_AGREEMENT', '개인정보 처리 방침', '1.0');

INSERT INTO policy (content, policy_type, title, version)
VALUES ('서비스 이용 약관에 대한 내용', 'TERM', '서비스 이용 약관', '1.0');

INSERT INTO user (fcm_registration, notification_consent, visibility, created_at, updated_at, withdrawn_at, email,
                  hashed_password, nickname, role, user_status)
VALUES (1, 0, 1, NOW(), NOW(), NULL, 'user2@example.com', 'hashedpassword2', 'nickname2', 'ADMIN', 'ACTIVE');


INSERT INTO topic (visibility, applied_at, created_at, updated_at, content)
VALUES (1, NOW(), NOW(), NOW(), 'Topic Content 1');

INSERT INTO topic (visibility, applied_at, created_at, updated_at, content)
VALUES (1, NOW(), NOW(), NOW(), 'Topic Content 2');

INSERT INTO board (visibility, created_at, updated_at, user_id, topic_id, board_type, content, title)
VALUES (1, NOW(), NOW(), 1, 1, 'BOARD', 'Conent', 'title');


INSERT INTO image (height, visibility, width, board_id, created_at, updated_at, user_id, type, url)
VALUES (200, 1, 300, null, NOW(), NOW(), 1, 'BOARD', 'http://example.com/image1.jpg');


INSERT INTO comment (visibility, board_id, user_id, content, created_at, updated_at)
VALUES (1, 1, 1, '여기에 코멘트 내용을 입력하세요', NOW(), NOW());

INSERT INTO whisky (board_id, user_id, created_at)
VALUES (1, 1, NOW());
