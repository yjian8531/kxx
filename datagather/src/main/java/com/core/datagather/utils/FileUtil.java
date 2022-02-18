package com.core.datagather.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileUtil {


    /**
     * 获取文件夹下的文件
     * @param path
     * @return
     */
    public static List<File> getFiles(String path){
        List<File> files = new ArrayList<>();
        File superiors = new File(path);

        File[] fileArray = superiors.listFiles();
        if(fileArray == null){
            log.info("目录为空！");
            return null;
        }

        for(File file : fileArray){
            if(file.isFile()){
                files.add(file);
            }
        }
        return files;
    }

    /**
     * 获取文件内容
     * @param fileName
     * @return
     */
    public static String readFileByBytes(String fileName){
        File file = new File(fileName);
        InputStream in = null;
        StringBuffer sb = new StringBuffer();

        try{
            if (file.isFile() && file.exists()) { //判断文件是否存在
                // 一次读多个字节
                byte[] tempbytes = new byte[1024];
                int byteread = 0;
                in = new FileInputStream(file);
                //ReadFromFile.showAvailableBytes(in);
                // 读入多个字节到字节数组中，byteread为一次读入的字节数
                while ((byteread = in.read(tempbytes)) != -1) {
                    String str = new String(tempbytes, 0, byteread);
                    sb.append(str);
                }
                return sb.toString();
            } else {
                log.info("找不到指定的文件，请确认文件路径是否正确");
                return null;
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

    public static String getContent(File file){
        InputStream in = null;
        StringBuffer sb = new StringBuffer();
        try{
            // 一次读多个字节
            byte[] tempbytes = new byte[1024];
            int byteread = 0;
            in = new FileInputStream(file);
            //ReadFromFile.showAvailableBytes(in);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                String str = new String(tempbytes, 0, byteread);
                sb.append(str);
            }
            return sb.toString();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 写入文件
     * @param url 文件路劲
     * @param content 文件内容
     */
    public static void writeFile(String url,String content){
        try {
            File file = new File(url);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showAvailableBytes(InputStream in) {
        try {
            log.info("当前字节输入流中的字节数为 {} ",in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
