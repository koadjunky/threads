package eu.malycha.kypai.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TaskConsumer extends Stoppable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskConsumer.class);

    private final WatermarkQueue<Task> taskQueue;

    public TaskConsumer(WatermarkQueue<Task> queue) {
        this.taskQueue = queue;
    }

    @Override
    protected void perform() {
        try {
            Task task = taskQueue.take();
            System.out.println(task.execute());
        } catch (Exception ex) {
            // Catch all to avoid thread death
            LOGGER.warn("Exception in TaskConsumer", ex);
        }
    }
}
