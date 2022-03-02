package eu.malycha.kypai.threads;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskConsumerTest {

    @Test
    void shouldExecuteTaskFromTheQueue() throws InterruptedException {
        Task task = Mockito.mock(Task.class);
        when(task.execute()).thenReturn("3.14");
        WatermarkQueue<Task> queue = Mockito.mock(WatermarkQueue.class);
        when(queue.take()).thenReturn(task);
        TaskConsumer consumer = new TaskConsumer(queue);
        consumer.perform();
        verify(queue, times(1)).take();
        verify(task, times(1)).execute();
    }
}
