package com.liu;



/**
 * @author Liush
 * @description 断言
 * @date 2020/12/1 16:30
 **/
public class CAsserts {



    public static void illegalState(boolean expression, String message){

        if(expression){
            throw new IllegalStateException(message);
        }

    }



}
