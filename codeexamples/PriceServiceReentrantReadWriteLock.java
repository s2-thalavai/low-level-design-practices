/*
Behavior

Multiple readers allowed

Writer blocks all readers

Reentrant (same thread can acquire again)

*/
import java.util.concurrent.locks.ReentrantReadWriteLock;

class PriceServiceReentrantReadWriteLock {

    private final ReentrantReadWriteLock lock =
            new ReentrantReadWriteLock();

    private double price = 100;

    public double getPrice() {
        lock.readLock().lock();
        try {
            return price;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void updatePrice(double newPrice) {
        lock.writeLock().lock();
        try {
            price = newPrice;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
