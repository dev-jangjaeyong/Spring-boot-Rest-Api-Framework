package com.atonm.excel;

import com.atonm.kblease.api.common.base.BaseRestController;
import com.atonm.kblease.api.dto.*;
import com.atonm.kblease.api.service.KbConsultService;
import com.atonm.kblease.api.service.KbLeaseService;
import com.atonm.kblease.api.utils.DateUtil;
import com.atonm.kblease.api.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jyjang
 * @since 2018-08-09
 */
@RestController
public class ExcelRestController extends BaseRestController {

    private final KbLeaseService kbLeaseService;
    private final KbConsultService kbConsultService;

    @Autowired
    public ExcelRestController(KbLeaseService kbLeaseService, KbConsultService kbConsultService) {
        this.kbLeaseService = kbLeaseService;
        this.kbConsultService =kbConsultService;
    }

    /**
     * KB리스대상 차량 엑셀다운로드
     * @param
     * @return
     */
    @GetMapping("/excel/mapping/download")
    @ApiOperation(value = "리스대상 차량종류 다운로드")
    public ModelAndView getKbMappingCarExcelListDownload(@ModelAttribute KbConvertDTO kbConvertDTO) {
        Map<String, Object> excelDownInfo = new HashMap<>();
        excelDownInfo.put("fileName","KB_MAPPING_CAR");
        excelDownInfo.put("lastPath","lease/");

        List<String> excelHeaderList = new ArrayList<>();
        excelHeaderList.add("NO");
        excelHeaderList.add("카매니저_제조사_명");
        excelHeaderList.add("카매니저_모델_명");
        excelHeaderList.add("카매니저_세부모델_명");
        excelHeaderList.add("카매니저_등급_명");
        excelHeaderList.add("카매니저_세부등급_명");
        excelHeaderList.add("KB_제조사_명");
        excelHeaderList.add("KB_모델_명");
        excelHeaderList.add("KB_세부모델_명");
        excelHeaderList.add("KB_등급_명");
        excelHeaderList.add("KB_세부등급_명");
        excelHeaderList.add("KB_리스_코드");
        excelHeaderList.add("KB_제조사_코드");
        excelHeaderList.add("KB_모델_코드");
        excelHeaderList.add("KB_세부모델_코드");
        excelHeaderList.add("KB_등급_코드");
        excelHeaderList.add("KB_세부등급_코드");

        excelDownInfo.put("headers", excelHeaderList);

        List<KbConvertExcelDTO> excelObjList = kbLeaseService.excelMappingCar(kbConvertDTO);
        excelDownInfo.put("list", excelObjList);

        ModelAndView modelAndView = new ModelAndView("excelView", "resultList", excelDownInfo);
        return modelAndView;
    }

    /**
     * KB리스대상 차량 엑셀다운로드
     * @param
     * @return
     */
    @GetMapping("/excel/aggregate/download")
    @ApiOperation(value = "리스이용 집계 엑셀 다운로드")
    public ModelAndView getKbAggregateExcelListDownload(@ModelAttribute KbLeaseAggreGateDTO kbLeaseAggreGateDTO) {
        Map<String, Object> excelDownInfo = new HashMap<>();
        excelDownInfo.put("fileName","KB_AGGREGATE");
        excelDownInfo.put("lastPath","consult/");

        List<String> excelHeaderList = new ArrayList<>();
        excelHeaderList.add("NO");
        excelHeaderList.add("차량번호");
        excelHeaderList.add("발생시간");
        excelHeaderList.add("이용구분");
        excelHeaderList.add("딜러ID");
        excelHeaderList.add("채널");

        excelDownInfo.put("headers", excelHeaderList);

        List<KbConvertExcelDTO> excelObjList = kbConsultService.excelAggregate(kbLeaseAggreGateDTO);
        excelDownInfo.put("list", excelObjList);

        ModelAndView modelAndView = new ModelAndView("excelView", "resultList", excelDownInfo);
        return modelAndView;
    }

    /**
     * KB리스대상 차량 엑셀다운로드
     * @param
     * @return
     */
    @GetMapping("/excel/cmconsult/download")
    @ApiOperation(value = "상담목록 엑셀 다운로드")
    public ModelAndView getKbCmconsultExcelListDownload(@ModelAttribute KbConsultSearchDTO kbConsultSearchDTO) {


        Map<String, Object> excelDownInfo = new HashMap<>();
        excelDownInfo.put("fileName","KB_CONSULT");
        excelDownInfo.put("lastPath","consult/");

        List<String> excelHeaderList = new ArrayList<>();
        excelHeaderList.add("NO");

        excelHeaderList.add("대상차량 시도");
        excelHeaderList.add("대상차량 지역");
        excelHeaderList.add("대상차량 단지");
        excelHeaderList.add("대상차량 상사");
        excelHeaderList.add("차량소유자 성함");
        excelHeaderList.add("차량소유자 연락처");

        excelHeaderList.add("차량번호");
        excelHeaderList.add("차대번호");

        excelHeaderList.add("알선딜러 시도");
        excelHeaderList.add("알선딜러 지역");
        excelHeaderList.add("알선딜러 단지");
        excelHeaderList.add("알선딜러 상사");
        excelHeaderList.add("알선딜러 성함");
        excelHeaderList.add("알선딜러 연락처");

        excelHeaderList.add("상담고객 성함");
        excelHeaderList.add("상담고객 연락처");

        excelHeaderList.add("AP 성함");
        excelHeaderList.add("AP 연락처");
        excelHeaderList.add("상담요청일자");
        excelHeaderList.add("AP 확인여부");


        excelDownInfo.put("headers", excelHeaderList);
        List<KBConsultExcelDTO> excelObjList = kbConsultService.excelCmConsult(kbConsultSearchDTO);
        excelDownInfo.put("list", excelObjList);

        ModelAndView modelAndView = new ModelAndView("excelView", "resultList", excelDownInfo);
        return modelAndView;
    }

}
