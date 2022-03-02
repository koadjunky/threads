package eu.malycha.kypai.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class TaskProducer extends Stoppable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskProducer.class);

    private final WatermarkQueue<Task> taskQueue;

    TaskProducer(WatermarkQueue<Task> queue) {
        this.taskQueue = queue;
    }

    @Override
    protected void perform() {
        try {
            Task task = Task.create();
            taskQueue.put(task);
        } catch (Exception ex) {
            // Catch all to avoid thread death
            LOGGER.warn("Exception in TaskProducer", ex);
        }
    }
}
