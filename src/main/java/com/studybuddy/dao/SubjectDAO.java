package com.studybuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.studybuddy.model.Subject;

public class SubjectDAO {

    public List<Subject> getAllSubjects() throws SQLException {
        List<Subject> subjects = new ArrayList<>();

        String sql = """
            SELECT subject_id, subject_name, description
            FROM subjects
            ORDER BY subject_name
            """;

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                subjects.add(new Subject(
                    rs.getInt("subject_id"),
                    rs.getString("subject_name"),
                    rs.getString("description") == null
                        ? "" : rs.getString("description")
                ));
            }
        }

        return subjects;
    }

    public void addSubject(String name, String description)
            throws SQLException {

        String sql = """
            INSERT INTO subjects (subject_name, description)
            VALUES (?, ?)
            """;

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);

            if (description == null || description.isBlank()) {
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(2, description);
            }

            ps.executeUpdate();
        }
    }
}
