--Создание таблицы Курс
CREATE TABLE course
(
    id            UUID PRIMARY KEY,
    title         VARCHAR(255),
    description   TEXT,
    category      VARCHAR(255),
    level         VARCHAR(50) CHECK (
        level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED')
        ),
    duration      INTEGER,
    price         NUMERIC(10, 2) CHECK (price >= 0),
    instructor_id UUID,
        CONSTRAINT fk_course_instructor FOREIGN KEY (instructor_id)
        REFERENCES instructor(id)
        ON DELETE SET NULL
);

