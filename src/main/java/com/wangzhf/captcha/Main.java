package com.wangzhf.captcha;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        cpicCode();
        //piccCode();
    }

    private static void piccCode() throws IOException {
        File dir = new File("D:\\temp\\images\\cpic\\mock");
        int w = 70, h = 30;
        File[] listFile = dir.listFiles();
        if(listFile != null && listFile.length > 0) {
            for(File temp : listFile) {
                temp.delete();
            }
        }

        List<ImageResut> list = new ArrayList<>();
        CaptchaProducer.FONT_SIZE = 20;
        CaptchaProducer.VERIFY_CODES = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // CaptchaProducer.FONT_NAME = "楷体";
        for (int i = 0; i < 10000; i++) {
            String verifyCode = CaptchaProducer.generateVerifyCode(4);

            BufferedImage bi = CaptchaProducer.getNormalImage(w, h, verifyCode);
            String path = "E:\\temp\\images\\picc\\mock\\";
            File parent = new File(path);
            if(!parent.exists()){
                parent.mkdirs();
            }
            String fileName = i + ".jpg";
            String result = path + fileName;
            File file = new File(result);
            OutputStream out = new FileOutputStream(file);
            try {
                ImageIO.write(bi, "jpg", out);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageResut ret = new ImageResut(fileName, verifyCode);
            list.add(ret);
        }

        if(list.size() > 0){
            String csvPath = "E:\\temp\\images\\picc\\";
            String csvFileName = "mock.csv";
            String[] headers = {"FileName", "ImageCode"};

            CSVFormat format = CSVFormat.DEFAULT.withHeader(headers).withSkipHeaderRecord();

            try (Writer writer = new FileWriter(new File(csvPath, csvFileName))){
                CSVPrinter printer = new CSVPrinter(writer, format);
                printer.printRecord(headers);
                for(ImageResut ir : list) {
                    List<String> records = new ArrayList<>();
                    records.add(ir.getFileName());
                    records.add(ir.getVerfyCode());
                    printer.printRecord(records);
                }

            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private static void cpicCode() throws IOException {
        File dir = new File("D:\\temp\\images\\cpic\\mock");
        int w = 120, h = 32;
        File[] listFile = dir.listFiles();
        if(listFile != null && listFile.length > 0) {
            for(File temp : listFile) {
                temp.delete();
            }
        }

        List<ImageResut> list = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            String verifyCode = CaptchaProducer.generateVerifyCode(4);

            BufferedImage bi = CaptchaProducer.getImage(w, h, verifyCode);

            ImageModel model = new ImageModel();
            model.paintComponent(bi);

            String path = "E:\\temp\\images\\cpic\\mock\\";
            File parent = new File(path);
            if(!parent.exists()){
                parent.mkdirs();
            }
            String fileName = i + ".jpg";
            String result = path + fileName;
            File file = new File(result);
            OutputStream out = new FileOutputStream(file);
            try {
                ImageIO.write(bi, "jpg", out);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageResut ret = new ImageResut(fileName, verifyCode);
            list.add(ret);
        }

        if(list.size() > 0){
            String csvPath = "E:\\temp\\images\\cpic\\";
            String csvFileName = "mock.csv";
            String[] headers = {"FileName", "ImageCode"};

            CSVFormat format = CSVFormat.DEFAULT.withHeader(headers).withSkipHeaderRecord();

            try (Writer writer = new FileWriter(new File(csvPath, csvFileName))){
                CSVPrinter printer = new CSVPrinter(writer, format);
                for(ImageResut ir : list) {
                    List<String> records = new ArrayList<>();
                    records.add(ir.getFileName());
                    records.add(ir.getVerfyCode());
                    printer.printRecord(records);
                }

            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    static class ImageResut {
        private String fileName;
        private String verfyCode;

        public ImageResut(String fileName, String verfyCode) {
            this.fileName = fileName;
            this.verfyCode = verfyCode;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getVerfyCode() {
            return verfyCode;
        }

        public void setVerfyCode(String verfyCode) {
            this.verfyCode = verfyCode;
        }
    }

}
