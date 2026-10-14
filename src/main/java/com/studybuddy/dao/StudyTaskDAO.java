package com.studybuddy.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.studybuddy.model.StudyTask;

public class StudyTaskDAO {

    public void addTask(StudyTask task) throws SQLException {
        String sql = """
            INSERT INTO study_tasks
                (title, subject_id, due_date, priority, status)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, task.getTitle());
            ps.setInt(2, task.getSubjectId());

            if (task.getDueDate() == null) {
                ps.setNull(3, Types.DATE);
            } else {
                ps.setDate(3, Date.valueOf(task.getDueDate()));
            }

            ps.setString(4, task.getPriority());
            ps.setString(5, task.getStatus());

            ps.executeUpdate();
        }
    }

    public List<StudyTask> getAllTasks() throws SQLException {
        List<StudyTask> tasks = new ArrayList<>();

        String sql = """
            SELECT task_id, title, subject_id,
                due_date, priority, status
            FROM study_tasks
            ORDER BY
                CASE status WHEN 'PENDING' THEN 0 ELSE 1 END,
                due_date IS NULL,
                due_date
            """;

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Date sqlDate = rs.getDate("due_date");

                tasks.add(new StudyTask(
                    rs.getInt("task_id"),
                    rs.getString("title"),
                    rs.getInt("subject_id"),
                    sqlDate == null
                        ? null : sqlDate.toLocalDate(),
                    rs.getString("priority"),
                    rs.getString("status")
                ));
            }
        }

        return tasks;
    }
}
