CREATE TABLE expense_sheet (
    id BIGSERIAL PRIMARY KEY,
    sheet_name VARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL REFERENCES household_member (id)
);

ALTER TABLE expense
    ADD COLUMN expense_sheet_id BIGINT REFERENCES expense_sheet (id);
