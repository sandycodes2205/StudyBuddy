package com.studybuddy;

import javax.swing.SwingUtilities;

import com.studybuddy.dao.DBConnection;
import com.studybuddy.gui.DashboardFrame;

public class Main {

    public static void main(String[] args) {

        System.out.println(
            "MySQL connected: " + DBConnection.testConnection()
        );
        
        SwingUtilities.invokeLater(() -> {
            DashboardFrame frame = new DashboardFrame();
            frame.setVisible(true);
        });
    }
}
