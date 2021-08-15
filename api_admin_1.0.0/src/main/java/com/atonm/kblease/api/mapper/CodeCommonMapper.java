package com.atonm.kblease.api.mapper;


import com.atonm.kblease.api.config.datasource.annotation.Master;

import java.util.HashMap;
import java.util.List;

@Master
public interface CodeCommonMapper {

	/**
	 * 시도 정보리스트 조회
	 * @return
	 */
	public List<HashMap<String, Object>> getBaseCodeSido();

	/**
	 * 도시 정보리스트 조회
	 * @return
	 */
	public List<HashMap<String, Object>> getBaseCodeSidoArea();

	/**
	 * 단지 정보리스트 조회
	 * @return
	 */
	public List<HashMap<String, Object>> getBaseCodeDanjiArea();

	/**
	 * 상사 정보리스트 조회
	 * @return
	 */
	public List<HashMap<String, Object>> getBaseCodeShop();
}
