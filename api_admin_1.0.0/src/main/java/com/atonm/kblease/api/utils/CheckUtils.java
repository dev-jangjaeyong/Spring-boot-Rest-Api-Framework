package com.atonm.kblease.api.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author jang jea young
 * @since 2018-09-06
 */
public final class CheckUtils {
    public static boolean has(String source) {
        return StringUtils.isNoneBlank(source);
    }

    public static boolean has(String first, String second) {
        return StringUtils.isNoneBlank(first) && StringUtils.isNoneBlank(second);
    }

    public static <T> boolean nonNull(T source) {
        return Objects.nonNull(source);
    }

    public static <T, S> boolean nonNull(T first, S second) {

        return Objects.nonNull(first) && Objects.nonNull(second);
    }

    public static <T> boolean isNull(T target) {
        return Objects.isNull(target);
    }

    public static <T> boolean isNull(T first, T second) {
        return Objects.isNull(first) && Objects.isNull(second);
    }

    public static <T, S> boolean bothNull(T first, S second) {
        return Objects.isNull(first) && Objects.isNull(second);
    }

    public static <T, S> boolean hasNull(T first, S second) {
        return Objects.nonNull(first) || Objects.nonNull(second);
    }

    public static <T> boolean isEmpty(List<T> target) {
        return target.isEmpty();
    }

    public static <T> boolean isNotEmpty(List<T> target) {
        return !target.isEmpty();
    }

    public static <T, S> boolean isEqual(T first, S second) {
        return Objects.equals(first, second);
    }
}
