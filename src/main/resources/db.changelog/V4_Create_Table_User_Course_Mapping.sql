CREATE TABLE user_course_mapping(
    user_id UUID NOT NULL,
    course_id UUID NOT NULL,
    status VARCHAR(255) CHECK(
    status IN ('ACTIVE', 'PENDING', 'INACTIVE')
    ) NOT NULL,

    PRIMARY KEY (user_id, course_id),

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);