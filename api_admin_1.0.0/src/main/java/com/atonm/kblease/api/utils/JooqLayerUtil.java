package com.atonm.kblease.api.utils;

import com.atonm.kblease.api.common.base.AuthorDTO;
import com.atonm.kblease.api.common.base.MenuDTO;
import com.atonm.kblease.api.common.base.OrgnztUnitDTO;
import com.atonm.kblease.api.common.base.ProductDTO;
import com.atonm.kblease.api.common.enumerate.YN;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class JooqLayerUtil {
    public static List<OrgnztUnitDTO> orgztUnit_layering(List<OrgnztUnitDTO> t) {
        t.forEach(c -> {
            if (CheckUtils.nonNull(c.getUpOrgnztUnitNo())) { // 상위메뉴 번호가 존재할 경우
                t.forEach(p -> {
                    if (p.getOrgnztUnitNo().equals(c.getUpOrgnztUnitNo())) {
                        p.addChild(c);
                    }
                });
            }
        });

        return t.stream().filter(OrgnztUnitDTO::hasChildren).collect(Collectors.toList());
    }

    /**
     * 메뉴쪽은 동일 DTO이므로 구분을 위해 st 추가.
     * @param st single : 단일 구조 데이터 , tree : 트리 구조 데이터.
     */
    public static List<Object> menu_layering(List<Object> t, String st) {
        if(Object.class.isInstance(MenuDTO.class) && st.equals("tree")) {
            //t = (List<Object>) t;
            //ArrayList<MenuDTO> tt = new ArrayList<MenuDTO>();
            List<MenuDTO> menu = ModelMapperUtils.mapList(t, MenuDTO.class);

            AtomicReference<Long> minLv = menu.size() > 0 ? new AtomicReference<>(menu.get(0).getMenuLv()) : new AtomicReference<>(0L);

            menu.forEach(c -> {
                if (minLv.get() > c.getMenuLv()) minLv.set(c.getMenuLv());

                if (CheckUtils.nonNull(c.getUpMenuNo())) { // 상위메뉴 번호가 존재할 경우
                    menu.forEach(p -> {
                        if (p.getMenuNo().equals(c.getMenuNo()) && !p.getMenuLv().equals(c.getMenuLv())) {
                            if (p.getMenuLv() <= c.getMenuLv()) {
                                p.setUseYn(YN.N.name());
                            } else {
                                c.setUseYn(YN.N.name());
                            }
                        }

                        if (p.getMenuNo().equals(c.getUpMenuNo())) {
                            if (YN.Y.name().equals(c.getUseYn())) {
                                p.addChild(c);
                            }
                        }
                    });
                }
            });

            return menu.stream().filter(m -> m.getMenuLv() == minLv.get() && YN.Y.name().equals(m.getUseYn())).collect(Collectors.toList());

        }else if(Object.class.isInstance(MenuDTO.class) && st.equals("single")){

            List<MenuDTO> menu = ModelMapperUtils.mapList(t, MenuDTO.class);

            AtomicReference<Long> minLv = menu.size() > 0 ? new AtomicReference<>(menu.get(0).getMenuLv()) : new AtomicReference<>(0L);

            menu.forEach(c -> {

                if (CheckUtils.nonNull(c.getUpMenuNo())) { // 상위메뉴 번호가 존재할 경우
                    menu.forEach(p -> {
                        if (p.getMenuNo().equals(c.getUpMenuNo())) {
                            p.addChild(c);
                        }
                    });
                }
            });

            return menu.stream().filter(m -> m.getMenuLv() == minLv.get()).collect(Collectors.toList());
        }

        return null;
    }

    public static List<AuthorDTO> author_layering(List<AuthorDTO> t) {
        List<AuthorDTO> copyList = new ArrayList<>(t);

        copyList.forEach(element -> {
            t
                    .stream()
                    .filter(p -> CheckUtils.nonNull(p.getAuthorNo()) && CheckUtils.nonNull(element.getUpAuthorNo()) && CheckUtils.isEqual(element.getUpAuthorNo(), p.getAuthorNo()))
                    .findAny()
                    .ifPresent(p -> {
                        if (CheckUtils.isNull(p.getChildren())) {
                            p.setChildren(new ArrayList<>());
                        }

                        p.getChildren().add(element);
                    });
        });

        return  t.stream().filter(p -> CheckUtils.isNull(p.getUpAuthorNo())).collect(Collectors.toList());
    }

    public static List<ProductDTO> product_layering(List<ProductDTO> t) {
        List<ProductDTO> copyList = new ArrayList<>(t);

        copyList.forEach(element -> {
            t
                    .stream()
                    .filter(p -> CheckUtils.nonNull(p.getPid()) && CheckUtils.nonNull(element.getUpPid()) && CheckUtils.isEqual(element.getUpPid(), p.getPid()))
                    .findAny()
                    .ifPresent(p -> {
                        if (p.getChildren() == null) {
                            p.setChildren(new ArrayList<>());
                        }

                        p.getChildren().add(element);
                    });
        });

        return t.stream().filter(p -> p.getUpPid() == null).collect(Collectors.toList());
    }
}
