package com.atonm.kblease.api.utils;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


public class FtpUtil {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private Session session = null;
    private Channel channel = null;
    private ChannelSftp channelSftp = null;

    // SFTP 서버연결
    public void init(String url, String user, String password, int port){
        // System.out.println(url);
        //JSch 객체 생성
        JSch jsch = new JSch();
        try {
            //세션객체 생성 ( user , host, port )
            session = jsch.getSession(user, url, port);

            //password 설정
            session.setPassword(password);

            //세션관련 설정정보 설정
            java.util.Properties config = new java.util.Properties();

            //호스트 정보 검사하지 않는다.
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            //접속
            session.connect();

            //sftp 채널 접속
            channel = session.openChannel("sftp");
            channel.connect();

        } catch (JSchException e) {
            e.printStackTrace();
        }
        channelSftp = (ChannelSftp) channel;

    }

    // 단일 파일 업로드
    public void upload( String dir , File file) throws SftpException {
        SftpATTRS attrs = null;

        try {
            attrs = channelSftp.stat(dir);
        } catch (Exception e) {
            System.out.println(dir+" not found");
        }
        if (attrs != null) {
            System.out.println("Directory exists IsDir="+attrs.isDir());
        } else {
            System.out.println("Creating dir "+dir);
            //channelSftp.mkdir(dir);
            mkdirDir(dir);
            //mkdirDir(dir);
        }

        FileInputStream in = null;

        try{ //파일을 가져와서 inputStream에 넣고 저장경로를 찾아 put
            in = new FileInputStream(file);
            channelSftp.cd(dir);
            channelSftp.put(in,file.getName());
            channelSftp.chmod(Integer.parseInt("777", 8), dir + "/" + file.getName());
        }catch(SftpException se){
            se.printStackTrace();
        }catch(FileNotFoundException fe) {
            fe.printStackTrace();
        }finally{
            try{
                in.close();
            } catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

    // 단일 파일 다운로드
    public InputStream download(String dir, String fileNm){
        InputStream in = null;
        String path = "...";
        try{ //경로탐색후 inputStream에 데이터를 넣음
            channelSftp.cd(path+dir);
            in = channelSftp.get(fileNm);

        }catch(SftpException se){
            se.printStackTrace();
        }

        return in;
    }

    // 파일서버와 세션 종료
    public void disconnect(){
        channelSftp.exit();
        channelSftp.disconnect();
        channelSftp.quit();
        session.disconnect();
    }


    public void mkdirDir(String path) throws SftpException {
        channelSftp.cd("/");
        String[] folders = path.split( "/" );
        for ( String folder : folders ) {
            // System.out.println(folder);
            if (folder.length() > 0) {
                try {
                    //System.out.println(folder + " 이동시도");
                    channelSftp.cd( folder );
                }
                catch (SftpException e) {
                    //System.out.println(folder + " 생성시도");
                    channelSftp.mkdir( folder );
                    channelSftp.cd( folder );
                    channelSftp.chmod(Integer.parseInt("777", 8), channelSftp.pwd());
                }
            }
        }
        //return currentDirectory+ "/" + totPathArray;
    }


    /**
     * FTP 서버 해당경로의 파일목록과 파일최종수정일자 return
     * @param path
     * @return
     * @throws Exception
     */
    public List<HashMap<String, String>>   getRemoteFileList ( String jsUrlPath, String path )  throws Exception {
        List<ChannelSftp.LsEntry> files =   null;

        List<HashMap<String, String>>   responseList    =   new ArrayList<HashMap<String, String>>();

        try{
            files   =   (List<ChannelSftp.LsEntry>)channelSftp.ls(path);

            for (ChannelSftp.LsEntry le : files) {
                final String name = le.getFilename();
                if ( le.getAttrs().isDir()) {
                } else {
                    String lastModified   = le.getAttrs().getMtimeString();

                    SimpleDateFormat formatter_one = new SimpleDateFormat ( "EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH );
                    SimpleDateFormat formatter_two = new SimpleDateFormat ( "yyyyMMddHHmmss" );

                    ParsePosition pos = new ParsePosition ( 0 );
                    Date frmTime = formatter_one.parse ( lastModified, pos );

                    String  lastModifiedTime    =   formatter_two.format ( frmTime );

                    HashMap<String, String> responseMap =   new HashMap<String, String>();

                    if( name.lastIndexOf("pass-") > -1 ){

                        responseMap.put("jsFilePath",       jsUrlPath + name);
                        responseMap.put("lastModified",     lastModifiedTime);

                        responseList.add(responseMap);
                    } else if ( ( name.lastIndexOf("common-code.js") > -1 ) || ( name.lastIndexOf("common-sidocitydanji.js") > -1 ) ) {
                        responseMap.put("jsFilePath",       jsUrlPath + name);
                        responseMap.put("lastModified",     lastModifiedTime);

                        responseList.add(responseMap);
                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return responseList;
    }

}


