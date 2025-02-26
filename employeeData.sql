CREATE DATABASE employeeData;
USE employeeData;
CREATE TABLE employee (
    empId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    ssn VARCHAR(9),
    title VARCHAR(50),
    division VARCHAR(50),
    salary DOUBLE
);
CREATE TABLE pay_statement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    empId INT,
    pay_date DATE,
    amount DOUBLE,
    FOREIGN KEY (empId) REFERENCES employee(empId)
);

INSERT INTO employee (name, ssn, title, division, salary) VALUES
('Alice Johnson', '123456789', 'Manager', 'HR', 75000),
('Bob Smith', '987654321', 'Developer', 'IT', 60000),
('Charlie Brown', '456789123', 'Analyst', 'Finance', 65000),
('Diana Prince', '321654987', 'Designer', 'Marketing', 70000),
('Eve Adams', '654987321', 'Support', 'IT', 50000);