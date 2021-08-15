package com.atonm.kblease.api.config.typeHandler;

import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;

@Alias("onCarCipherTypeHandler")
@MappedJdbcTypes(JdbcType.VARCHAR)
public class OnCarCipherTypeHandler extends AbstractCipherTypeHandler {
    /*@Autowired
    private CodeConfig codeConfig;

    @Qualifier("MessageProperties")
    Properties properties;*/

    @Override
    protected final boolean isCipher() {
        return true;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        String value = parameter;

        try {
            ResultSetMetaData meta = ps.getMetaData();
            String columnName =  meta.getColumnName(i);

            System.out.print("\n====================\n");
            System.out.print("Parameter => " + meta.getColumnName(i));
            System.out.print("\n====================\n");

            if(columnName.toLowerCase().equalsIgnoreCase("car_no")) {
                //if(codeConfig.getServerType().equalsIgnoreCase(properties.getProperty("message.server-type-dev"))) {
                try{
                    if(Long.valueOf(parameter).getClass().getName().toLowerCase().equalsIgnoreCase("java.lang.long") || !isCipher(parameter)) {
                        value = parameter;
                    }else{
                        value = decode(parameter);
                    }
                }catch(Exception e) {value = decode(parameter);}
            /*}else{
                value = decode(parameter);
            }*/
            }
        }catch(Exception e) {
            if(isCipher(value)) {
                value = decode(parameter);
            }
        }


        ps.setString(i, value);
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        /*System.out.print("\n====================\n");
        System.out.print("first resultset type => " +columnName);
        System.out.print("\n====================\n");*/

        String value = rs.getString(columnName);
        if(columnName.toLowerCase().equalsIgnoreCase("en_car_no")) {
            value = encode(value);
        }
        return value;
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        String columnName =  meta.getColumnName(columnIndex);

        /*System.out.print("\n====================\n");
        System.out.print("second resultset type => " +meta.getColumnName(columnIndex));
        System.out.print("\n====================\n");*/

        String value = rs.getString(columnIndex);
        if(columnName.toLowerCase().equalsIgnoreCase("en_car_no")) {
            value = encode(value);
        }

        return value;
    }


    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);

        /*System.out.print("\n====================\n");
        System.out.print("nothting");
        System.out.print("\n====================\n");*/
        // 암호화 여부 확인
        /*if(isCipher()){
            value = decode(value);
        }*/
        return value;
    }

    @Override
    public boolean isCipher(String value) {
        return isEncode(value);
    }
}
