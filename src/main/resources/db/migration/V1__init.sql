CREATE TABLE users
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    profile_image VARCHAR(255) NOT NULL,
    username      VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    nickname      VARCHAR(255) NOT NULL,
    grade         INT          NOT NULL,
    skill_type    VARCHAR(50)  NOT NULL,
    gender        VARCHAR(50)  NOT NULL,
    created_at    DATETIME,
    updated_at    DATETIME
);

CREATE TABLE matches
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(100) NOT NULL,
    match_at          DATETIME     NOT NULL,
    team_size         INT          NOT NULL,
    duration_minutes  INT          NOT NULL,
    status            VARCHAR(50)  NOT NULL,
    team_a_captain_id BIGINT,
    team_b_captain_id BIGINT,
    author_id         BIGINT       NOT NULL,
    created_at        DATETIME,
    updated_at        DATETIME,
    FOREIGN KEY (team_a_captain_id) REFERENCES users (id),
    FOREIGN KEY (team_b_captain_id) REFERENCES users (id),
    FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE match_participants
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    match_id   BIGINT      NOT NULL,
    user_id    BIGINT      NOT NULL,
    team_type  VARCHAR(50) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (match_id) REFERENCES matches (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE announcements
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    content    VARCHAR(100) NOT NULL,
    image      VARCHAR(255) NOT NULL,
    author_id  BIGINT       NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (author_id) REFERENCES users (id)
);