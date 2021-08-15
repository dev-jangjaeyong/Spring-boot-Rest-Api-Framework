package com.atonm.kblease.api.service;

import com.atonm.core.api.ApiResponse;
import com.atonm.kblease.api.common.base.BaseService;
import com.atonm.kblease.api.mapper.CodeCommonMapper;
import com.atonm.kblease.api.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class CommonCodeService extends BaseService {
    private final CodeCommonMapper codeCommonMapper;

    @Value("${file.code-dir}")
    private String codeFileDirectory;

    public ApiResponse getBaseCodeSido() {
        return ok(codeCommonMapper.getBaseCodeSido());
    }

    public ApiResponse getGenBaseCodeSido() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try{
            File codeFile = new File(codeFileDirectory + "common-sido.js");
            if(codeFile.exists()) codeFile.delete();

            String _json = gson.toJson(codeCommonMapper.getBaseCodeSido());
            if(StringUtil.isEmpty(_json)) return ok("조회할 데이터가 없습니다.");

            FileOutputStream fileOutputStream = new FileOutputStream(codeFile, true);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            BufferedWriter out = new BufferedWriter(writer);

            out.write("app.code.sido" + " = " + _json + ";");
            out.newLine();
            out.flush();
            out.close();

            return ok("생성성공");
        }catch (IOException e) {
            e.printStackTrace();
            return ok("생성실패");
        }
    }
}
