import java.lang.management.*;

public class DeadlockMonitor {

    public static void detectDeadlock() {

        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        long[] threadIds = threadBean.findDeadlockedThreads();

        if (threadIds != null) {

            ThreadInfo[] threadInfos =
                    threadBean.getThreadInfo(threadIds, true, true);

            System.out.println("🔥 Deadlock detected!");

            for (ThreadInfo info : threadInfos) {
                System.out.println(info);
            }

        } else {
            System.out.println("No deadlock detected.");
        }
    }
}
