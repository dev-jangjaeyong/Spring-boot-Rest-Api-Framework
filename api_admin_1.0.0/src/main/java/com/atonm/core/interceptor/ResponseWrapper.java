package com.atonm.core.interceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseWrapper extends HttpServletResponseWrapper {
    private int statusCode=200;

    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private PrintWriter writer = null;

    private Map<String, String> headerMaps = new LinkedHashMap<>();

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaderMaps() {
        return headerMaps;
    }

    public String getResponseText(){
        if ( writer != null ) writer.flush();
        return buffer.toString();
    }

    public byte[] getResponseBinary(){
        if ( writer != null ) writer.flush();
        return buffer.toByteArray();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
//		return super.getOutputStream();
        ServletOutputStream sos = new BigServletOutputStream(buffer);
        return sos;
    }

    @Override
    public PrintWriter getWriter(){
        try {
            writer = new PrintWriter(new OutputStreamWriter(buffer, "utf-8"));
            return writer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addIntHeader(String name, int value) {
        headerMaps.put(name, String.valueOf(value));
        super.addIntHeader(name, value);
    }

    @Override
    public void setHeader(String name, String value) {
        headerMaps.put(name, value);
        super.setHeader(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        headerMaps.put(name, String.valueOf(value));
        super.setIntHeader(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        headerMaps.put(name, value);
        super.addHeader(name, value);
    }

    @Override
    public void sendError(int sc) throws IOException {
        statusCode=sc;
        super.sendError(sc);
    }

    @Override
    public void setStatus(int sc) {
        statusCode=sc;
        super.setStatus(sc);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        statusCode=sc;
        super.sendError(sc, msg);
    }

    @Override
    public void setStatus(int sc, String sm) {
        statusCode=sc;
        super.setStatus(sc, sm);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        setHeader("location", location);
        setStatus(302);
        super.sendRedirect(location);
    }
}
