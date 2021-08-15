package com.atonm.kblease.api.config.define.bean;

import java.util.ArrayList;
import java.util.Map;

// TODO: Auto-generated Javadoc

/**
 * The Class CoCode.
 */
public class CoCode {

    private ArrayList<Map<String, Object>> cdArrayList;

    /**
     * Gets the cd nm.
     *
     * @return the string
     */
    public ArrayList<Map<String, Object>> getCdArrayList() {
        return this.cdArrayList;
    }

    /**
     * Sets the cd nm.
     *
     * @param cdNm the new cd nm
     */
    /*public void setCdArrayList(ArrayList<HashMap<String, String>> cdDetailMap) {
        this.cdArrayList.put(cdDetailMap);
    }*/

    /**
     * Instantiates a new co code.
     *
     * @param
     */
    /*public CoCode(ArrayList<HashMap<String, String>> cdArrayList)
    {
    	this.cdArrayList = cdArrayList;
    }*/
    public CoCode(ArrayList<Map<String, Object>> cdArrayList)
    {
        this.cdArrayList = cdArrayList;
    }
}
