package com.atonm.kblease.api.controller;

import com.atonm.core.api.ApiResponse;
import com.atonm.core.common.constant.Constant;
import com.atonm.kblease.api.common.base.BaseRestController;
import com.atonm.kblease.api.config.define.CoDefineManager;
import com.atonm.kblease.api.service.CommonCodeService;
import com.atonm.kblease.api.service.KbCommonServce;
import com.atonm.kblease.api.utils.SecurityUtils;
import com.atonm.kblease.api.utils.StringUtil;
import com.atonm.kblease.api.utils.annotation.HttpGetMapping;
import com.atonm.kblease.api.utils.annotation.HttpPostMapping;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/common-code")
public class CommonCodeRestController extends BaseRestController {
    private final CommonCodeService commonCodeService;
    private final KbCommonServce kbCommonServce;

    /**
     * 시도 코드 조회
     * @return
     */
    @HttpGetMapping(value = "/sido")
    @ApiOperation(value = "시도코드 조회")
    public ResponseEntity<ApiResponse> getBaseCodeSido() {
        return ok(commonCodeService.getBaseCodeSido());
    }

    /**
     * 시도 코드 조회
     * @return
     */
    @HttpGetMapping(value = "/gen/sido")
    @ApiOperation(value = "시도코드 generate")
    public ResponseEntity<ApiResponse> getGenBaseCodeSido() {
        return ok(commonCodeService.getGenBaseCodeSido());
    }

    /**
     * 시도 코드 조회
     * @return
     */
    @HttpGetMapping(value = "/gen/city")
    @ApiOperation(value = "시도코드 generate")
    public ResponseEntity<ApiResponse> getGenBaseCodeCity() {
        return ok(commonCodeService.getGenBaseCodeCity());
    }

    /**
     * 시도 코드 조회
     * @return
     */
    @HttpGetMapping(value = "/gen/danji")
    @ApiOperation(value = "단지코드 generate")
    public ResponseEntity<ApiResponse> getGenBaseCodeDanji() {
        return ok(commonCodeService.getGenBaseCodeDanji());
    }

    /**
     * 시도 코드 조회
     * @return
     */
    @HttpGetMapping(value = "/gen/shop")
    @ApiOperation(value = "상사코드 generate")
    public ResponseEntity<ApiResponse> getGenBaseCodeShop() {
        return ok(commonCodeService.getGenBaseCodeShop());
    }


    @HttpGetMapping(value="/menu-role")
    @ApiOperation(value="메뉴권한 리스트 조회")
    public ResponseEntity<ApiResponse> getUserMenuRoleList() {
        return ok(ApiResponse.ok(CoDefineManager.getValues(Constant.KBL_ADMIN_MENU_ROLE)));
    }

    @HttpGetMapping(value="/user-role")
    @ApiOperation(value="유저 role 게층 조회")
    public ResponseEntity<ApiResponse> getUserRoleHrc() {
        return ok(ApiResponse.ok(SecurityUtils.getCurrentUserInfo().getRoleHierarchy()));
    }

    /*
     * 싱글톤을 재생성 호출부 합니다.
     */
    @HttpGetMapping("/reinit")
    @ApiOperation(value = "singleton reinit call")
    public ResponseEntity<ApiResponse> reinitCall () {
        kbCommonServce.reinitCall();
        return ok();
    }

    /*
     * 싱글톤을 재생성 - 구현 Controller 합니다.
     */
    @HttpGetMapping("/reinitSingleTon")
    @ApiOperation(value = "singleton reinit start")
    public ResponseEntity<ApiResponse> reinitSingleTon () {
        kbCommonServce.reinitSingleTon();
        return ok();
    }
}
