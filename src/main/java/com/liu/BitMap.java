package com.liu;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Objects;


/**
 * @author Liush
 * @description
 * @date 2021/7/16 16:54
 **/
public class BitMap {

    private long id;

    private int[] contain;

    private int size;


    private String name;
    
    private String filePath;



    public BitMap(String name, int size ,  String filePath){
        CAsserts.illegalState(size<0,"size必须大于0");
        CAsserts.illegalState(StringUtils.isEmpty(filePath),"历史存储路径为空");
        CAsserts.illegalState(StringUtils.isEmpty(name),"容器名不能为空");
        this.contain = new int[size];
        this.size=size;
        this.filePath=filePath;
        this.name=name;
        this.id=0;
    }


    private void add(long i) {
        int r = (int)(i / 32L);
        int c = (int)(i % 32L);
        //将1移动到对应的字节位上，并且和原来的值与操作，将放入值的位置1，表示放入了该位
        this.contain[r] |= 1 << c;
    }

    private void addHis(long i,int[] containOut) {
        int r = (int)(i / 32L);
        int c = (int)(i % 32L);
        //将1移动到对应的字节位上，并且和原来的值与操作，将放入值的位置1，表示放入了该位
        containOut[r] |= 1 << c;

    }

    public boolean contains(long i) throws IOException, ClassNotFoundException {
        long containId=i/(32*size);
        if(isNotHistoryDataId(containId)){
            return containsCurrent(i-(size*id*32));
        }else {
            File file = findHistoryStoreFile(containId);
            if(file.exists()){
                File historyContainFile=getHistoryContainFile(containId);
                if(canNotFindHistoryFile(historyContainFile)){
                    return false;
                }
                int[] historyContain=restoreHistoryContains(containId, historyContainFile);
                boolean result= containsHistory(i-(size*containId*32),historyContain);
                flushContainsToStore(historyContain,containId);
                return result;
            }
            return false;
        }
    }

    private boolean isNotHistoryDataId(long containId) {
        return containId==id;
    }


    private boolean canNotFindHistoryFile(File historyContainFile) {
        return Objects.isNull(historyContainFile);
    }

    private long getCurrentId() {
        return this.id;
    }

    private int[] getCurrentContain() {
        return this.contain;
    }


    private File findHistoryStoreFile(long containId) {
        return new File(filePath+ File.separator+name+File.separator+ containId);
    }

    private boolean containsCurrent(long i) {
        int r = (int)(i / 32L);
        int c = (int)(i % 32L);
        return (this.contain[r] >>> c & 1) == 1;
    }

    private boolean containsHistory(long i, int[] containOut) {
        int r = (int)(i / 32L);
        int c = (int)(i % 32L);
        return (containOut[r] >>> c & 1) == 1;
    }


    public synchronized void put(long i) throws IOException, ClassNotFoundException {
        long containId=i/(size*32);
        if(reachThreshold(containId)){
            flushContainsToStore(this.contain,id);
            id=containId;
            this.contain = new int[size];

        }
        if(isNotHistoryDataId(containId)){
            add(i-(size*containId*32));

        }else if(isHistoryData(containId)) {
            File historyContainFile=getHistoryContainFile(containId);
            int[] historyContain=null;
            if(canNotFindHistoryFile(historyContainFile)){
                historyContain=new int[size];
            }else {
                historyContain=restoreHistoryContains(containId, historyContainFile);
            }
            addHis(i-(size*containId*32),historyContain);
            flushContainsToStore(historyContain,containId);

        }



    }

    private boolean isHistoryData(long containId) {
        return containId<id;
    }



    private int[] restoreHistoryContains(long containId, File historyContainFile) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(historyContainFile));
        return  (int[]) inputStream.readObject();

    }

    private File getHistoryContainFile(long containId) {
        File file=new File(filePath+ File.separator+name);
        File[] historyContains=file.listFiles();
        if(Objects.isNull(historyContains)){
            return null;
        }
        for(File historyContain:historyContains){
                String fileName=historyContain.getName();
                if(fileName.equals(new String(containId+""))){
                    return historyContain;
                }
        }
        return null;
    }

    private void flushContainsToStore(int[] containOut,long id) throws IOException {
        FileOutputStream fileOutputStream=null;
        ObjectOutputStream out=null;
        try {
            File file=new File(filePath+ File.separator+name);
            file.mkdirs();
            fileOutputStream=new FileOutputStream(new File(filePath+ File.separator+name+File.separator+ id));
            out =new ObjectOutputStream(fileOutputStream);
            out.writeObject(containOut);
        }finally {
            out.close();
            fileOutputStream.close();
        }

    }



    /**
     * 达到阈值
     * @return
     */
    private boolean reachThreshold(long containId) {
        return containId>id;
    }

    public long getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
