package pdd.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import pdd.PDDException;

public class TaskListTest {
    @Test
    public void toIndex_validNumber_returnsZeroBasedIndex() throws PDDException {
        TaskList tasks = new TaskList(new ArrayList<>(List.of(new Todo("a"), new Todo("b"))));
        assertEquals(0, tasks.toIndex("1"));
        assertEquals(1, tasks.toIndex("2"));
    }

    @Test
    public void toIndex_nonNumeric_throwsExceptionWithHint() {
        TaskList tasks = new TaskList(new ArrayList<>(List.of(new Todo("a"))));
        PDDException e = assertThrows(PDDException.class, () -> tasks.toIndex("abc"));
        assertEquals("OOPS!!! Please provide a valid task number, e.g. mark 2", e.getMessage());
    }

    @Test
    public void toIndex_zero_throwsOutOfRangeException() {
        TaskList tasks = new TaskList(new ArrayList<>(List.of(new Todo("a"))));
        PDDException e = assertThrows(PDDException.class, () -> tasks.toIndex("0"));
        assertEquals("OOPS!!! Task number 0 does not exist. You have 1 task(s) in the list.", e.getMessage());
    }

    @Test
    public void toIndex_greaterThanSize_throwsOutOfRangeException() {
        TaskList tasks = new TaskList(new ArrayList<>(List.of(new Todo("a"))));
        PDDException e = assertThrows(PDDException.class, () -> tasks.toIndex("5"));
        assertEquals("OOPS!!! Task number 5 does not exist. You have 1 task(s) in the list.", e.getMessage());
    }

    @Test
    public void add_task_increasesSizeAndIsRetrievable() {
        TaskList tasks = new TaskList(new ArrayList<>());
        Todo todo = new Todo("read book");
        tasks.add(todo);
        assertEquals(1, tasks.size());
        assertEquals(todo, tasks.get(0));
    }

    @Test
    public void delete_validIndex_removesAndReturnsTask() {
        Todo first = new Todo("a");
        Todo second = new Todo("b");
        TaskList tasks = new TaskList(new ArrayList<>(List.of(first, second)));
        Todo removed = (Todo) tasks.delete(0);
        assertEquals(first, removed);
        assertEquals(1, tasks.size());
        assertEquals(second, tasks.get(0));
    }
}
