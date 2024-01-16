CREATE DATABASE IF NOT EXISTS byport_task;
USE byport_task;

CREATE TABLE IF NOT EXISTS employee(
employee_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
full_name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS Task (
task_id BIGINT PRIMARY KEY AUTO_INCREMENT,
task_name VARCHAR(30) NOT NULL,
start_date DATE,
end_date DATE,
implementation_date DATE,
is_completed BOOLEAN NOT NULL DEFAULT FALSE,
employee_id BIGINT,
FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
);
INSERT INTO Employee (full_name) VALUES
('Иванов И.И.'),
('Петров Д.В.'),
('Сидоров А.А.');

INSERT INTO Task (task_name, start_date, end_date, implementation_date, is_completed, employee_id) VALUES
('Task 1', '2009-01-01', '2009-01-10', '2009-01-05', TRUE, 1),
('Task 2', '2009-02-01', '2009-02-15', '2009-02-02', FALSE,1),
('Task 3', '2009-03-01', '2009-03-10', '2009-03-03', FALSE,1),
('Task 4', '2009-01-15', '2009-01-30', '2009-01-20', TRUE, 1),

('Task 1', '2009-01-01', '2009-01-10', '2009-01-05', FALSE,2),
('Task 2', '2009-02-01', '2009-02-15', '2009-02-02', FALSE,2),
('Task 3', '2009-03-01', '2009-03-10', '2009-03-03', TRUE, 2),
('Task 4', '2009-02-15', '2009-03-15', '2009-02-20', TRUE, 2),

('Task 1', '2009-01-01', '2009-01-10', '2009-01-05', TRUE, 3),
('Task 2', '2009-02-01', '2009-02-15', '2009-02-02', TRUE, 3),
('Task 3', '2009-03-01', '2009-03-10', '2009-03-03', FALSE,3);