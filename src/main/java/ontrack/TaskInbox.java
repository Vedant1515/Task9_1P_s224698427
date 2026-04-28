  
package ontrack;

import java.util.ArrayList;
import java.util.List;

// OnTrack Function: getTaskInbox
// Returns list of tasks for a given student in a given unit
// Observed from: Left panel task list in real OnTrack UI

public class TaskInbox {

    // Inner class representing a Task
    public static class Task {
        public String taskId;
        public String studentId;
        public String unitId;
        public String title;
        public String status;

        public Task(String taskId, String studentId, String unitId,
                    String title, String status) {
            this.taskId    = taskId;
            this.studentId = studentId;
            this.unitId    = unitId;
            this.title     = title;
            this.status    = status;
        }

        @Override
        public String toString() {
            return "[" + taskId + "] " + title + " | Status: " + status;
        }
    }

    // In-memory task store
    private List<Task> taskStore = new ArrayList<>();

    // Add a task to the store
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        taskStore.add(task);
    }

    // FUNCTION: getTaskInbox
    // Returns all tasks for a given studentId and unitId
    public List<Task> getTaskInbox(String studentId, String unitId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("studentId cannot be null or empty");
        }
        if (unitId == null || unitId.trim().isEmpty()) {
            throw new IllegalArgumentException("unitId cannot be null or empty");
        }

        List<Task> inbox = new ArrayList<>();
        for (Task task : taskStore) {
            if (task.studentId.equals(studentId) &&
                task.unitId.equals(unitId)) {
                inbox.add(task);
            }
        }
        return inbox;
    }

    // FUNCTION: getTaskCount
    // Returns total number of tasks for a student in a unit
    public int getTaskCount(String studentId, String unitId) {
        return getTaskInbox(studentId, unitId).size();
    }

    // FUNCTION: getTasksByStatus
    // Returns tasks filtered by status for a student in a unit
    public List<Task> getTasksByStatus(String studentId,
                                       String unitId,
                                       String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("status cannot be null or empty");
        }
        List<Task> result = new ArrayList<>();
        for (Task task : getTaskInbox(studentId, unitId)) {
            if (task.status.equals(status)) {
                result.add(task);
            }
        }
        return result;
    }
}