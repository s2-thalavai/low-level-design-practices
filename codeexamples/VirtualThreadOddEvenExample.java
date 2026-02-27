import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class NumberPrinter {

    private int number = 1;
    private final int MAX = 10;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void printOdd() {
        while (true) {
            lock.lock();
            try {
                while (number % 2 == 0 && number <= MAX) {
                    condition.await();
                }
                if (number > MAX) break;

                System.out.println("Odd (Virtual): " + number);
                number++;
                condition.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    public void printEven() {
        while (true) {
            lock.lock();
            try {
                while (number % 2 != 0 && number <= MAX) {
                    condition.await();
                }
                if (number > MAX) break;

                System.out.println("Even (Virtual): " + number);
                number++;
                condition.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }
}

public class VirtualThreadOddEvenExample {

    public static void main(String[] args) {

        NumberPrinter printer = new NumberPrinter();

        // 🔥 Virtual Threads
        Thread oddThread = Thread.ofVirtual().start(printer::printOdd);
        Thread evenThread = Thread.ofVirtual().start(printer::printEven);

        oddThread.join();
        evenThread.join();
    }
}
