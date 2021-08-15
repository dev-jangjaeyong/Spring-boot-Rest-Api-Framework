package com.atonm.core.interceptor;

import org.codehaus.plexus.util.IOUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

//import javax.servlet.ServletInputStream;

public class RequestWrapper extends HttpServletRequestWrapper {
    private byte[] bytes;
    private String requestBody;
    private ServletImpl servletStream;
    private HttpServletRequest _request;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        _request = request;
        InputStream in = super.getInputStream();
        bytes = IOUtil.toByteArray(in);
        requestBody = new String(bytes);
        this.servletStream = new ServletImpl();

        /*ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();

        map = mapper.readValue(new String(bytes), new TypeReference<Map<String, String>>(){});


        for(String modelParamKey : request.getParameterMap().keySet()) {
            String value = request.getParameterMap().get(modelParamKey)[0];
            map.put(modelParamKey, value);
        }

        requestBody = mapper.writeValueAsString(map);*/
    }

    public void resetInputStream(byte[] newRawData) throws IOException {
        /*this.servletStream = new ServletImpl();
        servletStream.is = new ByteArrayInputStream(newRawData);*/
        bytes = newRawData;
        this.getInputStream();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        /*final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        return new ServletImpl(bis);*/
        /*if (bytes == null) {
            bytes = IOUtils.toByteArray(this._request.getReader());
            servletStream.is = new ByteArrayInputStream(bytes);
        }*/servletStream.is = new ByteArrayInputStream(bytes);

        return servletStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.requestBody;
    }


    class ServletImpl extends ServletInputStream {
        private InputStream is;
        /*public ServletImpl(InputStream bis) {
            is = bis;
        }*/
        @Override
        public int read() throws IOException {
            return is.read();
        }
        @Override
        public int read(byte[] b) throws IOException {
            return is.read(b);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {
        }
    }
}
