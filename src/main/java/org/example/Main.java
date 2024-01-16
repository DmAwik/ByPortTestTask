package org.example;

import org.example.Application.App;
import org.example.dao.EmployeeDao;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        EmployeeDao employeeDao = new EmployeeDao();

        SwingUtilities.invokeLater(() -> {
            App app = new App(employeeDao);
            app.createApplication();
        });
    }
}
