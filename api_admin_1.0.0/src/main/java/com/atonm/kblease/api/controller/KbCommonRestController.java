package com.atonm.kblease.api.controller;

import com.atonm.core.api.ApiResponse;
import com.atonm.core.common.constant.Constant;
import com.atonm.kblease.api.common.service.RedisService;
import com.atonm.kblease.api.dto.KbApInfoDTO;
import com.atonm.kblease.api.dto.KbMonthlyPaymentDTO;
import com.atonm.kblease.api.service.KbCommonServce;
import com.atonm.kblease.api.common.base.BaseRestController;
import com.atonm.kblease.api.config.define.CoDefineManager;
import com.atonm.kblease.api.utils.StringUtil;
import com.atonm.kblease.api.utils.annotation.HttpGetMapping;
import com.atonm.kblease.api.utils.annotation.HttpPostMapping;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.ApiInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/kbcommon")
@RequiredArgsConstructor
public class KbCommonRestController extends BaseRestController {

    private final KbCommonServce kbCommonServce;
    private final RedisService redisService;

    /*
     * 싱글톤을 재생성 합니다.
     */
    @HttpGetMapping("/reinit")
    @ApiOperation(value = "singleton reinit")
    public ResponseEntity<ApiResponse> getKbGroupList (String redisSetYN) {
        if(redisSetYN == null || StringUtil.isEmpty(redisSetYN)) redisSetYN = "N";
        return ok(kbCommonServce.reinit(redisSetYN));
    }


    /*
     * 넘어온 키값으로 레디스데이터를 조회합니다.
     */
    @HttpGetMapping("/fetchredisdata")
    @ApiOperation(value = "레디스를 조회하여 사용중인 해당 키에 매칭되는 데이터를 가져옵니다.")
    public ResponseEntity<ApiResponse> commonRedisGet(HttpServletRequest req, HttpSession session, @RequestParam Map<String, Object> reqMap){
        if(StringUtil.isEmpty(reqMap.get("redisKey"))) return null;

        return ok(kbCommonServce.returnRedis(reqMap.get("redisKey").toString(), "N"));
    }

    /**
     * KB 리스 계산기
     * @param kbMonthlyPaymentDTO
     * @return
     */
    @HttpGetMapping("/month-payment")
    @ApiOperation(value="KB리스 계산기")
    public ResponseEntity<ApiResponse> monthlyEstimatePayment(@RequestBody  KbMonthlyPaymentDTO kbMonthlyPaymentDTO) {
        return ok(kbCommonServce.calMonthlyEstimatePayment(kbMonthlyPaymentDTO));
    }

    /**
     * 딜러 중계수수료를 조회하비다.(KB 기준)
     * @return
     */
    @HttpGetMapping("/dealer-fee")
    @ApiOperation(value="KB리스 딜러중계수수료 조회")
    public ResponseEntity<ApiResponse> dealerFee() {
        return ok(kbCommonServce.getDealerFee());
    }

}
