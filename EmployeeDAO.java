package com.example.demo1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private Connection connection;

    public EmployeeDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee (name, ssn, title, division, salary) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getSsn());
        statement.setString(3, employee.getTitle());
        statement.setString(4, employee.getDivision());
        statement.setDouble(5, employee.getSalary());
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            employee.setEmpId(generatedKeys.getInt(1));
        }
    }

    public void updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET name=?, ssn=?, title=?, division=?, salary=? WHERE empId=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getSsn());
        statement.setString(3, employee.getTitle());
        statement.setString(4, employee.getDivision());
        statement.setDouble(5, employee.getSalary());
        statement.setInt(6, employee.getEmpId());
        statement.executeUpdate();
    }

    public void deleteEmployee(int empId) throws SQLException {
        String sql = "DELETE FROM employee WHERE empId=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, empId);
        statement.executeUpdate();
    }

    public Employee getEmployee(int empId) throws SQLException {
        String sql = "SELECT * FROM employee WHERE empId=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, empId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new Employee(
                    resultSet.getInt("empId"),
                    resultSet.getString("name"),
                    resultSet.getString("ssn"),
                    resultSet.getString("title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary")
            );
        }
        return null;
    }

    public List<Employee> getAllEmployees() throws SQLException {
        String sql = "SELECT * FROM employee";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(new Employee(
                    resultSet.getInt("empId"),
                    resultSet.getString("name"),
                    resultSet.getString("ssn"),
                    resultSet.getString("title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary")
            ));
        }
        return employees;
    }

    public List<Employee> searchByNameOrId(String keyword) throws SQLException {
        String sql;

        // Determine if the keyword is an Employee ID (numeric) or Name
        if (keyword.matches("\\d+")) {
            sql = "SELECT * FROM employee WHERE empId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(keyword));
            return executeEmployeeQuery(statement);
        } else {
            sql = "SELECT * FROM employee WHERE name LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            String query = "%" + keyword + "%";
            statement.setString(1, query);
            return executeEmployeeQuery(statement);
        }
    }

    public List<Employee> searchBySsn(String ssn) throws SQLException {
        String sql = "SELECT * FROM employee WHERE ssn LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        String query = "%" + ssn + "%";
        statement.setString(1, query);
        return executeEmployeeQuery(statement);
    }

    private List<Employee> executeEmployeeQuery(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(new Employee(
                    resultSet.getInt("empId"),
                    resultSet.getString("name"),
                    resultSet.getString("ssn"),
                    resultSet.getString("title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary")
            ));
        }
        return employees;
    }

    public void updateSalaryByPercentage(double percentage, double minSalary, double maxSalary, boolean isIncrease) throws SQLException {
        String operation = isIncrease ? "+" : "-";
        String sql = String.format("UPDATE employee SET salary = salary %s (salary * ? / 100) WHERE salary >= ? AND salary < ?", operation);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, percentage);
        statement.setDouble(2, minSalary);
        statement.setDouble(3, maxSalary);
        statement.executeUpdate();
    }
}
