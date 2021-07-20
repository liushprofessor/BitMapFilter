package com.liu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author Liush
 * @description
 * @date 2021/7/19 16:25
 **/
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        File file=new File("D:\\file\\channel2");
        file.mkdirs();
        FileOutputStream fileOutputStream=new FileOutputStream(new File("D:\\file\\channel2\\1"));
        System.out.println(fileOutputStream);
    	}
}
