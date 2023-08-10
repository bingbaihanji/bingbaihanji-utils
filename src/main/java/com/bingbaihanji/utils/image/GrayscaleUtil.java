package com.bingbaihanji.utils.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author 冰白寒祭
 * @date 2023-07-15 08:36:59
 * @description 图片灰度工具类
 */
public class GrayscaleUtil {
    public static BufferedImage gray(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = bufferedImage.getRGB(x, y);
                grayImage.setRGB(x, y, rgb);
            }
        }
        return grayImage;
    }
}
