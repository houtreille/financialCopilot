ALTER TABLE household_member
    ADD COLUMN username VARCHAR(100),
    ADD COLUMN password_hash VARCHAR(255);

ALTER TABLE household_member
    ADD CONSTRAINT uk_household_member_username UNIQUE (username);
