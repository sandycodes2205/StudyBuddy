package com.studybuddy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DashboardPanel extends JPanel {
    
    private final Color backgroundColor = new Color(244, 246, 251);
    private final Color sidebarColor = new Color(23, 32, 51);
    private final Color accentColor = new Color(99, 102, 241);
    private final Color textColor = new Color(30, 41, 59);
    private final Color mutedColor = new Color(107, 119, 140);

    private JPanel createDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout(0, 25));
        dashboard.setBackground(backgroundColor);
        dashboard.setBorder(new EmptyBorder(30, 32, 30, 32));

        // Header
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel greeting = new JLabel("Good evening!");
        greeting.setFont(new Font("SansSerif", Font.BOLD, 29));
        greeting.setForeground(textColor);

        JLabel subtitle = new JLabel(
                "Ready to make progress today?"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(mutedColor);

        header.add(greeting);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);

        dashboard.add(header, BorderLayout.NORTH);

        // Main content
        JPanel content = new JPanel(new BorderLayout(0, 22));
        content.setOpaque(false);

        JPanel stats = new JPanel(new GridLayout(1, 4, 14, 0));
        stats.setOpaque(false);

        stats.add(createStatCard("TOTAL TASKS", "12", "Tasks created",
                new Color(99, 102, 241)));

        stats.add(createStatCard("COMPLETED", "7", "Tasks finished",
                new Color(16, 150, 116)));

        stats.add(createStatCard("PENDING", "5", "Tasks remaining",
                new Color(217, 119, 6)));

        stats.add(createStatCard("PROGRESS", "58%", "Overall completion",
                new Color(124, 83, 210)));

        content.add(stats, BorderLayout.NORTH);
        content.add(createTasksPanel(), BorderLayout.CENTER);

        dashboard.add(content, BorderLayout.CENTER);

        // Quick actions
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        actions.setOpaque(false);

        JButton addTask = new JButton("+ Add New Task");
        stylePrimaryButton(addTask);

        JButton startTimer = new JButton("Start Study Timer");
        styleSecondaryButton(startTimer);

        addTask.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "The task creation form will be added next."
                )
        );

        startTimer.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "The multithreaded study timer will be added next."
                )
        );

        actions.add(addTask);
        actions.add(startTimer);

        dashboard.add(actions, BorderLayout.SOUTH);

        return dashboard;
    }


    private JPanel createStatCard(
            String title, String value, String description, Color accent) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 4, 0, 0, accent
                ),
                new EmptyBorder(18, 16, 18, 12)
        ));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("SansSerif", Font.BOLD, 10));
        heading.setForeground(mutedColor);

        JLabel number = new JLabel(value);
        number.setFont(new Font("SansSerif", Font.BOLD, 29));
        number.setForeground(textColor);

        JLabel detail = new JLabel(description);
        detail.setFont(new Font("SansSerif", Font.PLAIN, 11));
        detail.setForeground(mutedColor);

        card.add(heading);
        card.add(Box.createVerticalStrut(13));
        card.add(number);
        card.add(Box.createVerticalStrut(7));
        card.add(detail);

        return card;
    }

    private JPanel createTasksPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(22, 22, 18, 22));

        JLabel heading = new JLabel("Upcoming Tasks");
        heading.setFont(new Font("SansSerif", Font.BOLD, 19));
        heading.setForeground(textColor);

        panel.add(heading, BorderLayout.NORTH);

        JPanel taskList = new JPanel();
        taskList.setBackground(Color.WHITE);
        taskList.setLayout(new BoxLayout(taskList, BoxLayout.Y_AXIS));

        taskList.add(createTaskRow(
                "Java inheritance practice",
                "Java  |  High priority",
                new Color(217, 119, 6)
        ));

        taskList.add(createDivider());

        taskList.add(createTaskRow(
                "DBMS revision",
                "DBMS  |  Medium priority",
                accentColor
        ));

        taskList.add(createDivider());

        taskList.add(createTaskRow(
                "Operating Systems notes",
                "OS  |  Low priority",
                new Color(16, 150, 116)
        ));

        panel.add(taskList, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTaskRow(
            String title, String detail, Color accent) {

        JPanel row = new JPanel(new BorderLayout(14, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(new EmptyBorder(16, 4, 16, 4));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JLabel indicator = new JLabel("●");
        indicator.setFont(new Font("SansSerif", Font.BOLD, 16));
        indicator.setForeground(accent);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel taskTitle = new JLabel(title);
        taskTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        taskTitle.setForeground(textColor);

        JLabel taskDetail = new JLabel(detail);
        taskDetail.setFont(new Font("SansSerif", Font.PLAIN, 12));
        taskDetail.setForeground(mutedColor);

        textPanel.add(taskTitle);
        textPanel.add(Box.createVerticalStrut(7));
        textPanel.add(taskDetail);

        row.add(indicator, BorderLayout.WEST);
        row.add(textPanel, BorderLayout.CENTER);

        return row;
    }

    private JPanel createDivider() {
        JPanel divider = new JPanel();
        divider.setBackground(new Color(232, 236, 243));
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        divider.setPreferredSize(new Dimension(1, 1));
        return divider;
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(13, 18, 13, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(textColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(218, 224, 235)
                ),
                new EmptyBorder(12, 16, 12, 16)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public DashboardPanel() {
        setLayout(new BorderLayout(0, 25));
        setBackground(new Color(244, 246, 251));
        setBorder(new EmptyBorder(30, 32, 30, 32));

        add(createDashboard(), BorderLayout.CENTER);
    }

}