package com.bingbaihanji.utils.code;

import java.util.Arrays;

public class ArrayUtil {
    public ArrayUtil() {
    }

    /**
     * @Description 从一个整数数组中删除所有在另一个整数数组中出现过的元素
     * @param multiset
     * @param subset
     * @return int[]
     */
    public static int[] deleteSubset(int[] multiset, int[] subset) {
        // 创建一个新数组来存储结果
        int[] result = new int[multiset.length];
        // 记录结果数组中元素的个数
        int size = 0;
        // 遍历 multiset 中的每个元素
        for (int element : multiset) {
            // 判断该元素是否在 subset 中出现过
            boolean found = false;
            for (int subElement : subset) {
                if (element == subElement) {
                    found = true;
                    break;
                }
            }
            // 如果该元素未在 subset 中出现过，则将其添加到结果数组中
            if (!found) {
                result[size] = element;
                size++;
            }
        }
        // 返回删除后的结果数组（仅包含前 size 个元素）
        return Arrays.copyOf(result, size);
    }
}
