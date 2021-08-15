package com.atonm.kblease.api.log.service;

import com.atonm.kblease.api.common.base.BaseService;
import com.atonm.kblease.api.log.dto.AccessLogDTO;
import com.atonm.kblease.api.log.dto.ActionLogDTO;
import com.atonm.kblease.api.log.dto.LeaseCallLogDTO;
import com.atonm.kblease.api.log.mapper.LogMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jang jae young
 * @since 2018-12-03
 */
@Service
public class LogService extends BaseService {
    private final LogMapper logMapper;

    @Autowired
    LogService(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    public int insertActionLog(List<ActionLogDTO> actionLogs) {
        int insertCnt = 0;
        Iterator<ActionLogDTO> iterator = actionLogs.iterator();
        while (iterator.hasNext()) {
            logMapper.insertActionLog(iterator.next());
            ++insertCnt;
        }
        return insertCnt;
    }


//    public int insertAccessLog(List<AccessLogDTO> accessLogDTOS) throws JSONException {
//        int insertCnt = 0;
//        Iterator<AccessLogDTO> iterator = accessLogDTOS.iterator();
//        while (iterator.hasNext()) {
//            logMapper.insertAccessLog(iterator.next());
//            ++insertCnt;
//        }
//        return insertCnt;
//    }

    public int insertAccessLog(List<AccessLogDTO> accessLogDTOS) throws JSONException {
        int insertCnt = 0;

        for (AccessLogDTO logDto : accessLogDTOS){
            logMapper.insertAccessLog(logDto);
            ++insertCnt;
//            try{
//                LeaseCallLogDTO leaseCallLogDTO = new LeaseCallLogDTO();
//
//                leaseCallLogDTO.setCarNo(jsonArrayGetFirstValue(logDto, "cmCarNo"));
//                leaseCallLogDTO.setCarplateNumber(jsonArrayGetFirstValue(logDto, "cmCarPlateNumber"));
//                leaseCallLogDTO.setUseType(jsonArrayGetFirstValue(logDto, "useType"));
//                leaseCallLogDTO.setConnectUserNo(jsonArrayGetFirstValue(logDto, "cmUserNo"));
//                leaseCallLogDTO.setIpAddress(logDto.getIp());
//                leaseCallLogDTO.setPid(jsonArrayGetFirstValue(logDto, "pid"));
//                leaseCallLogDTO.setChannel(jsonArrayGetFirstValue(logDto, "channel"));
//
//                logMapper.insertLeaseCallLog(leaseCallLogDTO);
//            }catch(Exception e){
//                e.printStackTrace();
//            }


        }
        return insertCnt;
    }

    private String jsonArrayGetFirstValue(AccessLogDTO reqArgs, String key) throws JSONException {
        String result = null;
        try{
            JSONObject jsonObject = new JSONObject(reqArgs.getReqArgs());
            result = (String)new ObjectMapper().convertValue(jsonObject.get(key), JSONArray.class).get(0);
        }catch (Exception e){
            e.printStackTrace();
            result = "";
        }

        return result;
    }
}
