package ontrack;
import java.util.ArrayList;
import java.util.List;
public class TaskInbox {
    public static class Task {
        public String taskId, studentId, unitId, title, status;
        public Task(String taskId, String studentId, String unitId,
                    String title, String status) {}
    }
    public void addTask(Task task) {}
    public List<Task> getTaskInbox(String studentId, String unitId) {
        return new ArrayList<>();
    }
    public int getTaskCount(String studentId, String unitId) { return 0; }
    public List<Task> getTasksByStatus(String s, String u, String st) {
        return new ArrayList<>();
    }
}