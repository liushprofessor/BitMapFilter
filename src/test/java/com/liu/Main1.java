package com.liu;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Liush
 * @description
 * @date 2021/7/20 11:11
 **/

public class Main1 {

    public static void main(String[] args) {

        putData();

    	}


    private static void putData() {


        for(int i=300000;i<350000;i++){
            Map<String,Object> params=new HashMap<>();
            params.put("name","channel1");
            params.put("i",i);
            HttpUtil.post("http://127.0.0.1:8080/put",params);


        }


        for(int i=0;i<100000;i++){
            Map<String,Object> params=new HashMap<>();
            params.put("name","channel1");
            params.put("i",i);
            HttpUtil.post("http://127.0.0.1:8080/put",params);


        }

        for(int i=200000;i<300000;i++){
            Map<String,Object> params=new HashMap<>();
            params.put("name","channel1");
            params.put("i",i);
            HttpUtil.post("http://127.0.0.1:8080/put",params);


        }


    }
}
