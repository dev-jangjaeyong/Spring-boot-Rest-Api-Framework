package com.atonm.kblease.api.controller;

import com.atonm.core.api.ApiResponse;
import com.atonm.core.interceptor.ClientInfo;
import com.atonm.kblease.api.common.base.BaseRestController;
import com.atonm.kblease.api.dto.*;
import com.atonm.kblease.api.log.dto.ActionLogDTO;
import com.atonm.kblease.api.log.dto.LeaseCallLogDTO;
import com.atonm.kblease.api.service.ExtServce;
import com.atonm.kblease.api.service.KbCommonServce;
import com.atonm.kblease.api.service.KbConsultService;
import com.atonm.kblease.api.utils.annotation.HttpGetMapping;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/ext")
@RequiredArgsConstructor
public class ExtRestController extends BaseRestController {

    private final KbCommonServce kbCommonServce;
    private final KbConsultService kbConsultService;
    private final ExtServce extServce;

    /**
     * KB 리스 계산기
     * @param kbMonthlyPaymentDTO
     * @return
     */
    @HttpGetMapping("/month-payment")
    @ApiOperation(value="KB리스 계산기")
    public ResponseEntity<ApiResponse> monthlyEstimatePayment(@RequestBody KbMonthlyPaymentDTO kbMonthlyPaymentDTO) {
        return ok(kbCommonServce.calMonthlyEstimatePayment(kbMonthlyPaymentDTO));
    }

    /**
     * 딜러 중계수수료를 조회하비다.(KB 기준)
     * @return
     */
    @HttpGetMapping("/dealer-fee")
    @ApiOperation(value="KB리스 딜러중계수수료 조회")
    public ResponseEntity<ApiResponse> dealerFee(@RequestBody ExtReqDTO reqDTO) {
        return ok(kbCommonServce.getDealerFee());
    }

    /**
     * 딜러 중계수수료를 조회하비다.(KB 기준)
     * @return
     */
    @HttpGetMapping("/cmcalcoptinit")
    @ApiOperation(value="카매니저 리스 계산기에 필요한 정보를 조회합니다.")
    public ResponseEntity<ApiResponse> cmCalcOptioninit(@RequestBody KbApInfoDTO kbApInfoDTO) {
        return ok(kbCommonServce.cmCalcOptInit(kbApInfoDTO));
    }

    /**
     * ShopNo로 담당 AP기본정보 조회
     * @return
     */
    @HttpGetMapping("/apfind")
    @ApiOperation(value="KB리스 - 카매니저 shopNo로 담당 AP조회(단건)")
    public ResponseEntity<ApiResponse> apFind(@RequestBody KbApInfoDTO kbApInfoDTO) {
        return ok(kbCommonServce.apFindBySHopNo(kbApInfoDTO));
    }


    /*
     * 카매니저 리스상담신청 데이터 set
     */
    @HttpGetMapping("/regconsult")
    @ApiOperation(value = "카매니저 리스상담 데이터를 적재후 알림톡을 보냅니다.")
    public ResponseEntity<ApiResponse> cmLeaseConsult(HttpServletRequest req, HttpSession session, @RequestBody KbConsultSetDTO reqDto) {
        return ok(kbConsultService.cmLeaseConsultSet(reqDto));
    }

    /**
     * 알림톡 연결 페이지 제공을 위한 정보취득
     * @param alimTokenDTO
     * @return
     */
    @HttpGetMapping("/aliminfo")
    @ApiOperation(value="KB리스 - 알림톡 연결 페이지 제공을 위한 정보취득")
    public ResponseEntity<ApiResponse> alimInfo(@ModelAttribute AlimTokenDTO alimTokenDTO) {
        return ok(extServce.alimInfo(alimTokenDTO));
    }

    /*
     * 알림톡 > 카매니저 리스상담 요청서
     */
    @HttpGetMapping("/consult-detail")
    @ApiOperation(value = "예상견적서 데이터를 가져옵니다")
    public ResponseEntity<ApiResponse> consultDetail(HttpServletRequest req, HttpSession session, @ModelAttribute ExtReqDTO reqDto) {
        return ok(kbConsultService.consultDetail(reqDto));
    }

    /*
     * 알림톡 > 카매니저 리스상담 요청서
     */
    @HttpGetMapping("/leaselogcall")
    @ApiOperation(value = "api호출전 로그테이블 호출 입니다")
    public ResponseEntity<ApiResponse> logCallData(HttpServletRequest req, HttpSession session, @RequestBody LeaseCallLogDTO reqDto) {
        ClientInfo clientInfo = new ClientInfo(req);
        reqDto.setIpAddress(clientInfo.getClientIpAddr());
        kbCommonServce.logCallData(reqDto);
        return ok();
    }
}
