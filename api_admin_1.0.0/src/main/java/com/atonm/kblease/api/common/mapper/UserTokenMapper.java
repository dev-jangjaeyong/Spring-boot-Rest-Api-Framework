package com.atonm.kblease.api.common.mapper;


import com.atonm.kblease.api.common.base.UserRefreshTokenDTO;
import com.atonm.kblease.api.common.base.UserRefreshTokenInfoDTO;
import com.atonm.kblease.api.common.base.UserRoleHrcDTO;
import com.atonm.kblease.api.config.datasource.annotation.Master;

@Master
public interface UserTokenMapper {
	UserRefreshTokenInfoDTO selectUserToken(UserRefreshTokenInfoDTO userRefreshTokenInfoDTO);
	UserRefreshTokenInfoDTO selectUserTokenAll(UserRefreshTokenInfoDTO userRefreshTokenInfoDTO);
	int insertUserToken(UserRefreshTokenInfoDTO userRefreshTokenInfoDTO);
	int deleteUserToken(UserRefreshTokenInfoDTO userRefreshTokenInfoDTO);
	UserRoleHrcDTO findUserHierarchyRoles(UserRoleHrcDTO userRoleHrcDTO);
}
