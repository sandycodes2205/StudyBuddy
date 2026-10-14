package com.studybuddy.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.studybuddy.dao.SubjectDAO;

public class AddSubjectDialog extends JDialog {
    private final JTextField nameField = new JTextField(22);
    private final JTextArea descriptionArea = new JTextArea(4, 22);
    private final Runnable onSaved;

    public AddSubjectDialog(Window owner, Runnable onSaved) {
        super(owner, "Add Subject", ModalityType.APPLICATION_MODAL);
        this.onSaved = onSaved;

        setSize(430, 330);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(12, 12));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(20, 24, 10, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 4, 7, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Subject name *"), gbc);

        gbc.gridy = 1;
        form.add(nameField, gbc);

        gbc.gridy = 2;
        form.add(new JLabel("Description (optional)"), gbc);

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        gbc.gridy = 3;
        form.add(new JScrollPane(descriptionArea), gbc);

        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save Subject");

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveSubject());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(cancelButton);
        buttons.add(saveButton);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(saveButton);
    }

    private void saveSubject() {
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please enter a subject name.",
                "Missing Subject", JOptionPane.WARNING_MESSAGE
            );
            nameField.requestFocus();
            return;
        }

        try {
            new SubjectDAO().addSubject(name, description);
            onSaved.run();
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not save subject.\n"
                    + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        // boolean duplicate = AppData.getSubjects().stream()
        //     .map(Subject::getSubjectName)
        //     .anyMatch(existing -> existing.equalsIgnoreCase(name));

        // if (duplicate) {
        //     JOptionPane.showMessageDialog(
        //         this, "This subject already exists.",
        //         "Duplicate Subject", JOptionPane.WARNING_MESSAGE
        //     );
        //     return;
        // }

        // AppData.addSubject(name, description);
        // onSaved.run();
        // dispose();
    }
}
