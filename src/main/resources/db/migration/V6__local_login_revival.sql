-- 자체 로그인 부활.
-- DAuth로 채워졌던 username은 이제 로컬 가입자 전용 필드로 의미가 바뀌므로 비워둔다.
UPDATE users SET username = NULL WHERE public_id IS NOT NULL;

ALTER TABLE users
    MODIFY COLUMN username VARCHAR(50) NULL,
    ADD COLUMN password VARCHAR(255) NULL,
    ADD CONSTRAINT uk_users_username UNIQUE (username);
