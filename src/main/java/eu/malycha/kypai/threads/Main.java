package eu.malycha.kypai.threads;

import java.util.ArrayList;
import java.util.List;


public class Main {

    private static final Integer LIMIT = 1000;
    private static final Integer HALF_LIMIT = 500;

    private final List<Thread> threads = new ArrayList<>();
    private final List<Stoppable> stoppables = new ArrayList<>();

    void addThread(Stoppable stoppable) {
        stoppables.add(stoppable);
        threads.add(new Thread(stoppable));
    }

    void start() {
        for (Thread thread: threads) {
            thread.start();
        }
    }

    void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    void stop() {
        for (Stoppable stoppable : stoppables) {
            stoppable.stop();
        }
        for (Thread thread: threads) {
            joinThread(thread);
        }
    }

    public static void main(String[] args) {
        WatermarkQueue<Task> queue = new WatermarkQueue<>(LIMIT, HALF_LIMIT);
        Main main = new Main();
        main.addThread(new TaskProducer(queue));
        main.addThread(new TaskProducer(queue));
        main.addThread(new TaskConsumer(queue));
        main.addThread(new TaskConsumer(queue));
        main.addThread(new TaskConsumer(queue));
        main.addThread(new TaskConsumer(queue));
        main.start();
    }
}
