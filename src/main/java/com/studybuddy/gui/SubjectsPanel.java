package com.studybuddy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.studybuddy.dao.SubjectDAO;
import com.studybuddy.model.Subject;

public class SubjectsPanel extends JPanel {
    private final Color backgroundColor = new Color(244, 246, 251);
    private final Color accentColor = new Color(99, 102, 241);

    private final JPanel cardsPanel = new JPanel(new GridLayout(0, 3, 18, 18));
    private final JPanel emptyPanel = new JPanel(new GridBagLayout());

    private final Random random = new Random();

    public SubjectsPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(backgroundColor);
        setBorder(new EmptyBorder(30, 32, 30, 32));

        add(createHeader(), BorderLayout.NORTH);

        cardsPanel.setBackground(backgroundColor);
        cardsPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        emptyPanel.setBackground(backgroundColor);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(backgroundColor);
        content.add(cardsPanel, BorderLayout.NORTH);

        add(content, BorderLayout.CENTER);

        refreshSubjects();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(backgroundColor);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("My Subjects");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(30, 41, 59));

        JLabel subtitle = new JLabel(
            "Organise the subjects you are studying."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(107, 119, 140));

        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(subtitle);

        JButton addButton = new JButton("+ Add Subject");
        addButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(accentColor);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.setBorder(new EmptyBorder(12, 18, 12, 18));

        addButton.addActionListener(e -> {
            Window owner = SwingUtilities.getWindowAncestor(this);
            AddSubjectDialog dialog =
                new AddSubjectDialog(owner, this::refreshSubjects);
            dialog.setVisible(true);
        });

        header.add(textPanel, BorderLayout.WEST);
        header.add(addButton, BorderLayout.EAST);
        return header;
    }

    public void refreshSubjects() {
        cardsPanel.removeAll();

        // var subjects = AppData.getSubjects();
        
        List<Subject> subjects;

        try {
            subjects = new SubjectDAO().getAllSubjects();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not load subjects!\n"
                    + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }


        if (subjects.isEmpty()) {
            JPanel empty = new JPanel();
            empty.setBackground(backgroundColor);
            empty.setLayout(new BoxLayout(empty, BoxLayout.Y_AXIS));

            JLabel icon = new JLabel("+");
            icon.setFont(new Font("SansSerif", Font.PLAIN, 38));
            icon.setForeground(accentColor);
            icon.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel message = new JLabel("No subjects yet");
            message.setFont(new Font("SansSerif", Font.BOLD, 20));
            message.setForeground(new Color(30, 41, 59));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel hint = new JLabel(
                "Click “Add Subject” to create your first subject."
            );
            hint.setFont(new Font("SansSerif", Font.PLAIN, 13));
            hint.setForeground(new Color(107, 119, 140));
            hint.setAlignmentX(Component.CENTER_ALIGNMENT);

            empty.add(icon);
            empty.add(Box.createVerticalStrut(10));
            empty.add(message);
            empty.add(Box.createVerticalStrut(6));
            empty.add(hint);

            emptyPanel.removeAll();
            emptyPanel.add(empty);
            cardsPanel.setVisible(false);

            JPanel content = (JPanel) getComponent(1);
            content.removeAll();
            content.add(emptyPanel, BorderLayout.CENTER);
            content.revalidate();
            content.repaint();
            return;
        }

        cardsPanel.setVisible(true);

        JPanel content = (JPanel) getComponent(1);
        content.removeAll();
        content.add(cardsPanel, BorderLayout.NORTH);

        for (Subject subject : subjects) {
            cardsPanel.add(createSubjectCard(subject));
        }

        content.revalidate();
        content.repaint();
    }

    private JPanel createSubjectCard(Subject subject) {
        Color[] colors = {
            new Color(230, 240, 255),
            new Color(232, 248, 239),
            new Color(255, 239, 225),
            new Color(245, 233, 255),
            new Color(255, 233, 238),
            new Color(230, 247, 248),
            new Color(250, 245, 220)
        };

        Color cardColor = colors[random.nextInt(colors.length)];

        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );

                // Subtle shadow beneath the rounded card.
                g2.setColor(new Color(20, 30, 50, 24));
                g2.fill(new RoundRectangle2D.Float(
                    3, 5, getWidth() - 7, getHeight() - 7, 22, 22
                ));

                g2.setColor(cardColor);
                g2.fill(new RoundRectangle2D.Float(
                    0, 0, getWidth() - 7, getHeight() - 7, 22, 22
                ));
                g2.dispose();
                super.paintComponent(g);
            }
        };

        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(20, 18, 20, 18));
        card.setPreferredSize(new Dimension(220, 155));

        JLabel subjectName = new JLabel(subject.getSubjectName());
        subjectName.setFont(new Font("SansSerif", Font.BOLD, 18));
        subjectName.setForeground(new Color(30, 41, 59));
        subjectName.setAlignmentX(Component.LEFT_ALIGNMENT);

        String descriptionText = subject.getDescription().isBlank()
            ? "No description added"
            : subject.getDescription();

        JLabel description = new JLabel(
            "<html><div style='width:165px'>"
                + escapeHtml(descriptionText)
                + "</div></html>"
        );
        description.setFont(new Font("SansSerif", Font.PLAIN, 12));
        description.setForeground(new Color(80, 90, 110));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel idLabel = new JLabel("SUBJECT #" + subject.getSubjectId());
        idLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        idLabel.setForeground(new Color(90, 100, 120));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(idLabel);
        card.add(Box.createVerticalStrut(13));
        card.add(subjectName);
        card.add(Box.createVerticalStrut(8));
        card.add(description);

        return card;
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;");
    }
}
