package eu.malycha.kypai.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WatermarkQueue<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatermarkQueue.class);

    private final ArrayList<E> items;
    private int takeIndex;
    private int putIndex;
    private int count;
    private final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;
    private final int lowWatermark;
    private boolean filling = true;

    public WatermarkQueue(int highWatermark, int lowWatermark) {
        if (lowWatermark < 0 || highWatermark < lowWatermark) {
            throw new IllegalArgumentException();
        }
        this.items = new ArrayList<>(Collections.nCopies(highWatermark, (E) null));
        this.lowWatermark = lowWatermark;
        this.lock = new ReentrantLock(true);
        this.notEmpty = this.lock.newCondition();
        this.notFull = this.lock.newCondition();
    }

    private void enqueue(E e) {
        this.items.set(this.putIndex, e);
        if (++this.putIndex == this.items.size()) {
            this.putIndex = 0;
        }

        if (++this.count == this.items.size()) {
            this.filling = false;
        }
        LOGGER.info("Queue size: {}", this.count);
        this.notEmpty.signal();
    }

    private E dequeue() {
        E e = this.items.get(this.takeIndex);
        this.items.set(this.takeIndex, null);
        if (++this.takeIndex == items.size()) {
            this.takeIndex = 0;
        }

        if (--this.count <= this.lowWatermark) {
            this.filling = true;
        }
        LOGGER.info("Queue size: {}", this.count);
        this.notFull.signal();
        return e;
    }

    public void put(E e) throws InterruptedException {
        Objects.requireNonNull(e);

        this.lock.lockInterruptibly();
        try {
            while(!filling || (this.count == this.items.size())) {
                this.notFull.await();
            }
            this.enqueue(e);
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        this.lock.lockInterruptibly();
        try {
            while(this.count == 0) {
                this.notEmpty.await();
            }
            return this.dequeue();
        } finally {
            lock.unlock();
        }
    }
}
