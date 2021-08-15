package com.atonm.kblease.api.config.define;
import com.atonm.kblease.api.utils.ModelMapperUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.MappingException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CoDefineData {
	private final JdbcTemplate jdbc;

	protected static Log logger = LogFactory.getLog("CoDefineData");

	public CoDefineData(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public ArrayList<Map<String, Object>> getSetUpCodes() {
		ArrayList<Map<String, Object>> SETUP_CODE_LIST = (ArrayList<Map<String, Object>>)jdbc.queryForList(
		"SELECT TA.GROUP_ID as groupId" +
				",TA.GROUP_NAME as groupName" +
				",TA.DISP_GROUP_NAME as dispGroupName" +
				",TA.ORG_ID as orgId" +
				",TB.CODE_NAME as codeName" +
				",TB.DISP_NAME as dispName" +
				",TB.CODE_VALUE as codeValue" +
				",TB.SORT_NO as sortNo" +
				",TB.CODE_DESC as codeDesc" +
				",TB.ATTR as attr " +
			"FROM KBLease.dbo.TA_SETUPCODE_GROUP AS TA WITH(NOLOCK) " +
			"INNER JOIN KBLease.dbo.TA_SETUPCODE AS TB WITH(NOLOCK) ON TA.GROUP_ID = TB.GROUP_ID " +
			"WHERE TA.USE_YN = \'Y\' AND TB.USE_YN = \'Y\' ORDER BY TA.GROUP_ID, SETUPCODE_ID");
			//      "WHERE TA.USE_YN = \'Y\' AND TB.USE_YN = \'Y\' AND TA.ORG_ID = 1 ORDER BY TA.GROUP_ID, SETUPCODE_ID");
			return SETUP_CODE_LIST;
	}

	public ArrayList<Map<String, Object>> getResidualPerCodes() {
		ArrayList<Map<String, Object>> RESIDUAL_CODE_LIST = (ArrayList<Map<String, Object>>)jdbc.queryForList(
		"SELECT CODE_ID as codeId " +
				",ORG_ID as orgId " +
				",RESIDUAL_12_RATE as residual12Rate " +
				",RESIDUAL_24_RATE as residual24Rate " +
				",RESIDUAL_36_RATE as residual36Rate " +
				",RESIDUAL_48_RATE as residual48Rate " +
				",RESIDUAL_60_RATE as residual60Rate " +
			"FROM KBLease.dbo.TA_RESIDUAL_PER_CODE WITH(NOLOCK) " +
			"WHERE USE_YN = \'Y\'");
		return RESIDUAL_CODE_LIST;
	}

	public ArrayList<Map<String, Object>> getApiRoleList() {
		ArrayList<Map<String, Object>> API_ROLE_LiST = (ArrayList<Map<String, Object>>)jdbc.queryForList(
		"SELECT API_ID as apiId " +
				",API_CLASS_TY_CD as apiClassTyCd " +
				",API_NAME as apiName " +
				",API_URL as apiUrl " +
				",method as method " +
				",AUTHORITY as authority " +
			"FROM KBLease.dbo.TA_API_ROLE_MATCHER WITH(NOLOCK) ");
		return API_ROLE_LiST;
	}

	public ArrayList<Map<String, Object>> getMenuRoleList() {
		ArrayList<Map<String, Object>> MENU_ROLE_LiST = (ArrayList<Map<String, Object>>)jdbc.queryForList(
			"WITH R_MENU_ROLE_MATCHER AS ( " +
				"SELECT MENU_ID " +
					  ",P_MENU_ID " +
					  ",MENU_NAME " +
					  ",MENU_DISP " +
					  ",MENU_URL " +
					  ",AUTHORITY " +
					  ",MENU_SORT " +
					  ",1 AS LV " +
				"FROM KBLease.dbo.TA_MENU_ROLE_MATCHER WITH(NOLOCK) " +
				"WHERE P_MENU_ID IS NULL " +
				"UNION ALL " +
				"SELECT a.MENU_ID " +
					  ",a.P_MENU_ID " +
					  ",a.MENU_NAME " +
					  ",a.MENU_DISP " +
					  ",a.MENU_URL " +
					  ",a.AUTHORITY " +
					  ",a.MENU_SORT " +
					  ",b.LV + 1 AS LV " +
				"FROM KBLease.dbo.TA_MENU_ROLE_MATCHER AS a WITH(NOLOCK) " +
				"INNER JOIN R_MENU_ROLE_MATCHER AS b ON a.P_MENU_ID = b.MENU_ID " +
			") " +
			"SELECT * FROM R_MENU_ROLE_MATCHER ORDER BY P_MENU_ID, MENU_SORT ");
		return MENU_ROLE_LiST;
	}

	private <T> List<T> map(Iterable<?> iterableSources, Class<T> destinationClass) throws MappingException {
		return ModelMapperUtils.mapList(iterableSources, destinationClass);
	}
}
