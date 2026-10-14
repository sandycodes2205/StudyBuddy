CREATE TABLE IF NOT EXISTS subjects (
    subject_id INT PRIMARY KEY AUTO_INCREMENT,
    subject_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    target_hours DECIMAL(6,2) DEFAULT 0
);

CREATE TABLE IF NOT EXISTS study_tasks (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    subject_id INT NOT NULL,
    due_date DATE,
    priority ENUM('LOW', 'MEDIUM', 'HIGH')
        NOT NULL DEFAULT 'MEDIUM',
    status ENUM('PENDING', 'COMPLETED')
        NOT NULL DEFAULT 'PENDING',

    CONSTRAINT fk_task_subject
        FOREIGN KEY (subject_id)
        REFERENCES subjects(subject_id)
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS study_sessions (
    session_id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id INT NOT NULL,
    duration_minutes INT NOT NULL,
    session_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_session_subject
        FOREIGN KEY (subject_id)
        REFERENCES subjects(subject_id)
        ON DELETE RESTRICT
);
