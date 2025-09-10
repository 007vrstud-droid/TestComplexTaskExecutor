import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Task implements Runnable {
    private final int taskId;
    private final CyclicBarrier barrier;
    private final AtomicInteger resultCollector;

    public Task(int taskId, CyclicBarrier barrier, AtomicInteger resultCollector) {
        this.taskId = taskId;
        this.barrier = barrier;
        this.resultCollector = resultCollector;
    }

    @Override
    public void run() {
        resultCollector.addAndGet(execute());
        try {
            barrier.await();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    private int execute() {
        return taskId * 10;
    }
}