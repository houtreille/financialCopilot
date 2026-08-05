CREATE TABLE expense_category (
    id BIGSERIAL PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    color VARCHAR(50)
);

ALTER TABLE expense
    ADD COLUMN category_id BIGINT REFERENCES expense_category (id);
