package com.bingbaihanji.utils.code;

import com.bingbaihanji.utils.math.algorithm.BigNumberAlgorithm;

import java.math.BigDecimal;

public class PITest {
    public static void main(String[] args) {
        System.out.println(BigNumberAlgorithm.PIValue(2500));
        BigDecimal bigDecimal = BigNumberAlgorithm.PIValue(2500);
        System.out.println(bigDecimal.toString().length());
    }
}
