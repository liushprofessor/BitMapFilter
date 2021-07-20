package com.liu;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Liush
 * @description
 * @date 2021/7/19 14:52
 **/
@RestController
public class Controller {

    @RequestMapping("put")
    public String put(String name,long i){
        BitMap bitMap=BitMapContains.getBitMap(name);
        try {
            bitMap.put(i);
            System.out.println(i);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }


    @RequestMapping("createBitMap")
    public String createBitMap(String channelName){
        BitMap bitMap=new BitMap(channelName,2500,"D:\\file");
        BitMapContains.put(channelName,bitMap);
        return "success";
    }

    @RequestMapping("contain")
    public String contain(String channelName,long i) throws IOException, ClassNotFoundException {
        boolean result=false;
        try {
             result= BitMapContains.getBitMap(channelName).contains(i);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result+"";
    }






}
