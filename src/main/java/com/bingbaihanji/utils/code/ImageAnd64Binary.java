package com.bingbaihanji.utils.code;


import java.io.*;
import java.util.Base64;


/**
 * 功能：Base64编码与的图片相互转化
 */
public class ImageAnd64Binary {
    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param imgSrcPath 生成64编码的图片的路径
     * @return String
     */
    public String getImageStr(String imgSrcPath) {
        InputStream inputStream;
        byte[] data = null;
        //读取图片字节数组
        try {
            inputStream = new FileInputStream(imgSrcPath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     *
     * @param imgStr        转换为图片的字符串
     * @param imgCreatePath 将64编码生成图片的路径
     * @param FileName      文件名
     * @return boolean
     */
    public boolean generateImage(String imgStr, String imgCreatePath, String FileName) {

        if (null == imgStr) {   //图像数据为空
            return false;
        }
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            //Base64解码
//            byte[] b=decoder.decodeBuffer(imgStr);
            byte[] bytes = decoder.decode(imgStr);
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] < 0) { //调整异常数据
                    bytes[i] += 256;
                }
            }

            OutputStream out = new FileOutputStream(imgCreatePath + "//" + FileName);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     *
     * @param fileBase64String 转换为图片的字符串
     * @param filePath         将64编码生成图片的路径
     * @param fileName         文件名
     * @return File
     */

    public File convertBase64ToFile(String fileBase64String,
                                    String filePath,
                                    String fileName) {

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }

//            BASE64Decoder decoder = new BASE64Decoder();
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bfile = decoder.decode(fileBase64String);
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
