package com.studybuddy.model;

import java.time.LocalDate;

public class StudyTask {
    private final int taskId;
    private final String title;
    private final int subjectId;
    private final LocalDate dueDate;
    private final String priority;
    private final String status;

    public StudyTask(int taskId, String title, int subjectId,
                     LocalDate dueDate, String priority, String status) {
        this.taskId = taskId;
        this.title = title;
        this.subjectId = subjectId;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }
}
