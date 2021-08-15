package com.atonm.core.worker;

import com.atonm.kblease.api.log.dto.ActionLogDTO;
import com.atonm.kblease.api.log.service.LogService;
import com.atonm.core.context.AppContextManager;
import com.atonm.kblease.api.utils.ModelMapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class QueueManager {
    private static int queueSize = 1000;
    private BlockingQueue<Object> logQueue = null;
    private static final Logger log_usual = LoggerFactory.getLogger(QueueManager.class);

    private QueueManager() {
        logQueue = new ArrayBlockingQueue<Object>(queueSize, true);
    }

    private static class SingletonHolder {
        static final QueueManager SINGLETON = new QueueManager();
    }

    public static QueueManager getInstance() {
        return SingletonHolder.SINGLETON;
    }

    public static void setConfig(int qSize) {
        queueSize = qSize;
    }

    public void offerLog(Object log) {
        try{
            logQueue.put(log);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Object> drainToLog() {
        List<Object> list = new ArrayList<Object>();
        try {
            Object one = logQueue.take();
            list.add(one);
            int cnt = logQueue.drainTo(list);

            List<ActionLogDTO> actionLogs = new ArrayList<ActionLogDTO>();
            actionLogs = ModelMapperUtils.mapList(list, ActionLogDTO.class);

            ObjectMapper mapper = new ObjectMapper();

            LogService logService = AppContextManager.getBean(LogService.class);
            int actionlogInsertCnt = logService.insertActionLog(actionLogs);
        } catch ( Throwable t ) {
            t.printStackTrace();
        }
        return list;
    }
}
