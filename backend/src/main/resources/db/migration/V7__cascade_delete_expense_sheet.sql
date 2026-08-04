ALTER TABLE expense
    DROP CONSTRAINT expense_expense_sheet_id_fkey;

ALTER TABLE expense
    ADD CONSTRAINT expense_expense_sheet_id_fkey
        FOREIGN KEY (expense_sheet_id) REFERENCES expense_sheet (id) ON DELETE CASCADE;
