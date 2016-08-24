package com.xing.app.newplayer.ToolKit;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wangxing on 16/8/2.
 */
public class SortHashMap {

    /**
     * key按照拼音排序
     * @param hashMap
     * @return
     */
    public static  Map<String, String> sortHashMap(HashMap<String, String> hashMap) {
        Map<String, String> resultMap = Collections.synchronizedMap(new LinkedHashMap<String, String>());
        TreeMap<String, String> sorted_map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String arg0, String arg1) {
                        String s1 = "";
                        String s2 = "";
                        try {
                            s1 = new String(arg0.getBytes("gbk"), "ISO-8859-1");
                            s2 = new String(arg1.getBytes("gbk"), "ISO-8859-1");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return s1.compareTo(s2);
                    }
                });
        sorted_map.putAll(hashMap);
        for (String key : sorted_map.keySet()) {
            resultMap.put(key, sorted_map.get(key));
        }
        return resultMap;
    }

}
