package com.liu;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Liush
 * @description
 * @date 2021/7/19 14:55
 **/
public class BitMapContains {

    public static HashMap<String,BitMap> contains=new HashMap<>();


    public static void put(String channelName,BitMap bitMap){
        contains.put(channelName,bitMap);

    }

    public static BitMap getBitMap(String name){
        return contains.get(name);
    }

    public static boolean contain(String name,long i) throws IOException, ClassNotFoundException {
        return contains.get(name).contains(i);
    }


}
