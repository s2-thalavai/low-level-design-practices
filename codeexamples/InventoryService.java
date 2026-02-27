// inventory service using ReentrantLock
// (same thread can acquire again)

import java.util.concurrent.locks.ReentrantLock;

class InventoryService {

    private final ReentrantLock lock = new ReentrantLock();
    private int stock = 100;

    public void purchase(int quantity) {
        lock.lock();
        try {
            stock -= quantity;
            System.out.println("Stock updated: " + stock);
        } finally {
            lock.unlock();
        }
    }

    public int checkStock() {
        lock.lock();
        try {
            return stock;
        } finally {
            lock.unlock();
        }
    }
}
