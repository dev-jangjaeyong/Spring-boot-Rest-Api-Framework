package com.atonm.kblease.api;


import com.atonm.kblease.api.config.CustomLocalDateTimeSerializer;
import com.atonm.kblease.api.config.security.custom.SessionUser;
import com.atonm.kblease.api.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.InputSource;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

public class JavaMain {
	/*public static void main(String[] args) throws UnsupportedEncodingException {
		try {


			 * SOAP XML문 구성

			String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> " +
					"<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
					"xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"> " +
					" <soap12:Body> " +
					" <GetCarImageDataArray xmlns=\"http://tempuri.org/\"> " +
					" <sCompCode>4bd8c16f487b6799171ec5fcfdd6ee94234cf3bb938ff4985100d9d92f7cfa51</sCompCode> " +
					" <sPlateNumber>82가1234</sPlateNumber> " +
					" </GetCarImageDataArray>" +
					" </soap12:Body> " +
					"</soap12:Envelope>                                                                          ";

			URL url = new URL("http://ws.dicar.co.kr/WService/WsCarService.asmx");

			System.out.println("Request Xml : \n" + xml);
			System.out.println("\n\n\n");

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			// Header 영역에 쓰기
			conn.addRequestProperty("Content-Type", "application/soap+xml;charset=utf-8");
			conn.addRequestProperty("SOAPAction","http://tempuri.org/GetCarImageDataArray");
			conn.addRequestProperty("Host","ws.dicar.co.kr");
			// BODY 영역에 쓰기
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(xml);
			wr.flush();

			// 리턴된 결과 읽기
			String inputLine = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			System.out.println("Response Xml : ");
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
			in.close();
			wr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	@Autowired
	ObjectMapper objectMapper;

	public static void main(String[] args) throws GeneralSecurityException, IOException {
		//System.out.println(10000000 + Math.round((10000000 / 1.1) * 0.07));
		/*System.out.println(KBLeaseCalUtil.acquisitionCost(21145042));
		System.out.println(KBLeaseCalUtil.carCost( 73922727));
		System.out.println(KBLeaseCalUtil.residualRatio(35445000, 69500000));*/
		/*
		// 중계커미션
		System.out.println(KBLeaseCalUtil.broadcastFee(KBLeaseCalUtil.acquisitionCost((10000000)), 1, 1, 2));

		// 초기선수금
		System.out.println(KBLeaseCalUtil.advance(10000000, 30));

		// 잔존가치
		System.out.println(KBLeaseCalUtil.residual(10000000, 59));

		// 월이율
		System.out.println(KBLeaseCalUtil.monthRatio(7.4));

		// 1 + 월이율
		System.out.println(KBLeaseCalUtil.ontPlusMonthRatio(7.4));*/
		/*System.out.println(KBLeaseCalUtil.calAmount(KBLeaseCalUtil.carCost(11700000)));	// 리스계산기 차량금액
		System.out.println(KBLeaseCalUtil.acquisitionCost(10000000));	// 취득원가
		System.out.println(KBLeaseCalUtil.residualRatio(Integer.parseInt("4070000"), Integer.parseInt("10000000"))); // 잔존율
		System.out.println(KBLeaseCalUtil.carCost(11700000));	// 차량광고가*/


		// 월납부액 분자 계산
		/*System.out.println(KBLeaseCalUtil.monthlyPaymentNumerator(69500000, 1,1,2,30.0,33.0,7.5,48));
		System.out.println(KBLeaseCalUtil.monthlyPaymentDenominator(7.5, 60));
		System.out.println(KBLeaseCalUtil.monthlyEstimatePayment(69500000, 1,1,2,30.0,33.0,7.5,48));*/
		/*System.out.println(StringUtil.removeHtmlTagStr("<p>고전압 배터리시스템 어셀블리(BSA) 교환 (*단, 20.10월 1차 리콜 시 BSA 교환 또는 개선 배터리셀이 장착된 차량의 경우 고전압 배터리 관리 시스템 진단 로직 업데이트 진행)</p><p><br></p><p>&lt;*해당 리콜 건은 부품 수급 일정에 따라 순차적으로 진행될 예정이므로, 현재 조치 가능 여부에 대해서는 제작사 고객센터로 문의주시기 바랍니다.&gt;</p>"));
		HashMap<String, String> a = new HashMap<>();
		a.put("csti", "50");
		System.out.println(AlimTokenUtil.createToken(a));
		System.out.println(AlimTokenUtil.getTokenInfo("eyJhbGciOiJIUzI1NiJ9__eyJzdWIiOiJ7XCJjc3RpXCI6XCIzOVwifSIsImV4cCI6MTYxOTY2ODA0Mn0__d9qpumLPgwzcR475Sasa4nw25wgCvitIIo3c6KOvLLE"));*/
		/*try{
    		SimpleDateFormat sdfOrg = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    		SimpleDateFormat sdfConv = new SimpleDateFormat("YYYY년 MM월 dd일 HH시 mm분");

    		Date orgDate = sdfOrg.parse("20210409 19:52:21");
    		System.out.println(sdfConv.format(orgDate));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}*/

