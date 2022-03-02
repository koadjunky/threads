package eu.malycha.kypai.threads;

abstract class Stoppable implements Runnable {

    private boolean running = true;

    protected void stop() {
        running = false;
    }

    abstract protected void perform();

    @Override
    public void run() {
        while (running) {
            perform();
        }
    }
}
