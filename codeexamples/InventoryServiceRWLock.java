// InventoryService using ReentrantReadWriteLock

import java.util.concurrent.locks.ReentrantReadWriteLock;

class import java.util.concurrent.locks.ReentrantReadWriteLock;

class InventoryServiceRWLock {

    private final ReentrantReadWriteLock rwLock =
            new ReentrantReadWriteLock();

    private int stock = 100;

    public void purchase(int quantity) {
        rwLock.writeLock().lock();
        try {
            stock -= quantity;
            System.out.println("Stock updated: " + stock);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public int checkStock() {
        rwLock.readLock().lock();
        try {
            return stock;
        } finally {
            rwLock.readLock().unlock();
        }
    }
} {

    private final ReentrantReadWriteLock rwLock =
            new ReentrantReadWriteLock();

    private int stock = 100;

    public void purchase(int quantity) {
        rwLock.writeLock().lock();
        try {
            stock -= quantity;
            System.out.println("Stock updated: " + stock);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public int checkStock() {
        rwLock.readLock().lock();
        try {
            return stock;
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
