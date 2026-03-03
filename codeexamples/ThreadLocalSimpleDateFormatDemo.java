import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalSimpleDateFormatDemo {

    // Each thread gets its own SimpleDateFormat instance
    private static final ThreadLocal<SimpleDateFormat> threadLocalFormatter =
            ThreadLocal.withInitial(() ->
                    new SimpleDateFormat("yyyy-MM-dd"));

    public static void main(String[] args) {

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    Date date = threadLocalFormatter.get()
                            .parse("2026-03-03");

                    if (i % 200 == 0) {
                        System.out.println(Thread.currentThread().getName()
                                + " parsed: " + date);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // Important in thread pools (avoid memory leaks)
            threadLocalFormatter.remove();
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }
}
