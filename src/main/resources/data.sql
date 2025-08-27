INSERT INTO EMPLOYEE(id, name, email) VALUES
(1, 'Employee test 1', 'employee1@mail.com'),
(2, 'Employee test 2', 'employee2@mail.com'),
(3, 'Employee test 3', 'employee3@mail.com'),
(4, 'Employee test 4', 'employee4@mail.com'),
(5, 'Employee test 5', 'employee5@mail.com');

INSERT INTO PROJECT(id, project_name, description, start_date, end_date) VALUES
(1, 'Project A', 'Description for Project A', '2023-01-01', '2025-06-30'),
(2, 'Project B', 'Description for Project B', '2023-02-15', '2025-08-15'),
(3, 'Project C', 'Description for Project C', '2023-03-01', '2025-09-30'),
(4, 'Project D', 'Description for Project D', '2023-04-10', '2025-10-10'),
(5, 'Project E', 'Description for Project E', '2023-05-20', '2025-11-20');

INSERT INTO TASK(id, task_name, description, status, due_date, priority, assigned_to, project_id, version) VALUES
(1, 'Task 1', 'Description for Task 1', 'PENDING', '2025-09-04', 1, 1, 1, 0),
(2, 'Task 2', 'Description for Task 2', 'IN_PROGRESS', '2025-08-04', 2, 2, 1, 0),
(3, 'Task 3', 'Description for Task 3', 'COMPLETED', '2025-08-29', 4, 3, 2, 0),
(4, 'Task 4', 'Description for Task 4', 'PENDING', '2025-08-29', 1, 4, 2, 0),
(5, 'Task 5', 'Description for Task 5', 'IN_PROGRESS', '2025-09-10', 3, 5, 3, 0),
(6, 'Task 6', 'Description for Task 6', 'PENDING', '2025-06-15', 1, 2, 1, 0),
(7, 'Task 7', 'Description for Task 7', 'IN_PROGRESS', '2025-05-01', 2, 2, 1, 0),
(8, 'Task 8', 'Description for Task 8', 'COMPLETED', '2025-05-30', 4, 3, 1, 0),
(9, 'Task 9', 'Description for Task 9', 'PENDING', '2025-08-28', 1, 4, 2, 0),
(10, 'Task 10', 'Description for Task 10', 'IN_PROGRESS', '2025-09-01', 3, 5, 3, 0);

-- Max existing for TASK IS 10
ALTER TABLE TASK ALTER COLUMN ID RESTART WITH 11;