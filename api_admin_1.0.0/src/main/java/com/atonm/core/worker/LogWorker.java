package com.atonm.core.worker;

import java.util.List;

public class LogWorker  implements Runnable {
    private static QueueManager queueManager = QueueManager.getInstance();

    public void run() {
        while ( true ) {
            try {
                List<Object> list = queueManager.drainToLog();
                Thread.sleep(10 * 1000L);
            } catch ( Throwable t ) {
                t.printStackTrace();
            }
        }
    }
}
