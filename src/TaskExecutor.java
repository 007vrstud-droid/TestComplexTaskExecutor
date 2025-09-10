import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskExecutor {

    public TaskExecutor(int numberOfTasks) {}

    public void executeTasks(int taskCount) {
        AtomicInteger resultCollector = new AtomicInteger();
        Runnable barrierAction = () -> {
            System.out.println("Задачи завершены. Результат: " + resultCollector.get());
        };
        CyclicBarrier barrier = new CyclicBarrier(taskCount,barrierAction);
        ExecutorService executor = Executors.newFixedThreadPool(taskCount);
        for (int i = 1; i <= taskCount; i++) {
            executor.submit(new Task(i, barrier, resultCollector));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}