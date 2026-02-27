public class Singleton {

    // Volatile is critical here
    private static volatile Singleton instance;

    // Private constructor prevents instantiation
    private Singleton() {
        System.out.println("Singleton instance created");
    }

    public static Singleton getInstance() {

        // First check (no locking)
        if (instance == null) {

            synchronized (Singleton.class) {

                // Second check (with locking)
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }

    public void showMessage() {
        System.out.println("Hello from Singleton");
    }

    public static void main(String[] args) {

        Runnable task = () -> {
            Singleton singleton = Singleton.getInstance();
            singleton.showMessage();
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();
    }
}
