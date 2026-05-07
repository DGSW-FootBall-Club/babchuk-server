-- DAuth 전환에 따른 users 테이블 스키마 변경.
-- 기존 자체 로그인 시 적재된 row와 그에 연관된 매치/공지/참여 데이터를 모두 비우고 컬럼을 재정의한다.

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE match_participants;
TRUNCATE TABLE matches;
TRUNCATE TABLE announcements;
TRUNCATE TABLE users;

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE users
    DROP INDEX username,
    DROP COLUMN password,
    DROP COLUMN nickname,
    MODIFY COLUMN username VARCHAR(255) NULL,
    MODIFY COLUMN profile_image LONGTEXT NULL,
    MODIFY COLUMN skill_type VARCHAR(50) NULL,
    MODIFY COLUMN gender     VARCHAR(50) NULL,
    ADD COLUMN public_id      VARCHAR(255) NULL UNIQUE AFTER id,
    ADD COLUMN student_id     VARCHAR(10)  NOT NULL UNIQUE AFTER public_id,
    ADD COLUMN name           VARCHAR(255) NOT NULL AFTER student_id,
    ADD COLUMN phone          VARCHAR(50)  NULL,
    ADD COLUMN status         VARCHAR(50)  NULL,
    ADD COLUMN role           VARCHAR(50)  NULL,
    ADD COLUMN room           INT          NOT NULL,
    ADD COLUMN student_number INT          NOT NULL;
