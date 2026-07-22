CREATE TABLE household_member (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    country_of_residence VARCHAR(100) NOT NULL,
    country_of_employment VARCHAR(100) NOT NULL,
    average_monthly_salary NUMERIC(19, 2) NOT NULL,
    current_cash NUMERIC(19, 2) NOT NULL
);
