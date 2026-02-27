import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class NumberPrinter {

    private int number = 1;
    private final int MAX = 10;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void printOdd() {
        while (number <= MAX) {
            lock.lock();
            try {
                while (number % 2 == 0) {
                    condition.await();
                }
                if (number <= MAX) {
                    System.out.println("Odd Thread: " + number);
                    number++;
                    condition.signal();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    public void printEven() {
        while (number <= MAX) {
            lock.lock();
            try {
                while (number % 2 != 0) {
                    condition.await();
                }
                if (number <= MAX) {
                    System.out.println("Even Thread: " + number);
                    number++;
                    condition.signal();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }
}

public class OddEvenReentrantLockExample {

    public static void main(String[] args) {

        NumberPrinter printer = new NumberPrinter();

        Thread t1 = new Thread(printer::printOdd);
        Thread t2 = new Thread(printer::printEven);

        t1.start();
        t2.start();
    }
}
