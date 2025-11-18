CREATE TABLE IF NOT EXISTS user_metadata (
    user_id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(512),
    updated_at BIGINT
);
