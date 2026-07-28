CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(100) NOT NULL,
    member_id BIGINT NOT NULL,
    CONSTRAINT fk_role_member FOREIGN KEY (member_id) REFERENCES household_member (id)
);
