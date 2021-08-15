package com.atonm.kblease.api.config.typeHandler;

import com.atonm.kblease.api.common.entity.*;
import com.atonm.kblease.api.utils.ObfuscationUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Iterator;

@Configuration
@Component
public class OncarEntityInterceptor extends EmptyInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(OncarEntityInterceptor.class);

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object [] previousState, String[] propertyNames, Type[] types) {
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        System.out.println();
    }

    @Override
    public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        return null;
    }

    @Override
    public String onPrepareStatement(String sql) {
        return sql;
    }

    @Override
    public void preFlush(Iterator entities) {
        while(entities.hasNext()){
            Object entity = entities.next();
            if (entity instanceof Oncar || entity instanceof OncarOption || entity instanceof OncarImage
                    || entity instanceof OncarHotMark || entity instanceof WishCar || entity instanceof Share) {
                Oncar oncar = ((Oncar) entity);

                try{
                    if(Long.valueOf(oncar.getCarNo()).getClass().getName().toLowerCase().equalsIgnoreCase("java.lang.long")) {
                        oncar.setCarNo(oncar.getCarNo());
                    } else {
                        oncar.setCarNo(ObfuscationUtils.decode(oncar.getCarNo()));
                    }
                }catch(Exception e) {
                    oncar.setCarNo(ObfuscationUtils.decode(oncar.getCarNo()));
                }
            }
        }
    }
}