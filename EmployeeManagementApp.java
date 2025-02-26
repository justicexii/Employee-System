package com.example.demo1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;

public class EmployeeManagementApp extends Application {
    private EmployeeDAO employeeDAO;
    private TableView<Employee> employeeTable;
    private ObservableList<Employee> employeeData;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management System");

        // Initialize the DAO and the employee data list
        try {
            employeeDAO = new EmployeeDAO();
            employeeData = FXCollections.observableArrayList(employeeDAO.getAllEmployees());
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Create input fields
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField ssnField = new TextField();
        ssnField.setPromptText("SSN");
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField divisionField = new TextField();
        divisionField.setPromptText("Division");
        TextField salaryField = new TextField();
        salaryField.setPromptText("Salary");

        // Create search fields
        TextField keywordField = new TextField();
        keywordField.setPromptText("Search by Name or Emp ID");
        TextField ssnSearchField = new TextField();
        ssnSearchField.setPromptText("Search by SSN");

        // Create buttons
        Button addButton = new Button("Add Employee");
        Button updateButton = new Button("Update Employee");
        Button deleteButton = new Button("Delete Employee");
        Button searchButton = new Button("Search by Name or Emp ID");
        Button ssnSearchButton = new Button("Search by SSN");
        Button salaryUpdateButton = new Button("Update Salary by Percentage");

        // Create fields for salary update
        TextField percentageField = new TextField();
        percentageField.setPromptText("Percentage");
        TextField minSalaryField = new TextField();
        minSalaryField.setPromptText("Min Salary");
        TextField maxSalaryField = new TextField();
        maxSalaryField.setPromptText("Max Salary");
        RadioButton increaseRadio = new RadioButton("Increase");
        RadioButton decreaseRadio = new RadioButton("Decrease");
        ToggleGroup toggleGroup = new ToggleGroup();
        increaseRadio.setToggleGroup(toggleGroup);
        decreaseRadio.setToggleGroup(toggleGroup);
        increaseRadio.setSelected(true);

        // Create TableView to display employee data
        employeeTable = new TableView<>();
        TableColumn<Employee, Integer> empIdColumn = new TableColumn<>("Employee ID");
        empIdColumn.setCellValueFactory(cellData -> cellData.getValue().empIdProperty().asObject());
        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        TableColumn<Employee, String> ssnColumn = new TableColumn<>("SSN");
        ssnColumn.setCellValueFactory(cellData -> cellData.getValue().ssnProperty());
        TableColumn<Employee, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        TableColumn<Employee, String> divisionColumn = new TableColumn<>("Division");
        divisionColumn.setCellValueFactory(cellData -> cellData.getValue().divisionProperty());
        TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());
        employeeTable.getColumns().addAll(empIdColumn, nameColumn, ssnColumn, titleColumn, divisionColumn, salaryColumn);
        employeeTable.setItems(employeeData);

        // Layout
        VBox inputLayout = new VBox(10, nameField, ssnField, titleField, divisionField, salaryField, addButton, updateButton, deleteButton, keywordField, searchButton, ssnSearchField, ssnSearchButton);
        inputLayout.setPadding(new Insets(10));

        VBox salaryUpdateLayout = new VBox(10, percentageField, minSalaryField, maxSalaryField, increaseRadio, decreaseRadio, salaryUpdateButton);
        salaryUpdateLayout.setPadding(new Insets(10));

        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(inputLayout);
        mainLayout.setRight(salaryUpdateLayout);
        mainLayout.setCenter(employeeTable);

        // Set button actions
        addButton.setOnAction(e -> addEmployee(nameField, ssnField, titleField, divisionField, salaryField));
        updateButton.setOnAction(e -> updateEmployee(nameField, ssnField, titleField, divisionField, salaryField));
        deleteButton.setOnAction(e -> deleteEmployee());
        searchButton.setOnAction(e -> searchByNameOrId(keywordField));
        ssnSearchButton.setOnAction(e -> searchBySsn(ssnSearchField));
        salaryUpdateButton.setOnAction(e -> updateSalariesByPercentage(percentageField, minSalaryField, maxSalaryField, increaseRadio.isSelected()));

        // Create scene and set stage
        Scene scene = new Scene(mainLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addEmployee(TextField nameField, TextField ssnField, TextField titleField, TextField divisionField, TextField salaryField) {
        try {
            Employee employee = new Employee(0, nameField.getText(), ssnField.getText(), titleField.getText(), divisionField.getText(), Double.parseDouble(salaryField.getText()));
            employeeDAO.addEmployee(employee);
            employeeData.add(employee);
            clearFields(nameField, ssnField, titleField, divisionField, salaryField);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee(TextField nameField, TextField ssnField, TextField titleField, TextField divisionField, TextField salaryField) {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                selectedEmployee.setName(nameField.getText());
                selectedEmployee.setSsn(ssnField.getText());
                selectedEmployee.setTitle(titleField.getText());
                selectedEmployee.setDivision(divisionField.getText());
                selectedEmployee.setSalary(Double.parseDouble(salaryField.getText()));
                employeeDAO.updateEmployee(selectedEmployee);
                employeeTable.refresh();
                clearFields(nameField, ssnField, titleField, divisionField, salaryField);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                employeeDAO.deleteEmployee(selectedEmployee.getEmpId());
                employeeData.remove(selectedEmployee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void searchByNameOrId(TextField keywordField) {
        try {
            String keyword = keywordField.getText();
            List<Employee> searchResults = employeeDAO.searchByNameOrId(keyword);
            if (!searchResults.isEmpty()) {
                employeeData.setAll(searchResults);
            } else {
                employeeData.clear(); // Clear the table if no employee is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchBySsn(TextField ssnSearchField) {
        try {
            String keyword = ssnSearchField.getText();
            List<Employee> searchResults = employeeDAO.searchBySsn(keyword);
            if (!searchResults.isEmpty()) {
                employeeData.setAll(searchResults);
            } else {
                employeeData.clear(); // Clear the table if no employee is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateSalariesByPercentage(TextField percentageField, TextField minSalaryField, TextField maxSalaryField, boolean isIncrease) {
        try {
            double percentage = Double.parseDouble(percentageField.getText());
            double minSalary = Double.parseDouble(minSalaryField.getText());
            double maxSalary = Double.parseDouble(maxSalaryField.getText());

            employeeDAO.updateSalaryByPercentage(percentage, minSalary, maxSalary, isIncrease);
            employeeData.setAll(employeeDAO.getAllEmployees()); // Refresh the data
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
