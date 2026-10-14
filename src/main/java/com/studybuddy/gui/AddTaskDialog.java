package com.studybuddy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.studybuddy.dao.StudyTaskDAO;
import com.studybuddy.dao.SubjectDAO;
import com.studybuddy.model.StudyTask;
import com.studybuddy.model.Subject;

public class AddTaskDialog extends JDialog {
    private final JTextField titleField = new JTextField(22);
    private final JComboBox<Subject> subjectCombo = new JComboBox<>();
    private final JTextField dueDateField = new JTextField(22);
    private final JComboBox<String> priorityCombo =
        new JComboBox<>(new String[]{"LOW", "MEDIUM", "HIGH"});
    private final JComboBox<String> statusCombo =
        new JComboBox<>(new String[]{"PENDING", "COMPLETED"});

    private final Runnable onSaved;

    public AddTaskDialog(Window owner, Runnable onSaved) {
        super(owner, "Add Study Task", ModalityType.APPLICATION_MODAL);
        this.onSaved = onSaved;

        setSize(470, 440);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(22, 26, 18, 26));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 4, 7, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        addField(form, gbc, 0, "Task title *", titleField);
        addField(form, gbc, 2, "Subject *", subjectCombo);

        dueDateField.setToolTipText("Format: YYYY-MM-DD");
        dueDateField.setText(LocalDate.now().toString());
        addField(form, gbc, 4, "Due date (YYYY-MM-DD)", dueDateField);

        addField(form, gbc, 6, "Priority", priorityCombo);
        addField(form, gbc, 8, "Status", statusCombo);

        subjectCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus
                );

                if (value instanceof Subject subject) {
                    setText(subject.getSubjectName());
                } else {
                    setText("Select a subject");
                }
                return this;
            }
        });

        try {
            for (Subject subject : new SubjectDAO().getAllSubjects()) {
                subjectCombo.addItem(subject);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not load subjects.\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        JLabel dateHint = new JLabel("Leave the date blank if there is no deadline.");
        dateHint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        dateHint.setForeground(new Color(107, 119, 140));

        JPanel datePanel = new JPanel(new BorderLayout(0, 4));
        datePanel.add(dueDateField, BorderLayout.CENTER);
        datePanel.add(dateHint, BorderLayout.SOUTH);

        // Replace the date field row with the field and its hint.
        addField(form, gbc, 4, "Due date (YYYY-MM-DD)", datePanel);

        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save Task");

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveTask());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBorder(new EmptyBorder(0, 12, 12, 12));
        buttons.add(cancelButton);
        buttons.add(saveButton);

        if (subjectCombo.getItemCount() == 0) {
            JLabel warning = new JLabel(
                "Add a subject first before creating a task."
            );
            warning.setForeground(new Color(180, 70, 60));
            warning.setBorder(new EmptyBorder(0, 20, 8, 20));
            add(warning, BorderLayout.NORTH);
            saveButton.setEnabled(false);
        }

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(saveButton);
    }

    private void addField(
            JPanel panel, GridBagConstraints gbc,
            int row, String label, JComponent field) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridy = row + 1;
        panel.add(field, gbc);
    }

    private void saveTask() {
        String title = titleField.getText().trim();
        Subject subject = (Subject) subjectCombo.getSelectedItem();
        String dateText = dueDateField.getText().trim();

        if (title.isEmpty()) {
            showError("Please enter a task title.");
            titleField.requestFocus();
            return;
        }

        if (subject == null) {
            showError("Please add and select a subject first.");
            return;
        }

        LocalDate dueDate = null;

        if (!dateText.isEmpty()) {
            try {
                dueDate = LocalDate.parse(dateText);
            } catch (DateTimeParseException ex) {
                showError("Enter a valid date in YYYY-MM-DD format.");
                dueDateField.requestFocus();
                return;
            }
        }

        StudyTask task = new StudyTask(
            0, // MySQL will generate the task ID
            title,
            subject.getSubjectId(),
            dueDate,
            (String) priorityCombo.getSelectedItem(),
            (String) statusCombo.getSelectedItem()
        );

        try {
            new StudyTaskDAO().addTask(task);

            JOptionPane.showMessageDialog(
                this,
                "Task saved successfully!"
            );

            onSaved.run();
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not save task.\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
            this, message, "Invalid Input",
            JOptionPane.WARNING_MESSAGE
        );
    }
    
}
