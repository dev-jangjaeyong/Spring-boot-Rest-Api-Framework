package com.atonm.kblease.api.config.define;
import com.atonm.core.common.constant.Constant;
import com.atonm.kblease.api.config.define.bean.CoCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class CoDefineManager {
	@Autowired
	private final JdbcTemplate jdbc;

	protected static Log logger = LogFactory.getLog("CoDefineManager");
	private static CoDefineManager instance;
	private static HashMap<String, ArrayList<Map<String, Object>>> codes;
	private static CoDefineData coDefineData = null;



	@Autowired
	public void setCdMgr() {
		init();
	}

	public void setCdMgrReInit() {
		connectAllApiList();
	}

	public void reinitSingleton() {
		reinit();
	}

	@Autowired
	public static CoDefineManager getInstance() {
		return instance;
	}

	@Autowired
	private CoDefineManager(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
		instance = this;
	}

	private void init() {
		coDefineData = new CoDefineData(jdbc);
		codes = new HashMap<String, ArrayList<Map<String, Object>>>();
		connectAllApiList();
	}

	private void reinit() {
		codes = new HashMap<String, ArrayList<Map<String, Object>>>();
		connectAllApiList();
	}

	@SuppressWarnings({ "unchecked" })
	private void connectAllApiList() {
		codes.put(Constant.KBL_USP_RESIDUAL_GROUPBY, coDefineData.getResidualPerCodes());
		codes.put(Constant.KBL_ADMIN_API_ROLE, coDefineData.getApiRoleList());
		codes.put(Constant.KBL_ADMIN_MENU_ROLE, coDefineData.getMenuRoleList());
	}

	@SuppressWarnings("unused")
	private static CoCode getCodeInstance(String s) {
		CoCode code = new CoCode(codes.get(s));
		if (code == null) {
			logger.warn((new StringBuilder()).append("Code No.").append(s)
					.append(" is not found.").toString());
		}
		return code;
	}

	public static ArrayList<Map<String, Object>> getValues(String s) {
		CoCode code = getCodeInstance(s);
		return code != null ? code.getCdArrayList() : null;
	}
}
