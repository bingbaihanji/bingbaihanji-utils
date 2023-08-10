package com.bingbaihanji.utils.code;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author 冰白寒祭
 * @date 2023-06-07 12:44:26
 * @description 这是类描述
 */
public class CaptchaUtilTest {
    public static void main(String[] args) throws IOException {
        BufferedImage bufferedImage = CaptchaUtil.genCaptchaImg(CaptchaUtil.genCaptcha(6));
        File outputfile  = new File("D:\\javaProject\\bingbaihanjiUtils\\src\\test\\java\\com\\bingbaihanji\\utils\\code\\save.png");
        ImageIO.write(bufferedImage,  "png",  outputfile);
    }
}
