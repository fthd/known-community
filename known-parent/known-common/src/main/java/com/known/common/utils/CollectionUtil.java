package com.known.common.utils;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtil {
    /**
     * 刪除集合中的null值
     *
     * @param collection
     * @return
     */
    public static Collection<?> removeNullValue(Collection<?> collection) {
        if (collection == null) {
            return null;
        }
        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (null == o || "".equals(o + "")) {
                iterator.remove();
            }
        }
        return collection;
    }
}
