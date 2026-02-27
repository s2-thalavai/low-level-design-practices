
/*

// StampedLock introduces a new concept: optimistic read.

Flow:

Read without blocking

Check if write occurred

If yes → fallback to real read lock
*/
    
import java.util.concurrent.locks.StampedLock;

class PriceServiceStampedLock {

    private final StampedLock lock = new StampedLock();
    private double price = 100;

    public double getPrice() {
        long stamp = lock.tryOptimisticRead();
        double currentPrice = price;

        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                currentPrice = price;
            } finally {
                lock.unlockRead(stamp);
            }
        }

        return currentPrice;
    }

    public void updatePrice(double newPrice) {
        long stamp = lock.writeLock();
        try {
            price = newPrice;
        } finally {
            lock.unlockWrite(stamp);
        }
    }
}
