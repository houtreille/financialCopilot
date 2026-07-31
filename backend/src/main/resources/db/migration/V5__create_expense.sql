CREATE TABLE expense (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(19, 2) NOT NULL,
    label VARCHAR(255) NOT NULL,
    month VARCHAR(9) NOT NULL,
    expense_version INTEGER NOT NULL,
    expense_version_id INTEGER NOT NULL,
    expense_type VARCHAR(50) NOT NULL
);
