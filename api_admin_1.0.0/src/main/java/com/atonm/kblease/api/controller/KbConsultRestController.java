package com.atonm.kblease.api.controller;

import com.atonm.core.api.ApiResponse;
import com.atonm.kblease.api.common.base.BaseRestController;
import com.atonm.kblease.api.dto.*;
import com.atonm.kblease.api.service.KbConsultService;
import com.atonm.kblease.api.utils.annotation.HttpGetMapping;
import com.atonm.kblease.api.utils.annotation.HttpPostMapping;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/consult")
public class KbConsultRestController extends BaseRestController {

    private final KbConsultService kbConsultService;


    /*
     * 카매니저 리스상담신청 데이터 total count
     */
    @HttpGetMapping("/cmconsultcount")
    @ApiOperation(value = "검색된 카매니저 리스상담 count룰 가져옵니다.")
    public ResponseEntity<ApiResponse> cmConsultCount(HttpServletRequest req, HttpSession session, @ModelAttribute KbConsultSearchDTO reqDto) {
        return ok(kbConsultService.cmConsultCount(reqDto));
    }

    /*
     * 카매니저 리스상담신청 데이터 get
     */
    @HttpGetMapping("/cmconsult")
    @ApiOperation(value = "검색된 카매니저 리스상담 데이터를 가져옵니다.")
    public ResponseEntity<ApiResponse> cmConsult(HttpServletRequest req, HttpSession session, @ModelAttribute KbConsultSearchDTO reqDto) {
        return ok(kbConsultService.cmConsult(reqDto));
    }

    @PostMapping("/statistics")
    @ApiOperation(value = "상담/견적통계 조회")
    public ResponseEntity<ApiResponse> statics(HttpServletRequest req, HttpSession session, @ModelAttribute KbConsultStaticsDTO reqDto) {
        return ok(kbConsultService.statics(reqDto));
    }

    /*
     * 알림톡 예상견적서 데이터를 가져옵니다.
     */
    @HttpGetMapping("/consultdetail")
    @ApiOperation(value = "예상견적서 데이터를 가져옵니다")
    public ResponseEntity<ApiResponse> consultDetailCallByAdmin(HttpServletRequest req, HttpSession session, @ModelAttribute ExtReqDTO reqDto) {
        return ok(kbConsultService.consultDetailCallByAdmin(reqDto));
    }

    @HttpGetMapping("/aggregatecount")
    @ApiOperation(value = "이용집계현황 total count")
    public ResponseEntity<ApiResponse> aggregateCount(HttpServletRequest req, HttpSession session, @ModelAttribute KbLeaseAggreGateDTO reqDto) {
        return ok(kbConsultService.aggregateCount(reqDto));
    }

    @HttpGetMapping("/aggregate")
    @ApiOperation(value = "이용집계현황 정보 조회.")
    public ResponseEntity<ApiResponse> aggregate(HttpServletRequest req, HttpSession session, @ModelAttribute KbLeaseAggreGateDTO reqDto) {
        return ok(kbConsultService.aggregate(reqDto));
    }

    @HttpGetMapping("/statement")
    @ApiOperation(value = "이용집계통계")
    public ResponseEntity<ApiResponse> numericalStatement(HttpServletRequest req, HttpSession session, @ModelAttribute KbleaseNumericalStatementDTO reqDto) {
        return ok(kbConsultService.numericalStatement(reqDto));
    }
}

