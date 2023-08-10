package com.bingbaihanji.utils.code;// Java program to demonstrate

import com.bingbaihanji.utils.fileUtil.FileStreamUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Negative {

    /**
     * 将BufferedImage转换为InputStream
     * @param image
     * @return InputStream
     */
    public static InputStream bufferedImageToInputStream(BufferedImage image) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return new ByteArrayInputStream(os.toByteArray());
    }


    /**
     * @MethodName: antiColor
     * @Author 冰白寒祭
     * @Description: 获取反色图片
     * @Date: 2023/7/14  23:31
     */

    public static void antiColor(BufferedImage img, String finishedImgPath, String filename) throws IOException {
        BufferedImage imgs = antiColor(img);
        ImageIO.write(imgs, "png", new File(finishedImgPath + "/" + filename + ".png"));
    }

    /**
     * @MethodName: antiColor
     * @Author 冰白寒祭
     * @Description: 将图片中的每一个像素点进行反色计算
     * @Date: 2023/7/14 23:28
     * @param: BufferedImage
    * @return: BufferedImage
    */
    public static BufferedImage antiColor(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int r = img.getRGB(x, y);

                int red = (r >> 16) & 0x0ff;
                int green = (r >> 8) & 0x0ff;
                int blue = r & 0x0ff;

                red = 255 - red;
                green = 255 - green;
                blue = 255 - blue;

                r = (red << 16) | (green << 8) | blue;
                img.setRGB(x, y, r);
            }
        }
        return img;
    }

    /**
     * @MethodName: antiColorToInputStream
     * @Author 冰白寒祭
     * @Description: 将BufferedImage对象转为反色的InputStream对象
     * @Date: 2023/7/14 23:47
     * @param: BufferedImage
    * @return: InputStream
    */

    public static InputStream antiColorToInputStream(BufferedImage img) throws IOException {
        return bufferedImageToInputStream(antiColor(img));
    }




    public static void main(String[] args) throws IOException {
//        BufferedImage read = ImageIO.read(new FileInputStream("D:\\javaProject\\bingbaihanjiUtils\\src\\test\\java\\com\\bingbaihanji\\utils\\code\\uri_mr16125326738581.jpg"));
//        antiColor(read, "D:\\javaProject\\bingbaihanjiUtils\\src\\test\\java\\com\\bingbaihanji\\utils\\code", "url");
        BufferedImage read = ImageIO.read(new File("D:\\javaProject\\bingbaihanjiUtils\\gray.png"));
        InputStream inputStream = antiColorToInputStream(read);
//        InputStream inputStream = bufferedImageToInputStream(read);
        FileStreamUtil.streamSaveAsFile(inputStream,new File("gray.png"));

    }

}