package com.atonm.kblease.api.controller;

import com.atonm.core.api.ApiResponse;
import com.atonm.kblease.api.common.base.BaseRestController;
import com.atonm.kblease.api.dto.KbApInfoDTO;
import com.atonm.kblease.api.dto.KbCalculatorDTO;
import com.atonm.kblease.api.dto.KbShopInfoDTO;
import com.atonm.kblease.api.dto.KbShopInfoListDTO;
import com.atonm.kblease.api.service.KbApService;
import com.atonm.kblease.api.utils.StringUtil;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/ap")
public class KbApRestController extends BaseRestController {

    private final KbApService kbApService;

    /*
     * ap 관리자 디테일 조회합니다.
     */
    @HttpGetMapping("/apdetail")
    @ApiOperation(value = "AP 관리자를 조회 합니다.")
    public ResponseEntity<ApiResponse> apDetail(HttpServletRequest req, HttpSession session, @ModelAttribute KbApInfoDTO reqDto) {
        return ok(kbApService.apdetail(reqDto));
    }

    /*
     * ap 관리자의 리스트 카운트를 가져옵니다.
     */
    @HttpGetMapping("/apmanagementcount")
    @ApiOperation(value = "AP 관리자의 검색된 카운터를 가져옵니다.")
    public ResponseEntity<ApiResponse> apManagementCount(HttpServletRequest req, HttpSession session, @ModelAttribute KbApInfoDTO reqDto) {
        return ok(kbApService.apManagementCount(reqDto));
    }

    /*
     * ap 관리자의 리스트 데이터를 가져옵니다.
     */
    @HttpGetMapping("/apmanagement")
    @ApiOperation(value = "AP 관리자의 검색된 리스트를 불러옵니다.")
    public ResponseEntity<ApiResponse> apManagement(HttpServletRequest req, HttpSession session, @ModelAttribute KbApInfoDTO reqDto) {
        return ok(kbApService.apManagement(reqDto));
    }

    /*
     * ap 관리자를 등록 및 수정 전 활동중인 AP의 HP를 중복체크합니다.
     */
    @HttpGetMapping("/hpduplcheck")
    @ApiOperation(value = "활동AP 관리자의 HP를 중복체크합니다.")
    public ResponseEntity<ApiResponse> hpDuplicateCheck(HttpServletRequest req, HttpSession session, @ModelAttribute KbApInfoDTO reqDto) {
        return ok(kbApService.hpDuplicateCheck(reqDto));
    }

    /*
     * ap 관리자를 등록합니다
     */
    @HttpPostMapping("/apreg")
    @ApiOperation(value = "AP 관리자를 등록 합니다.")
    public ResponseEntity<ApiResponse> apResist(HttpServletRequest req, HttpSession session, @ModelAttribute KbApInfoDTO reqDto) {
        return ok(kbApService.apRegsist(reqDto));
    }

    /*
     * ap 관리자를 수정합니다.
     */
    @HttpGetMapping("/apnameall")
    @ApiOperation(value = "AP 관리자의 목록을 사용상태 상관없이 모두 가져옵니다.")
    public ResponseEntity<ApiResponse> apNameAll(HttpServletRequest req, HttpSession session, @ModelAttribute KbApInfoDTO reqDto) {
        return ok(kbApService.apNameAll(reqDto));
    }
    /*
     * ap 관리자를 수정합니다.
     */
    @PutMapping("/apedit")
    @ApiOperation(value = "AP 관리자를 수정 합니다.")
    public ResponseEntity<ApiResponse> apEdit(HttpServletRequest req, HttpSession session, @ModelAttribute KbApInfoDTO reqDto) {
        return ok(kbApService.apEdit(reqDto));
    }


    /*
     * 상사별 담당 ap 설정 리스트 조회
     */
    @HttpGetMapping("/shopmanagement")
    @ApiOperation(value = "AP 관리자를 등록 합니다.")
    public ResponseEntity<ApiResponse> shopManagement(HttpServletRequest req, HttpSession session, @ModelAttribute KbShopInfoDTO reqDto) {
        return ok(kbApService.shopManagement(reqDto));
    }

    /*
     * 상사별 담당 ap 설정 카운트 조회
     */
    @HttpGetMapping("/shopmanagementcount")
    @ApiOperation(value = "AP 관리자를 등록 합니다.")
    public ResponseEntity<ApiResponse> shopManagementCount(HttpServletRequest req, HttpSession session, @ModelAttribute KbShopInfoDTO reqDto) {
        return ok(kbApService.shopManagementCount(reqDto));
    }

    /*
     * 상사별 담당 ap 등록
     */
    @HttpPostMapping("/shopinsert")
    @ApiOperation(value = "AP 관리자를 등록 합니다.")
    public ResponseEntity<ApiResponse> shopInsert(HttpServletRequest req, HttpSession session, @ModelAttribute KbShopInfoListDTO reqDto) {
        return ok(kbApService.shopInsert(reqDto));
    }

    /*
     * 상사별 담당 ap 수정
     */
    @PutMapping("/shopupdate")
    @ApiOperation(value = "AP 관리자를 수정 합니다.")
    public ResponseEntity<ApiResponse> shopUpdate(HttpServletRequest req, HttpSession session, @ModelAttribute KbShopInfoListDTO reqDto) {
        return ok(kbApService.shopUpdate(reqDto));
    }

    /*
     * 상사별 담당 ap 설정 리스트 조회
     */
    @HttpGetMapping("/shopmanagement_layer")
    @ApiOperation(value = "AP 관리자를 등록 합니다.")
    public ResponseEntity<ApiResponse> ShopManagementApList(HttpServletRequest req, HttpSession session, @ModelAttribute KbApInfoDTO reqDto) {
        return ok(kbApService.apManagement(reqDto));
    }
}
