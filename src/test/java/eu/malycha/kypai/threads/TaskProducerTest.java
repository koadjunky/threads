package eu.malycha.kypai.threads;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TaskProducerTest {

    @Test
    void shouldAddTaskToTheQueue() throws InterruptedException {
        WatermarkQueue<Task> queue = Mockito.mock(WatermarkQueue.class);
        TaskProducer producer = new TaskProducer(queue);
        producer.perform();
        verify(queue, times(1)).put(any(Task.class));
    }
}
