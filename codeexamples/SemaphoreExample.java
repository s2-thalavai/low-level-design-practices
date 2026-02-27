import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    private static final Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting...");
                semaphore.acquire();

                System.out.println(Thread.currentThread().getName() + " acquired permit");
                Thread.sleep(2000);

                System.out.println(Thread.currentThread().getName() + " releasing permit");
                semaphore.release();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(task).start();
        }
    }
}
