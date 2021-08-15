package com.atonm.kblease.api.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class CryptCarmanagerPcUtil {
	final static String passphrase = "Password@123";  //consant string Pass key
	/**
	 * 생성자, 외부에서 객체를 인스턴스화 할 수 없도록 설정
	 */
	private CryptCarmanagerPcUtil() {
	}

	/**
	 *
	 * <pre>
	 * 1. 설명 : AES 암호화(128)
	 * 2. Input : message, key, initialization vector
	 * 3. Output : AES 128 Encrypted result(String)
	 * 4. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 		작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2017. 11. 22.    Jy-jang                        최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param message
	 * @param key
	 * @param iv
	 * @return Encrypted message
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeySpecException
	 */
    /*public static String encryptAES128(String message, String key, String iv) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
    	try {
            PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new RijndaelEngine(128)), new PKCS7Padding());
            CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(hexToByteArray(key)), hexToByteArray(iv));
            cipher.init(true, ivAndKey);
            return new String(Base64.encodeBase64String(cipherData(cipher, message.getBytes())));
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }*/

    /**
     *
     * <pre>
     * 1. 설명 : AES 복호화(128)
     * 2. Input : encrypted message, key, initialization vector
     * 3. Output : AES 128 Decrypted result(String)
     * 4. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 		작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2017. 11. 22.    Jy-jang                       최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param encrypted
     * @param key
     * @param iv
     * @return Decrypted message
     */
    public static String decryptAES128(String encrypted, String key, String iv) {
        try {
            byte[] ciphertext = Base64.decodeBase64(encrypted);
            PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new RijndaelEngine(128)), new PKCS7Padding());
            CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(hexToByteArray(key)), hexToByteArray(iv));
            cipher.init(false, ivAndKey);
            return new String(cipherData(cipher, ciphertext));
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }


    private static byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data) throws InvalidCipherTextException
    {
        int minSize = cipher.getOutputSize(data.length);
        byte[] outBuf = new byte[minSize];
        int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
        int length2 = cipher.doFinal(outBuf, length1);
        int actualLength = length1 + length2;
        byte[] cipherArray = new byte[actualLength];

        for (int x = 0; x < actualLength; x++) {
            cipherArray[x] = outBuf[x];
        }
        return cipherArray;
    }

    public static byte[] hexToByteArray(String hex) {
    	if (hex == null || hex.length() == 0) { return null; }
    	byte[] ba = new byte[hex.length() / 2];
    	for (int i = 0; i < ba.length; i++) { ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16); } return ba;
    }



    public static String md5DesEncrypt(String message) throws Exception {
		// String deskey = computeHash();

		String result = TripleDesBouncy.encrypt(message, passphrase);
		return result;
	}

	public static String md5DesDecrypt(String message) throws Exception {
		String result = TripleDesBouncy.decrypt(message, passphrase);
		return result;
	}

	public static String computeHash() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		byte[] digest = m.digest(passphrase.getBytes("UTF-8"));
		String hash = new BigInteger(1, digest).toString(16);
		return hash;
	}
}
