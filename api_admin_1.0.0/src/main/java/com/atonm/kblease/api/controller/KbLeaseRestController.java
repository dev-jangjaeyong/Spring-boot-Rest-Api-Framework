package com.atonm.kblease.api.controller;

import com.atonm.core.api.ApiResponse;
import com.atonm.kblease.api.dto.*;
import com.atonm.kblease.api.service.KbLeaseService;
import com.atonm.kblease.api.service.KbUserService;
import com.atonm.kblease.api.common.base.BaseRestController;
import com.atonm.kblease.api.utils.annotation.HttpGetMapping;
import com.atonm.kblease.api.utils.annotation.HttpPostMapping;
import com.atonm.kblease.api.utils.annotation.HttpPutMapping;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/lease")
public class KbLeaseRestController extends BaseRestController {

    private final KbLeaseService kbLeaseService;
    private final KbUserService kbUserService;

    @PutMapping("/editcalculatoropt")
    @ApiOperation(value = "리스조건관리 > 리스가격설정 변경")
    public ResponseEntity<ApiResponse> editCalculatorOpt (HttpServletRequest req, HttpSession session, @ModelAttribute KbCalculatorDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ editcalculatoropt ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        return ok(kbLeaseService.editCalculatorOpt(reqDto));
    }

    @HttpGetMapping("/codemappingcount")
    @ApiOperation(value = "리스조건관리 > 리스대창 차량종류 카운트")
    public ResponseEntity<ApiResponse> codeMappingCount (HttpServletRequest req, HttpSession session, @ModelAttribute KbConvertDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ codemappingcount ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        return ok(kbLeaseService.codeMappingCount(reqDto));
    }

    @HttpGetMapping("/mappingcar")
    @ApiOperation(value = "리스조건관리 > 리스대창 차량종류 조회")
    public ResponseEntity<ApiResponse> codeMapping (HttpServletRequest req, HttpSession session, @ModelAttribute KbConvertDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ mappingcar ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        return ok(kbLeaseService.mappingCar(reqDto));
    }
    @PutMapping("/editconnvertrsd")
    @ApiOperation(value = "KB차량코드매핑 차량 잔가군 코드 변경")
    public ResponseEntity<ApiResponse> editConnvertResidualCode (HttpServletRequest req, HttpSession session, @ModelAttribute KbConvertDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ editconnvertrsd ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        System.out.println("############ editconnvertrsd ############");
        System.out.println("reqDto = " + reqDto);
        System.out.println("############ ############ ############");
        return ok(kbLeaseService.editConnvertRsd(reqDto));
    }
    @HttpGetMapping("/leasemanagementcount")
    @ApiOperation(value = "리스조건관리 > 리스차량관리 카운트")
    public ResponseEntity<ApiResponse> leaseManagementCount (HttpServletRequest req, HttpSession session, @ModelAttribute KbConvertDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ leasemanagementcount ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        return ok(kbLeaseService.leaseManagementCount(reqDto));
    }
    @HttpGetMapping("/leasemanagement")
    @ApiOperation(value = "리스차량관리 > 리스차량목록 조회")
    public ResponseEntity<ApiResponse> leaseManagement (HttpServletRequest req, HttpSession session, @ModelAttribute KbConvertDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ LeaseManagement ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        return ok(kbLeaseService.leaseManagement(reqDto));
    }

    @PutMapping("/editleasestate")
    @ApiOperation(value = "리스조건관리 > 리스차량 추가 및 제외")
    public ResponseEntity<ApiResponse> editLeaseState (HttpServletRequest req, HttpSession session, @ModelAttribute KbLeaseCarDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ EditLeaseState ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        return ok(kbLeaseService.editLeaseState(reqDto));
    }


    @HttpGetMapping("/codemanagementcount")
    @ApiOperation(value = "잔가군 코드 관리 > 잔가군 코드 카운트")
    public ResponseEntity<ApiResponse> codeManagementCount (HttpServletRequest req, HttpSession session, @RequestParam Map<String, Object> reqMap) {
        if (log.isDebugEnabled()) log.debug("############ codemanagementcount ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqMap.toString());
        return ok(kbLeaseService.codeManagementCount(reqMap));
    }

    @HttpGetMapping("/codemanagement")
    @ApiOperation(value = "잔가군 코드 관리 > 잔가군 코드 리스트")
    public ResponseEntity<ApiResponse> codeManagementList (HttpServletRequest req, HttpSession session, @RequestParam Map<String, Object> reqMap) {
        if (log.isDebugEnabled()) log.debug("############ codemanagement ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqMap.toString());
        return ok(kbLeaseService.codeManagement(reqMap));
    }

    @PostMapping("/regresidualcode")
    @ApiOperation(value = "잔가군 코드 관리 > 잔가군 코드 신규 입력")
    public ResponseEntity<ApiResponse> setResidualCode (HttpServletRequest req, HttpSession session, @ModelAttribute KBLeaseCodeDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ setresidualcode ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        return ok(kbLeaseService.setResidualCode(reqDto));
    }

    @PutMapping("/editresidualcode")
    @ApiOperation(value = "잔가군 코드 관리 > 잔가군 코드 수정")
    public ResponseEntity<ApiResponse> editResidualCode (HttpServletRequest req, HttpSession session, @ModelAttribute KBLeaseCodeDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ editresidualcode ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        return ok(kbLeaseService.editResidualCode(reqDto));
    }

    @HttpGetMapping("/termmanagement")
    @ApiOperation(value = "리스 조건 설정 > 리스 조건 조회")
    public ResponseEntity<ApiResponse> termManagement (HttpServletRequest req, HttpSession session, @ModelAttribute KbTermDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ editresidualcode ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        return ok(kbLeaseService.termManagement(reqDto));
    }

    @PutMapping("/edittermcode")
    @ApiOperation(value = "리스조건관리 > 리스가격설정 변경")
    public ResponseEntity<ApiResponse> editTermCode (HttpServletRequest req, HttpSession session, @ModelAttribute KbTermListDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ edittermcode ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        return ok(kbLeaseService.editTermCode(reqDto));
    }

    @HttpGetMapping("/leasebatchcount")
    @ApiOperation(value = "KB리스 TA_CAR 일별 통계")
    public ResponseEntity<ApiResponse> leaseBatchCount (HttpServletRequest req, HttpSession session, @ModelAttribute KbLeaseBatchDTO reqDto) {
        if (log.isDebugEnabled()) log.debug("############ leaseBatchCount ############");
        if (log.isDebugEnabled()) log.debug("========= reqMap : " + reqDto.toString());
        return ok(kbLeaseService.leaseBatchCount(reqDto));
    }

}
