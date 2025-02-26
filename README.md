# Employee Management System

## Purpose

The **Employee System Management** software is designed for **Company 'Z'** to allow users to interact with their employee database. Through a simple user interface, users can **generate reports**, **add/update/delete** information, and **query** data from the database.

This project aims to provide an intuitive and straightforward experience for employees to manage and view their company’s data.

## Scope

This software enables **Company 'Z'** to:
- Generate reports based on the data in the employee database.
- Add, update, and delete records from the database using SQL commands.
- Provide a basic user interface for ease of use.

## Overview

The system consists of:
- A **simple user interface (UI)** for interacting with the employee database.
- Integration with **DBeaver** for sending SQL commands to the database.
- The ability to **query, add, update, and delete** data in the company’s employee database.

---

## System Overview

The software allows users to perform the following tasks:
1. **Generate reports** based on existing data.
2. **Add, update, or delete** records from the database.
3. **Communicate with the database** through SQL commands, executed using **DBeaver**.

### System Architecture

The software follows a **client-server model** where:
- The **user interface** serves as the client.
- The **SQL commands** sent by the UI are executed on the **database**.
- **DBeaver** facilitates communication between the software and the database.

### Design Rationale

The design of this system is based on **simplicity** and **usability**, as requested by **Company 'Z'**. The system implements minimal features to perform the core tasks requested: querying, updating, and managing employee data. The **user interface** ensures that users can interact with the system intuitively, even without prior knowledge of SQL.

---

## Data Design

### Data Description

- **Input Data:** The system allows users to input data (such as employee details) through a text box in the UI.
- **Output Data:** After performing operations (add/update/delete), the program sends SQL commands to the database, which updates the employee data.
- The system allows users to **generate reports** based on specific queries executed on the database.

### Data Dictionary

- **Execution:** The process of interpreting user input and converting it into SQL commands.
- **Generation:** Generating reports based on SQL queries.
- **User Interface (UI):** A simple interface for input and displaying results from the database.

---

## Component Design

The main components of the software include:
- **User Interface (UI):** Allows the user to input data, select actions (add, update, delete, search), and view reports.
- **Backend Logic:** The program processes the user input, converts it into SQL commands, and interacts with the database.

---

## Human Interface Design

### User Interface (UI) Overview

The UI is designed to be **simple and user-friendly**, with the following features:
- **Text boxes** for user input (for search queries or employee details).
- **Action buttons** below the text boxes, allowing users to:
  - **Search**: Query the database for employee data.
  - **Add**: Insert new employee records into the database.
  - **Update**: Modify existing employee records.
  - **Delete**: Remove records from the database.

Once the user has selected an action (search, add, update, or delete), the relevant SQL command is executed, and the results are displayed accordingly.

---

## Requirements

- **Java**: This software is written in Java and requires Java Runtime Environment (JRE) 8 or later.
- **DBeaver**: Used for interacting with the database through SQL.
- **MySQL**: The database used to store and manage employee data.

---

## Setup & Installation

1. **Clone the repository** to your local machine:
   ```bash
   git clone https://github.com/justicexii/Employee-System.git
