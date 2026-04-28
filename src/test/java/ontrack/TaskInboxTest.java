  
package ontrack;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

// TDD Test Cases for TaskInbox function
// Following RED -> GREEN -> REFACTOR cycle
//
// RED    = Test written first, fails because code does not exist
// GREEN  = Minimum code written to make test pass
// REFACTOR = Code cleaned up, tests still pass

public class TaskInboxTest {

    private TaskInbox taskInbox;

    private static final String STUDENT_ID = "s223456789";
    private static final String UNIT_ID    = "SIT707";

    // ============================================================
    // Setup — runs before each test
    // Follows ARRANGE step of AAA pattern
    // ============================================================
    @Before
    public void setUp() {
        taskInbox = new TaskInbox();

        // Add sample tasks matching real OnTrack SIT707 unit
        taskInbox.addTask(new TaskInbox.Task(
            "task_4_3H", STUDENT_ID, UNIT_ID,
            "Summarise research article 1", "Not Started"));

        taskInbox.addTask(new TaskInbox.Task(
            "task_6_2D", STUDENT_ID, UNIT_ID,
            "Right-BICEP and code coverage", "Working On It"));

        taskInbox.addTask(new TaskInbox.Task(
            "task_7_1P", STUDENT_ID, UNIT_ID,
            "Integrate web front-end with Java", "Ready for Feedback"));

        // Task belonging to a different student
        taskInbox.addTask(new TaskInbox.Task(
            "task_other", "s999999999", UNIT_ID,
            "Another student task", "Not Started"));
    }

    // ============================================================
    // TDD CYCLE 1 — RED phase
    // Test written FIRST before any implementation
    // This test verifies the basic correct result
    // ============================================================

    @Test
    public void testGetTaskInbox_ReturnsCorrectTasksForStudent() {
        System.out.println("\n[TDD - GREEN] getTaskInbox returns correct tasks");
        // ACT
        List<TaskInbox.Task> inbox = taskInbox.getTaskInbox(STUDENT_ID, UNIT_ID);
        // ASSERT
        System.out.println("Expected: 3 | Actual: " + inbox.size());
        assertEquals(3, inbox.size());
        System.out.println("PASSED");
    }

    // ============================================================
    // TDD CYCLE 2 — Verifying task does not include other students
    // ============================================================

    @Test
    public void testGetTaskInbox_ExcludesOtherStudentTasks() {
        System.out.println("\n[TDD - GREEN] getTaskInbox excludes other student tasks");
        List<TaskInbox.Task> inbox = taskInbox.getTaskInbox(STUDENT_ID, UNIT_ID);
        boolean hasOtherTask = inbox.stream()
            .anyMatch(t -> t.studentId.equals("s999999999"));
        System.out.println("Contains other student task: " + hasOtherTask);
        assertFalse(hasOtherTask);
        System.out.println("PASSED");
    }

    // ============================================================
    // TDD CYCLE 3 — Boundary: unknown student returns empty list
    // ============================================================

    @Test
    public void testGetTaskInbox_UnknownStudentReturnsEmptyList() {
        System.out.println("\n[TDD - GREEN] Unknown student returns empty list");
        List<TaskInbox.Task> inbox = taskInbox.getTaskInbox("s000000000", UNIT_ID);
        System.out.println("Expected: 0 | Actual: " + inbox.size());
        assertNotNull(inbox);
        assertEquals(0, inbox.size());
        System.out.println("PASSED");
    }

    // ============================================================
    // TDD CYCLE 4 — Boundary: wrong unit returns empty list
    // ============================================================

    @Test
    public void testGetTaskInbox_WrongUnitReturnsEmptyList() {
        System.out.println("\n[TDD - GREEN] Wrong unit returns empty list");
        List<TaskInbox.Task> inbox = taskInbox.getTaskInbox(STUDENT_ID, "SIT999");
        System.out.println("Expected: 0 | Actual: " + inbox.size());
        assertEquals(0, inbox.size());
        System.out.println("PASSED");
    }

