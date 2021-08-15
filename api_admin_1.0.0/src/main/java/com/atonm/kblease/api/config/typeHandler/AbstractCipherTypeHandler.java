package com.atonm.kblease.api.config.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.atonm.kblease.api.utils.ObfuscationUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.TypeHandler;

@MappedJdbcTypes(JdbcType.VARCHAR)
public abstract class AbstractCipherTypeHandler implements TypeHandler<String> {
    /* (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#setParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        System.out.print("\n====================\n");
        System.out.print("Parameter1 => " + parameter);
        System.out.print("\n====================\n");

        // 암호화 여부 확인
        if(isCipher()){
            parameter = encode(parameter);
        }
        ps.setString(i, parameter);
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet, java.lang.String)
     */
    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        // 암호화 여부 확인
        if(isCipher()){
            value = decode(value);
        }
        return value;
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet, int)
     */
    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        // 암호화 여부 확인
        if(isCipher()){
            value = decode(value);
        }
        return value;
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.CallableStatement, int)
     */
    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        // 암호화 여부 확인
        if(isCipher()){
            value = decode(value);
        }
        return value;
    }

    /**
     * 암호화 여부
     *
     * @return 암호화 여부
     */
    protected abstract boolean isCipher();
    protected abstract boolean isCipher(String value);

    /**
     * 암호화
     *
     * @param value 변환 문자
     * @return 암호화된 문자
     */
    protected String encode(String value){
        return ObfuscationUtils.encode(value);
    }

    /**
     * 복호화
     *
     * @param value 변환 문자
     * @return 복호화된 문자
     */
    protected String decode(String value){
        return ObfuscationUtils.decode(value);
    }

    protected boolean isEncode(String value) {
        return Base64.isBase64(value);
    }
}
