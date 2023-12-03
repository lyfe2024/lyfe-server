
INSERT INTO user (fcm_registration, notification_consent, visibility, created_at, updated_at, withdrawn_at, email, hashed_password, nickname, role, user_status)
VALUES (1, 0, 1, NOW(), NOW(), NULL, 'user2@example.com', 'hashedpassword2', 'nickname2', 'ADMIN', 'ACTIVE');

INSERT INTO user (fcm_registration, notification_consent, visibility, created_at, updated_at, withdrawn_at, email, hashed_password, nickname, role, user_status)
VALUES (1, 1, 1, NOW(), NOW(), NULL, 'user1@example.com', 'hashedpassword1', 'nickname1', 'USER', 'ACTIVE');



INSERT INTO topic (visibility, applied_at, created_at, updated_at, content)
VALUES (1, NOW(), NOW(), NOW(), 'Topic Content 1');

INSERT INTO topic (visibility, applied_at, created_at, updated_at, content)
VALUES (1, NOW(), NOW(), NOW(), 'Topic Content 2');


INSERT INTO image (height, visibility, width, board_id, created_at, updated_at, user_id, type, url)
VALUES (200, 1, 300, null, NOW(), NOW(), 1, 'BOARD', 'http://example.com/image1.jpg');