    // ============================================================
    // TDD CYCLE 5 — Inverse: add task then verify it appears
    // ============================================================

    @Test
    public void testGetTaskInbox_AddedTaskAppearsInInbox() {
        System.out.println("\n[TDD - GREEN] Added task appears in inbox");
        taskInbox.addTask(new TaskInbox.Task(
            "task_new", STUDENT_ID, UNIT_ID, "New Task", "Not Started"));
        List<TaskInbox.Task> inbox = taskInbox.getTaskInbox(STUDENT_ID, UNIT_ID);
        System.out.println("Expected: 4 | Actual: " + inbox.size());
        assertEquals(4, inbox.size());
        System.out.println("PASSED");
    }

    // ============================================================
    // TDD CYCLE 6 — Error: null studentId throws exception
    // ============================================================

    @Test(expected = IllegalArgumentException.class)
    public void testGetTaskInbox_NullStudentIdThrowsException() {
        System.out.println("\n[TDD - GREEN] Null studentId throws IllegalArgumentException");
        taskInbox.getTaskInbox(null, UNIT_ID);
    }

    // ============================================================
    // TDD CYCLE 7 — Error: empty unitId throws exception
    // ============================================================

    @Test(expected = IllegalArgumentException.class)
    public void testGetTaskInbox_EmptyUnitIdThrowsException() {
        System.out.println("\n[TDD - GREEN] Empty unitId throws IllegalArgumentException");
        taskInbox.getTaskInbox(STUDENT_ID, "");
    }

    // ============================================================
    // TDD CYCLE 8 — getTaskCount cross check
    // ============================================================

    @Test
    public void testGetTaskCount_MatchesInboxSize() {
        System.out.println("\n[TDD - GREEN] getTaskCount matches inbox size");
        List<TaskInbox.Task> inbox = taskInbox.getTaskInbox(STUDENT_ID, UNIT_ID);
        int count = taskInbox.getTaskCount(STUDENT_ID, UNIT_ID);
        System.out.println("Inbox size: " + inbox.size() + " | getTaskCount: " + count);
        assertEquals(inbox.size(), count);
        System.out.println("PASSED");
    }

    // ============================================================
    // TDD CYCLE 9 — getTasksByStatus filters correctly
    // ============================================================

    @Test
    public void testGetTasksByStatus_ReturnsCorrectTasks() {
        System.out.println("\n[TDD - GREEN] getTasksByStatus returns correct filtered tasks");
        List<TaskInbox.Task> notStarted =
            taskInbox.getTasksByStatus(STUDENT_ID, UNIT_ID, "Not Started");
        System.out.println("Not Started tasks: " + notStarted.size());
        assertEquals(1, notStarted.size());
        assertEquals("task_4_3H", notStarted.get(0).taskId);
        System.out.println("PASSED");
    }

    // ============================================================
    // TDD CYCLE 10 — Performance: 1000 tasks under 1 second
    // ============================================================

    @Test
    public void testGetTaskInbox_Performance1000Tasks() {
        System.out.println("\n[TDD - GREEN] Performance: 1000 tasks retrieved under 1 second");
        TaskInbox perfInbox = new TaskInbox();
        for (int i = 0; i < 1000; i++) {
            perfInbox.addTask(new TaskInbox.Task(
                "task_" + i, "s_perf", "SIT707",
                "Task " + i, "Not Started"));
        }
        long start = System.currentTimeMillis();
        List<TaskInbox.Task> inbox = perfInbox.getTaskInbox("s_perf", "SIT707");
        long end = System.currentTimeMillis();
        System.out.println("Tasks: " + inbox.size() + " | Time: " + (end - start) + "ms");
        assertEquals(1000, inbox.size());
        assertTrue("Should complete under 1000ms", (end - start) < 1000);
        System.out.println("PASSED");
    }
}