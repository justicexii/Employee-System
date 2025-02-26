package com.example.demo1;

import javafx.beans.property.*;

public class Employee {
    private final IntegerProperty empId;
    private final StringProperty name;
    private final StringProperty ssn;
    private final StringProperty title;
    private final StringProperty division;
    private final DoubleProperty salary;

    public Employee(int empId, String name, String ssn, String title, String division, double salary) {
        this.empId = new SimpleIntegerProperty(empId);
        this.name = new SimpleStringProperty(name);
        this.ssn = new SimpleStringProperty(ssn);
        this.title = new SimpleStringProperty(title);
        this.division = new SimpleStringProperty(division);
        this.salary = new SimpleDoubleProperty(salary);
    }

    public int getEmpId() {
        return empId.get();
    }

    public IntegerProperty empIdProperty() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId.set(empId);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSsn() {
        return ssn.get();
    }

    public StringProperty ssnProperty() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn.set(ssn);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDivision() {
        return division.get();
    }

    public StringProperty divisionProperty() {
        return division;
    }

    public void setDivision(String division) {
        this.division.set(division);
    }

    public double getSalary() {
        return salary.get();
    }

    public DoubleProperty salaryProperty() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary.set(salary);
    }
}
