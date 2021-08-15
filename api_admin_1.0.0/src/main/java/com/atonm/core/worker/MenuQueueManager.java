package com.atonm.core.worker;

import com.atonm.kblease.api.log.dto.AccessLogDTO;
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
public class MenuQueueManager {
    private static int queueSize = 1000;
    private BlockingQueue<Object> menulogQueue = null;
    private static final Logger log_usual = LoggerFactory.getLogger(MenuQueueManager.class);

    private MenuQueueManager() {
        menulogQueue = new ArrayBlockingQueue<Object>(queueSize, true);
    }

    private static class SingletonHolder {
        static final MenuQueueManager SINGLETON = new MenuQueueManager();
    }

    public static MenuQueueManager getInstance() {
        return SingletonHolder.SINGLETON;
    }

    public static void setConfig(int qSize) {
        queueSize = qSize;
    }

    public void offerLog(Object log) {
        try{
            /*if(logQueue.isEmpty()) {
                logQueue.wait();
            }*/
            menulogQueue.put(log);
            /*if ( !success ) {
                log_usual.error("fail " + log.toString());
            }*/
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Object> drainToLog() {
        List<Object> list = new ArrayList<Object>();
        try {
            System.out.println("################ menulogQueue.size() :" + String.valueOf(menulogQueue.size()));
            Object one = menulogQueue.take();
            list.add(one);
            int cnt = menulogQueue.drainTo(list);

            System.out.println("################ menulogQueueCnt :" + String.valueOf(list.size()));

            List<AccessLogDTO> accessLogs = new ArrayList<AccessLogDTO>();
            accessLogs = ModelMapperUtils.mapList(list, AccessLogDTO.class);

            ObjectMapper mapper = new ObjectMapper();
            System.out.println(">>>>>>>>>>>> access Log : " + mapper.writeValueAsString(accessLogs));

            //customLogService = new CustomLogService();
            LogService logService = AppContextManager.getBean(LogService.class);
            int accesslogInsertCnt = logService.insertAccessLog(accessLogs);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> access Log insert Count : " + String.valueOf(accesslogInsertCnt));
        } catch ( Throwable t ) {
            //log_usual.error("");
            System.out.println("################ drain Log Error ################");
            t.printStackTrace();
        }
        return list;
    }

}
