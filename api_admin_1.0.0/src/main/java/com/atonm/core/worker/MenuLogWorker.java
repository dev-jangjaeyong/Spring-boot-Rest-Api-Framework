package com.atonm.core.worker;

import java.util.List;

public class MenuLogWorker implements Runnable {
    private static MenuQueueManager menuQueueManager = MenuQueueManager.getInstance();

    public void run() {
        while ( true ) {
            try {
                List<Object> list = menuQueueManager.drainToLog();

                //DefaultDAO.insLogs(list);
                Thread.sleep(10 * 1000L);
            } catch ( Throwable t ) {
                t.printStackTrace();
            }
        }
    }

}
