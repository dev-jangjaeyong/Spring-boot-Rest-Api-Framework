package com.atonm.core.interceptor;

import com.atonm.core.interceptor.xss.XssFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

class WrappedRequestWithParameter extends HttpServletRequestWrapper {
    private final Map<String, String[]> modifiableParameters;
    private Map<String, String[]> allParameters = null;

    public WrappedRequestWithParameter(final HttpServletRequest request, final Map<String, String[]> additionalParams) {
        super(request);
        modifiableParameters = new TreeMap<String, String[]>();
        modifiableParameters.putAll(additionalParams);
    }

    @Override
    public String getParameter(final String name) {
        String[] strings = getParameterMap().get(name);
        if (strings != null) {
            return strings[0];
        }
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (allParameters == null) {
            allParameters = new TreeMap<String, String[]>();

            Map<String, String[]> _cloneParamMap = new HashMap<>();
            _cloneParamMap = super.getParameterMap();

            Map<String, String[]> xssFilteringMap = new HashMap<>();

            for(String key: _cloneParamMap.keySet()) {
                String[] encodedValues = _cloneParamMap.get(key);

                int i = 0;
                for (String value: encodedValues) {
                    encodedValues[i] = XssFilter.cleanXSS(value);
                    i++;
                }

                xssFilteringMap.put(key, encodedValues);
            }

            allParameters.putAll(xssFilteringMap);
            allParameters.putAll(modifiableParameters);
        }
        // Return an unmodifiable collection because we need to uphold the interface contract.
        return Collections.unmodifiableMap(allParameters);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(final String name) {
    	if(name == null) return null;
        return getParameterMap().get(name);
    }
}
