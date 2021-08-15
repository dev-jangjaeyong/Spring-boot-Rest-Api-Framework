package com.atonm.core.common.constant;

/**
 * @author jang jea young
 * @since 2018-08-16
 */
public class Constant {
    public static final String UTF_8 = "UTF-8";
    public static final String ACCESS_TOKEN_COOKIE_NAME = "_authorization";
    public static final String ACCESS_TOKEN_HEADER_NAME = "Authorization";
    public static final String REFRESH_TOKEN_UID_HEADER_NAME = "";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "_refreshAuthorization";
    public static final String REFRESH_TOKEN_HEADER_NAME = "RefreshAuthorization";
    public static final String EMPTY_STRING = "";
    public static final String QUESTION_MARK = "?";
    public static final String SLASH_MARK = "/";
    public static final String AMPERSAND_MARK = "&";
    public static final String ASCII_EMPTY_SPACE = "%20";
    public static final String ERROR_LOGIN_INACTIVE_ACCOUNT = "휴면계정입니다.";
    public static final String ERROR_LOGIN_MAX_PASSWORD_WRONG_COUNT = "5";
    public static final String ERROR_LOGIN_MAX_PASSWORD_WRONG_MESSAGE = "비밀번호 @회 오류입니다.";
    public static final String ERROR_PUSH_SAVE_USERID = "유저정보 저장에 실패했습니다.";

    public static final String SEND_DEALER_CERT_NUMBER_MSG = "[카매니저] 본인확인을 위해 인증번호 [ SECCODE ]를 입력해주세요.";
    public static final String SEND_DEALER_CERT_NUM_CHK_51 = "인증 코드 값이 다릅니다";
    public static final String SEND_DEALER_CERT_NUM_CHK_52 = "인증값이 달라 발부된 코드 값을 삭제 합니다. (10회 초과)";
    public static final String SEND_DEALER_CERT_NUM_CHK_61 = "인증코드가 발부 되지 않았습니다";
    public static final String SEND_DEALER_CERT_NUM_CHK_62 = "아이디가 등록된 회원입니다";
    public static final String PID = "AAA_BBB_CCC";
    public static final String PC_WEB_CHANNEL = "PWEB";
    public static final String MO_APP_CHANNEL = "MAPP";
    public static final String MO_WEB_CHANNEL = "MAPP";


    /*******************************************************************************************************************
     * 비밀번호 변경에 대한 메시지 정의
     ******************************************************************************************************************/
    public static   final   String  PASSWD_MODIFY_USER_INFO_NOT_EXISTS_MSG      =   "회원정보가 존재하지 않습니다.";
    public static   final   String  PASSWD_MODIFY_PHONE_NO_NOT_EXISTS_MSG       =   "회원의 휴대폰정보가 존재하지 않습니다.";
    public static   final   String  PASSWD_MODIFY_DAY_MODIFY_CNT_EXCESS_MSG     =   "1일 비밀번호 변경 가능횟수가 초괴되었습니다.";

    public static final String GEN_CAR_CODE_FILE_NM = "aaa.js";
    public static final String API_ROLE_PERMIT_ALL = "PERMIT_ALL";


    /*******************************************************************************************************************
     * 첨부파일 upload 폴더 지정
     ******************************************************************************************************************/
    public static   final   String  AAA = "/psc";
    public static   final   String  BBB = "/pcd";
    public static   final   String  CCC = "/cm";
    public static   final   String  DDD = "/pam";
    public static   final   String  EEE = "/dam";

    public enum SAMPLE {
        PASS_CAR        (AAA)           , 
        PASS_DEALER     (BBB)
        ;

        private String  value;

        IMAGE_UPLOAD_PATH(String value) {
            this.value  =   value;
        }

        public String getValue() {
            return this.value;
        }

    }   //  END enum


    /*******************************************************************************************************************
     * 이미지파일 외부공개여부
     *  - IMG_OPEN_TYPE_OUTER           :   이미지를 외부에 공개해도 무방한 이미지 타입 (차량 이미지, 리뷰 이미지 등)
     *  - IMG_OPEN_TYPE_INNER           :   이미지를 외부에 공개해서는 안되는 이미지 (종사원증 이미지)
     ******************************************************************************************************************/
    public  static  final   String  IMG_OPEN_TYPE_OUTER =   "OUTER";
    public  static  final   String  IMG_OPEN_TYPE_INNER =   "INNER";


    /*******************************************************************************************************************
     * Redis Key 값 정의calc_option
     *
     * 2021-03-25     added   by    Jeong Injin
     ******************************************************************************************************************/
    public static final String USP_RESIDUAL_GROUPBY = "residual_group"; //KB TA_RESIDUAL_PER_CODE ORG_ID로 사용중인 코드를 group by 하여 가져옵니다.
    public static final String ADMIN_API_ROLE = "api_role";
    public static final String ADMIN_MENU_ROLE = "menu_role";

}   // END class
