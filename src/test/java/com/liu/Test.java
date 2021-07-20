package com.liu;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Liush
 * @description
 * @date 2021/7/19 15:33
 **/

public class Test {

    public static void main(String[] args) {

       createChannel();
       //putData();

    }

    private static void putData() {
        for(int i=0;i<10000000;i++){
            Map<String,Object> params=new HashMap<>();
            params.put("name","channel1");
            if(i%100==0){

            }else {
                params.put("i",i);
                HttpUtil.post("http://127.0.0.1:8080/put",params);
            }

        }
    }


    private static void createChannel() {
        for(int i=0;i<800;i++){
            Map<String,Object> params=new HashMap<>();
            params.put("channelName","channel"+i);
            HttpUtil.post("http://127.0.0.1:8080/createBitMap",params);

        }
    }


}
