CREATE TABLE following (
       id BIGINT PRIMARY KEY,
       follower_id BIGINT NOT NULL,
       followee_id BIGINT NOT NULL,
       created_at BIGINT NOT NULL,
       CONSTRAINT unique_follow UNIQUE (follower_id, followee_id)
);
