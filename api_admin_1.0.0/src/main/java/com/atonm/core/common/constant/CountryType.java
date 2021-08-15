package com.atonm.core.common.constant;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jang jea young
 * @since 2018-10-16
 * <pre>
 *     차량의 제조국에 대한 정보.
 *     변경 가능성이 없어 클래스로 정의함.
 * </pre>
 */
public class CountryType {
    public static final String DOMESTIC = "DOMESTIC";
    public static final String FOREIGN = "FOREIGN";

    @Data
    @Builder
    private static class Country {
        private String name;
        private String code;
        private Integer number;
    }

    private final static Country SWEDEN  = Country.builder().name("SWEDEN").code("BE").number(1000).build();
    private final static Country FRANCE  = Country.builder().name("FRANCE").code("BF").number(1001).build();
    private final static Country JAPAN   = Country.builder().name("JAPAN").code("BC").number(1002).build();
    private final static Country GERMANY = Country.builder().name("GERMANY").code("BB").number(1003).build();
    private final static Country KOREA   = Country.builder().name("KOREA").code("AA").number(1004).build();
    private final static Country ITALY   = Country.builder().name("ITALY").code("BH").number(1005).build();
    private final static Country ENGLAND = Country.builder().name("ENGLAND").code("BG").number(1006).build();
    private final static Country USA     = Country.builder().name("USA").code("BD").number(1007).build();
    private final static Country CHINA   = Country.builder().name("CHINA").code("BI").number(1009).build();

    private final static List<Country> foreignCountries = Lists.newArrayList(SWEDEN, FRANCE, JAPAN, GERMANY, ITALY, ENGLAND, USA, CHINA);
    private final static List<Country> domesticCountries = Lists.newArrayList(KOREA);

    // 외산 국가 명
    public static List<String> getForeignCountryNames() {
        return foreignCountries.stream().map(Country::getName).collect(Collectors.toList());
    }

    // 외산 국가 코드
    public static List<String> getForeignCountryCodes() {
        return foreignCountries.stream().map(Country::getCode).collect(Collectors.toList());
    }

    // 외산 국가 번호
    public static List<Integer> getForeignCountryNumbers() {
        return foreignCountries.stream().map(Country::getNumber).collect(Collectors.toList());
    }

    // 국산 국가명
    public static List<String> getDomesticCountryNames() {
        return domesticCountries.stream().map(Country::getName).collect(Collectors.toList());
    }

    // 국산 국가 코드
    public static List<String> getDomesticCountryCodes() {
        return domesticCountries.stream().map(Country::getCode).collect(Collectors.toList());
    }

    // 국산 국가 번호
    public static List<Integer> getDomesticCountryNumbers() {
        return domesticCountries.stream().map(Country::getNumber).collect(Collectors.toList());
    }
}
