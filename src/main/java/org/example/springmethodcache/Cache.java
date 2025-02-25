package org.example.springmethodcache;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private final Map<Integer, List<Object[]>> hash = new ConcurrentHashMap<>();
    private final Map<Object[], Object> res = new ConcurrentHashMap<>();
    private final Map<String, Object> fast = new ConcurrentHashMap<>();
    private boolean fastCache;

    public Object get(Object[] key) {
        if (fastCache){
            return fastGet(key);
        }else {
            return safeGet(key);
        }
    }

    private Object safeGet(Object[] key) {
        int hashVal = Arrays.hashCode(key);
        if (hash.containsKey(hashVal)) {
            for (Object[] o : hash.get(hashVal)) {
                if (areArraysDeepEqual(o, key)) {
                    return res.get(o);
                }
            }
        }
        return null;
    }

    private Object fastGet(Object[] key) {
        String keyStr = Cache.getObjectsString(key);
        return fast.getOrDefault(keyStr, null);
    }

    public void put(Object[] key, Object value) {
        if (fastCache){
            fastPut(key, value);
        } else {
            safePut(key, value);
        }
    }

    private void safePut(Object[] key, Object value) {
        int hashVal = Arrays.hashCode(key);
        if (hash.containsKey(hashVal)) {
            hash.get(hashVal).add(key);
        } else {
            List<Object[]> tmp = new ArrayList<>();
            tmp.add(key);
            hash.put(hashVal, tmp);
        }
        res.put(key, value);
    }

    private void fastPut(Object[] key, Object value) {
        String keyStr = Cache.getObjectsString(key);
        fast.put(keyStr, value);
    }

    public static boolean areArraysDeepEqual(Object[] array1, Object[] array2) {
        if (array1 == array2) return true;
        if (array1 == null || array2 == null) return false;
        if (array1.length != array2.length) return false;

        for (int i = 0; i < array1.length; i++) {
            Object element1 = array1[i];
            Object element2 = array2[i];

            if (element1 instanceof Object[] && element2 instanceof Object[]) {
                if (!areArraysDeepEqual((Object[]) element1, (Object[]) element2)) {
                    return false;
                }
            } else if (element1 == null && element2 == null) {
                continue;
            } else if (element1 == null || element2 == null) {
                return false;
            } else if (!element1.equals(element2)) {
                return false;
            }
        }
        return true;
    }

    public static String getObjectsString(Object[] array) {
        StringBuilder sb = new StringBuilder();
        for (Object element : array) {
            if (element != null) {
                if (element instanceof Object[]) {
                    sb.append("[");
                    sb.append(getObjectsString((Object[]) element));
                    sb.append("]");
                } else {
                    sb.append(element.toString());
                }
            }
        }
        return sb.toString();
    }

    public boolean isFastCache() {
        return fastCache;
    }

    public void setFastCache(boolean fastCache) {
        this.fastCache = fastCache;
    }
}
