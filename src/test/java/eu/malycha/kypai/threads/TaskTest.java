package eu.malycha.kypai.threads;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {

    @Test
    void shouldGenerateExpression() {
        Task task = Task.create();
        assertTrue(task.getExpression().length() <= Task.SIZE);
        assertTrue(task.getExpression().matches(String.format("[%s]+", Task.CHARS)));
    }

    @Test
    void shouldEvaluateExpression() {
        Task task = new Task("2+2");
        assertEquals("4.0", task.execute());
    }

    @Test
    void shouldThrowExceptionWhenExpressionIncorrect() {
        Task task = new Task("2*/2");
        assertThrows(IllegalArgumentException.class, () -> task.execute());
    }
}
