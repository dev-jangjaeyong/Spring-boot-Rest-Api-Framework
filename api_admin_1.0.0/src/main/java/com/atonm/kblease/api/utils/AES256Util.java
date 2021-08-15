package com.atonm.kblease.api.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * 양방향 암호화 알고리즘인 AES256 암호화를 지원하는 클래스
 */
public class AES256Util {

    /**
     * 16자리의 키값을 입력하여 객체를 생성한다.
     *
     * @param key
     *            암/복호화를 위한 키값
     * @throws UnsupportedEncodingException
     *             키값의 길이가 16이하일 경우 발생
     */
    private String iv;
    private String key;
    private Key keySpec;

    public AES256Util(String key) throws UnsupportedEncodingException {
        if(StringUtil.isEmpty(key)) key = "f4150d4a1ac5708c86afc43868fea6ab";
        this.key = key;
        this.iv = key.substring(0,16);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        this.keySpec = keySpec;
    }

    public AES256Util(String key, String iv) throws UnsupportedEncodingException {
        this.key = key;
        this.iv = iv;
        byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		this.keySpec = keySpec;
    }


    /**
     * AES256 으로 암호화 한다.
     *
     * @param str
     *            암호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String encrypt(String str) throws NoSuchAlgorithmException,
            GeneralSecurityException, UnsupportedEncodingException {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(Base64.encodeBase64(encrypted));
		return enStr;
    }


    /**
     * AES256으로 암호화된 txt 를 복호화한다.
     *
     * @param str
     *            복호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String decrypt(String str) throws NoSuchAlgorithmException,
            GeneralSecurityException, UnsupportedEncodingException {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
    }


    public static void main(String[] args) throws Exception{
        /*String text = "{\n" +
            "   \"telCd\" : \"1\",\n" +
            "   \"mdn\" : \"01045294737\",\n" +
            "   \"chnCd\" : \"01\",\n" +
            "   \"pushType\" : \"01\",\n" +
            "   \"pushTitle\" : \"푸시발송 테스트 TITLE\",\n" +
            "   \"pushContents\" : \"푸시발송 테스트 CONTENTS\"\n" +
            "}";*/
        String text = "wodud123";
        String key = "63EC82B7267BDA34722A92B63C5305AE"; //(32/16자리)
        String iv = "722A92B63C5305AE";//(16자리)
        //AES256Util aes = new AES256Util(key, key);// error :  Wrong IV length: must be 16 bytes long
        AES256Util aes = new AES256Util(key, iv);
        String enc = aes.encrypt(text);
        String des = aes.decrypt(enc);
        System.out.println("enc [" + enc +"]");
        System.out.println("des [" + des +"]");
    }

}
