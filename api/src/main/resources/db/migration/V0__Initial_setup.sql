CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id               UUID PRIMARY KEY             DEFAULT uuid_generate_v4(),
    first_name       VARCHAR(75)         NOT NULL,
    last_name        VARCHAR(75)         NOT NULL,
    full_name        VARCHAR(151)        NOT NULL GENERATED ALWAYS AS (first_name || ' ' || last_name) STORED,
    email            VARCHAR(256) UNIQUE NOT NULL,
    account_status   VARCHAR(20)         NOT NULL DEFAULT 'ACTIVE' CHECK (account_status IN ('ACTIVE', 'PENDING', 'SUSPENDED', 'DELETED')),
    initial_handicap INTEGER             NOT NULL,
    api_key_hash     BYTEA UNIQUE,
    created_at       TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted          BOOLEAN             NOT NULL DEFAULT FALSE,
    deleted_at       TIMESTAMP                    DEFAULT NULL
);

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_delete ON users (deleted);

CREATE OR REPLACE FUNCTION update_user_account_status()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.deleted THEN
        NEW.account_status := 'DELETED';
    ELSIF NEW.account_status = 'DELETED' THEN
        NEW.deleted := true;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_account_status
    BEFORE INSERT OR UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_user_account_status();

CREATE TABLE courses
(
    id         UUID PRIMARY KEY     DEFAULT uuid_generate_v4(),
    name       VARCHAR(75) NOT NULL,
    location   VARCHAR(75) NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted    BOOLEAN     NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP            DEFAULT NULL
);

CREATE INDEX idx_courses_deleted ON courses (deleted);

CREATE TABLE tees
(
    id            UUID PRIMARY KEY     DEFAULT uuid_generate_v4(),
    course_id     UUID        NOT NULL REFERENCES courses (id) ON DELETE CASCADE,
    name          VARCHAR(15) NOT NULL,
    course_rating DECIMAL     NOT NULL,
    slope_rating  INTEGER     NOT NULL,
    created_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted       BOOLEAN     NOT NULL DEFAULT FALSE,
    deleted_at    TIMESTAMP            DEFAULT NULL
);

CREATE INDEX idx_tees_course_id ON tees (course_id);
CREATE INDEX idx_tees_deleted ON tees (deleted);

CREATE TABLE holes
(
    id          UUID PRIMARY KEY   DEFAULT uuid_generate_v4(),
    course_id   UUID      NOT NULL REFERENCES courses (id) ON DELETE CASCADE,
    hole_number INTEGER   NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted     BOOLEAN   NOT NULL DEFAULT FALSE,
    deleted_at  TIMESTAMP          DEFAULT NULL
);

CREATE INDEX idx_holes_course_id ON holes (course_id);
CREATE INDEX idx_holes_deleted ON holes (deleted);

CREATE TABLE holes_info
(
    id         UUID PRIMARY KEY   DEFAULT uuid_generate_v4(),
    tee_id     UUID      NOT NULL REFERENCES tees (id) ON DELETE CASCADE,
    hole_id    UUID      NOT NULL REFERENCES holes (id) ON DELETE CASCADE,
    yardage    INTEGER   NOT NULL,
    par        INTEGER   NOT NULL,
    handicap   INTEGER   NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted    BOOLEAN   NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP          DEFAULT NULL
);

CREATE INDEX idx_holes_info_tee_id ON holes_info (tee_id);
CREATE INDEX idx_holes_info_hole_id ON holes_info (hole_id);
CREATE INDEX idx_holes_info_deleted ON holes_info (deleted);

CREATE TABLE rounds
(
    id         UUID PRIMARY KEY   DEFAULT uuid_generate_v4(),
    user_id    UUID      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    course_id  UUID      NOT NULL REFERENCES courses (id) ON DELETE CASCADE,
    tee_id     UUID      NOT NULL REFERENCES tees (id) ON DELETE CASCADE,
    date       DATE      NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted    BOOLEAN   NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP          DEFAULT NULL
);

CREATE INDEX idx_rounds_deleted ON rounds (deleted);

CREATE TABLE scores
(
    id             UUID PRIMARY KEY   DEFAULT uuid_generate_v4(),
    round_id       UUID      NOT NULL REFERENCES rounds (id) ON DELETE CASCADE,
    hole_id        UUID      NOT NULL REFERENCES holes (id),
    score          INTEGER   NOT NULL,
    adjusted_score INTEGER   NOT NULL,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted        BOOLEAN   NOT NULL DEFAULT FALSE,
    deleted_at     TIMESTAMP          DEFAULT NULL
);

CREATE INDEX idx_scores_round_id ON scores (round_id);
CREATE INDEX idx_scores_hole_id ON scores (hole_id);
CREATE INDEX idx_scores_deleted ON scores (deleted);

CREATE VIEW cumulative_score AS
SELECT r.id,
       r.user_id,
       r.course_id,
       r.tee_id,
       r.date,
       r.created_at,
       r.updated_at,
       SUM(CASE WHEN h.hole_number BETWEEN 1 AND 9 THEN s.score ELSE 0 END)            AS front9_score,
       SUM(CASE WHEN h.hole_number BETWEEN 1 AND 9 THEN s.adjusted_score ELSE 0 END)   AS front9_score_adjusted,
       SUM(CASE WHEN h.hole_number BETWEEN 10 AND 18 THEN s.score ELSE 0 END)          AS back9_score,
       SUM(CASE WHEN h.hole_number BETWEEN 10 AND 18 THEN s.adjusted_score ELSE 0 END) AS back9_score_adjusted,
       SUM(s.score)                                                                    AS total_score,
       SUM(s.adjusted_score)                                                           AS total_score_adjusted
FROM rounds r
         JOIN scores s ON s.round_id = r.id
         JOIN holes h ON h.id = s.hole_id
WHERE 1 = 1
  AND r.deleted = FALSE
  AND s.deleted = FALSE
  AND h.deleted = FALSE
GROUP BY r.id,
         r.user_id,
         r.course_id,
         r.tee_id,
         r.date,
         r.created_at,
         r.updated_at;
