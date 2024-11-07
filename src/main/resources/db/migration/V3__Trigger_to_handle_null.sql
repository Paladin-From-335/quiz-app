CREATE
OR REPLACE FUNCTION set_default_is_correct()
RETURNS TRIGGER AS $$
BEGIN
    IF
NEW.is_correct IS NULL THEN
        NEW.is_correct := false;
END IF;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER trigger_set_default_is_correct
    BEFORE INSERT OR
UPDATE ON quiz_answer_options
    FOR EACH ROW
    EXECUTE FUNCTION set_default_is_correct();