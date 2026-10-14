package com.studybuddy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class DashboardFrame extends JFrame {

    private final Color backgroundColor = new Color(244, 246, 251);
    private final Color sidebarColor = new Color(23, 32, 51);
    private final Color accentColor = new Color(99, 102, 241);
    private final Color textColor = new Color(30, 41, 59);
    private final Color mutedColor = new Color(107, 119, 140);


    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(sidebarColor);
        sidebar.setBorder(new EmptyBorder(28, 18, 20, 18));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("  Study Buddy");
        logo.setFont(new Font("SansSerif", Font.BOLD, 23));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tagline = new JLabel("  YOUR PERSONAL STUDY SPACE");
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 9));
        tagline.setForeground(new Color(173, 187, 211));
        tagline.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(tagline);
        sidebar.add(Box.createVerticalStrut(45));

        String[] menuItems = {
                "Dashboard",
                "My Subjects",
                "Study Tasks",
                "Study Timer",
                "Progress Tracker"
        };

        for (int i = 0; i < menuItems.length; i++) {
            JButton button = new JButton(menuItems[i]);

            button.setFont(new Font("SansSerif", Font.PLAIN, 14));
            button.setForeground(Color.WHITE);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setBorder(new EmptyBorder(13, 14, 13, 8));
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
            button.setAlignmentX(Component.LEFT_ALIGNMENT);

            if (i == 0) {
                button.setBackground(accentColor);
            } else {
                button.setBackground(sidebarColor);
            }

            button.addActionListener(e ->
                    JOptionPane.showMessageDialog(
                            this,
                            button.getText()
                                    + " section will be implemented next.",
                            "Study Buddy",
                            JOptionPane.INFORMATION_MESSAGE
                    )
            );

            sidebar.add(button);
            sidebar.add(Box.createVerticalStrut(8));
        }

        sidebar.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("Learn. Focus. Achieve.");
        footer.setForeground(new Color(173, 187, 211));
        footer.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(footer);

        return sidebar;
    }

    public DashboardFrame() {
        setTitle("Study Buddy");
        setSize(1100, 720);
        setMinimumSize(new Dimension(850, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(new DashboardPanel(), BorderLayout.CENTER);
    }

}
