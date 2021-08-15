package com.atonm.kblease.api.service;

import com.atonm.core.api.ApiResponse;
import com.atonm.kblease.api.common.base.BaseService;
import com.atonm.kblease.api.common.base.UserRefreshTokenDTO;
import com.atonm.kblease.api.common.base.UserRefreshTokenInfoDTO;
import com.atonm.kblease.api.common.mapper.UserTokenMapper;
import com.atonm.kblease.api.utils.SecurityUtils;
import com.atonm.kblease.api.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.atonm.core.common.constant.Constant.REFRESH_TOKEN_UID_HEADER_NAME;

@Service
@RequiredArgsConstructor
public class KbUserService extends BaseService {
	private final UserTokenMapper userTokenMapper;

	public ApiResponse logoutProc(HttpServletRequest request) {
		UserRefreshTokenInfoDTO userRefreshTokenInfoDTO = new UserRefreshTokenInfoDTO();
		userRefreshTokenInfoDTO.setSId(getUid(request));
		userRefreshTokenInfoDTO.setPid("");
		userTokenMapper.deleteUserToken(userRefreshTokenInfoDTO);

    	HttpSession session = request.getSession(false);
		session.invalidate();

		return logoutok(null);
	}

	public ApiResponse loginRole(HttpServletRequest requesst) {
    	return ok(SecurityUtils.getCurrentUserInfo().getUserRoles());
	}


	private String getUid(HttpServletRequest request) {
        String uid = "";
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("_kblu")) {
                    uid = cookie.getValue();
                    break;
                }
            }
        }

        if(StringUtil.isEmpty(uid)) {
            uid = request.getHeader(REFRESH_TOKEN_UID_HEADER_NAME);
        }

        return StringUtil.isEmpty(uid) ? "" : uid;
    }
}
