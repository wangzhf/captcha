package com.wangzhf;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static void main(String[] args) throws IOException {
        //rename();
        filterNames();
    }

    public static void filterNames(){
        String path = "E:\\temp\\images\\picc\\images\\";
        String tempPath = "E:\\temp\\images\\picc\\images2\\";
        String start = "0";
        File parent = new File(path);
        File[] files = parent.listFiles();
        if(files.length > 0) {
            for (int i=0; i< 10000; i++){
                boolean found = false;
                for (File file : files){
                    String filename = file.getName();
                    filename = filename.replace(".jpg", "");
                    if (i == Integer.parseInt(filename)){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    File tempFile = new File(tempPath + start + ".jpg");
                    File destFile = new File(path + i + ".jpg");
                    try {
                        FileUtils.copyFile(tempFile, destFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void rename() throws IOException {
        String path = "E:\\temp\\images\\cpic\\images\\";
        File parent = new File(path);
        File[] files = parent.listFiles();
        if(files.length > 0) {
            for(File file : files) {
                String fileName = file.getName();
                if(fileName.startsWith("images")){
                    String newName = fileName.replace("images", "");
                    File destFile = new File(file.getParent(), newName);
                    System.out.println(destFile.getName());
                    FileUtils.copyFile(file, destFile);
                }
            }
        }
    }

}
