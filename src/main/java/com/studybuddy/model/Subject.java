package com.studybuddy.model;

public class Subject {
    private final int subjectId;
    private final String subjectName;
    private final String description;

    @Override
    public String toString() {
        return subjectName;
    }

    public Subject(int subjectId, String subjectName, String description) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.description = description;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getDescription() {
        return description;
    }
}
