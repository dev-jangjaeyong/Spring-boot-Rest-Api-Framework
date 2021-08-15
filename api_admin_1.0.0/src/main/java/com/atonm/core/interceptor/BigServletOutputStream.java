package com.atonm.core.interceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;

public class BigServletOutputStream extends ServletOutputStream {
    private ByteArrayOutputStream buffer;
    private static ResourceBundle lStrings = ResourceBundle
            .getBundle("javax.servlet.LocalStrings");

    public BigServletOutputStream(ByteArrayOutputStream buffer) {
        this.buffer = buffer;
    }

    @Override
    public void close() throws IOException {
        buffer.close();
    }

    @Override
    public void write(int b) throws IOException {
        buffer.write(b);
    }

    /*@Override
    public void print(String s) throws IOException {
        if (s == null)
            s = "null";
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if ((c & 65280) != 0) {
                String errMsg = lStrings.getString("err.not_iso8859_1");
                Object errArgs[] = new Object[1];
                errArgs[0] = new Character(c);
                errMsg = MessageFormat.format(errMsg, errArgs);
                throw new CharConversionException(errMsg);
            }
            write(c);
        }

    }*/

    @Override
    public void println() throws IOException {
        print("rn");
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener listener) {

    }

    @Override
    public void flush() throws IOException {
        buffer.flush();
    }

    @Override
    public void write(byte[] abyte0, int i, int j) throws IOException {
        buffer.write(abyte0, i, j);
    }

    @Override
    public void write(byte[] abyte0) throws IOException {
        buffer.write(abyte0);
    }
}