		/*String key = "63EC82B7267BDA34722A92B63C5305AE"; //(32/16자리)
		String iv = "722A92B63C5305AE";//(16자리)
		//AES256Util aes = new AES256Util(key, key);// error :  Wrong IV length: must be 16 bytes long
		AES256Util aes = new AES256Util(key, iv);
		System.out.println(aes.encrypt("123456"));*/

		System.out.println(StringEscapeUtils.unescapeHtml3(StringUtil.removeAll("&lt;ROOT&gt;&lt;INSEQ&gt;&lt;/INSEQ&gt;<br>&lt;CARNO&gt;42루6000&lt;/CARNO&gt;<br>&lt;SHOPNO&gt;91&lt;/SHOPNO&gt;<br>&lt;ENTNUM&gt;02-4125-000102&lt;/ENTNUM&gt;<br>&lt;CNTC_RESULT_CODE&gt;MSG50000&lt;/CNTC_RESULT_CODE&gt;<br>&lt;CNTC_RESULT_DTLS&gt;연계 호출을 성공적으로 처리하였습니다.&lt;/CNTC_RESULT_DTLS&gt;<br>&lt;VHRNO&gt;&lt;/VHRNO&gt;<br>&lt;VIN&gt;WAUZZZ4G2EN004602&lt;/VIN&gt;<br>&lt;CNM&gt;A6 3.0 TDI quattro&lt;/CNM&gt;<br>&lt;YBL_MD&gt;&lt;/YBL_MD&gt;<br>&lt;/ROOT&gt;", "<br>")));

		// Result XML contents parsing by JDOM Parser
		SAXBuilder builder = new SAXBuilder();

		try {
			InputSource __t_is = new InputSource();
			System.out.println(StringEscapeUtils.unescapeHtml3(StringUtil.removeAll("&lt;ROOT&gt;&lt;INSEQ&gt;&lt;/INSEQ&gt;<br>&lt;CARNO&gt;42루6000&lt;/CARNO&gt;<br>&lt;SHOPNO&gt;91&lt;/SHOPNO&gt;<br>&lt;ENTNUM&gt;02-4125-000102&lt;/ENTNUM&gt;<br>&lt;CNTC_RESULT_CODE&gt;MSG50000&lt;/CNTC_RESULT_CODE&gt;<br>&lt;CNTC_RESULT_DTLS&gt;연계 호출을 성공적으로 처리하였습니다.&lt;/CNTC_RESULT_DTLS&gt;<br>&lt;VHRNO&gt;&lt;/VHRNO&gt;<br>&lt;VIN&gt;WAUZZZ4G2EN004602&lt;/VIN&gt;<br>&lt;CNM&gt;A6 3.0 TDI quattro&lt;/CNM&gt;<br>&lt;YBL_MD&gt;&lt;/YBL_MD&gt;<br>&lt;/ROOT&gt;", "<br>")));


			__t_is.setCharacterStream(new StringReader(StringEscapeUtils.unescapeHtml3(StringUtil.removeAll("&lt;ROOT&gt;&lt;INSEQ&gt;&lt;/INSEQ&gt;<br>&lt;CARNO&gt;42루6000&lt;/CARNO&gt;<br>&lt;SHOPNO&gt;91&lt;/SHOPNO&gt;<br>&lt;ENTNUM&gt;02-4125-000102&lt;/ENTNUM&gt;<br>&lt;CNTC_RESULT_CODE&gt;MSG50000&lt;/CNTC_RESULT_CODE&gt;<br>&lt;CNTC_RESULT_DTLS&gt;연계 호출을 성공적으로 처리하였습니다.&lt;/CNTC_RESULT_DTLS&gt;<br>&lt;VHRNO&gt;&lt;/VHRNO&gt;<br>&lt;VIN&gt;WAUZZZ4G2EN004602&lt;/VIN&gt;<br>&lt;CNM&gt;A6 3.0 TDI quattro&lt;/CNM&gt;<br>&lt;YBL_MD&gt;&lt;/YBL_MD&gt;<br>&lt;/ROOT&gt;", "<br>"))));
			Document document = null;
			document = (Document) builder.build(__t_is);
			Element rootElement = document.getRootElement();
			if(rootElement.getChild("CNTC_RESULT_CODE").getValue().equalsIgnoreCase("MSG50000")) {
				System.out.println(rootElement.getChild("VIN").getValue());
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
