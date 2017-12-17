package com.verysu.basic.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Map工具类
 *
 * @author Cocodroid
 * @create 2017-11-25 2:27
 */
public class MapUtil<K, V> {

    private MapUtil(){}

    public static MapUtil getInstance(){
        return MapUtilHolder.instance;
    }

    private static class MapUtilHolder{
        private static MapUtil instance=new MapUtil();
    }

    /**
     * 统计List里包含相同Key的对应个数
     * @param list
     * @return
     */
    public Map<K, Long> keyCounts(List<K> list){
        Map<K , Long> map = Maps.newHashMap();
        for (K str : list){
            Long count = 1L;
            K str2;
            if((str2 = returnKeySameByCaseInsensitive(map.keySet(), str)) != null){
                count = (Long)map.get(str2) + 1;
                str = str2;
            }
            map.put(str, count);
//            System.out.println(map);
        }
        return map;
    }

    /**
     * Map判断是否包含某Key（字符串）不区分大小写
     * @param map
     * @param key
     * @return
     */
    public static boolean keySameByCaseInsensitive(Map<Object,Object> map, Object key){
        Set keySet = map.keySet();
        for (Object objKey : keySet){
            if (key instanceof String){
                String objKeyStr = ((String)objKey).toLowerCase();
                String keyStr = ((String)key).toLowerCase();
                return objKeyStr.equals(keyStr);
            }else {
                return objKey == key;
            }
        }
        return false;
    }

    /**
     * Map判断是否包含某Key（字符串）不区分大小写
     * @param keySet
     * @param key
     * @return 包含相同key就返回map对应的key，没有则返回null
     */
    public K returnKeySameByCaseInsensitive(Set keySet, Object key){
//        Set keySet = map.keySet();
        for (Object objKey : keySet){
            if (key instanceof String){
                String objKeyStr = ((String)objKey).toLowerCase();
                String keyStr = ((String)key).toLowerCase();
                if (objKeyStr.equals(keyStr)){
                    return (K)objKey;
                }
            }else {
                return objKey == key ? (K)objKey : null;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<Object, Object> map = Maps.newHashMap();
        map.put("Aa", 12L);
        System.out.println(keySameByCaseInsensitive(map, "aa"));
        System.out.println(keySameByCaseInsensitive(map, "av"));
        System.out.println(keySameByCaseInsensitive(map, "AA"));
        System.out.println(keySameByCaseInsensitive(map, ""));

        List list = Lists.newArrayList("AA", "aa", "bB", "Aa", "AA", "Bb", "aA", "BB");
        System.out.println(MapUtil.getInstance().keyCounts(list));
    }
}
