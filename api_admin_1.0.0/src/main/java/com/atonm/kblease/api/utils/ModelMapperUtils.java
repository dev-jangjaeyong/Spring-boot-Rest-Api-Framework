package com.atonm.kblease.api.utils;

import com.atonm.core.context.AppContextManager;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jang jea young
 * @since 2018-08-14
 */
public class ModelMapperUtils {

    public static <T> T map(Object source, Class<T> destinationClass) throws MappingException {
        if (source != null) {
            return getModelMapper().map(source, destinationClass);
        } else {
            return null;
        }
    }

    public static <T> List<T> mapList(Iterable<?> iterableSources, Class<T> destinationClass) {
        List<T> resultList = new ArrayList<>();

        List<?> listSources = (List<?>) iterableSources;

        if (!listSources.isEmpty()) {
            for (Object source : listSources) {
                resultList.add(map(source, destinationClass));
            }
        }

        return resultList;
    }

    private static ModelMapper getModelMapper() {
        return AppContextManager.getBean(ModelMapper.class);
    }
}
