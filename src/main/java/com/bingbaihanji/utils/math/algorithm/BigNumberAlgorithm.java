package com.bingbaihanji.utils.math.algorithm;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigNumberAlgorithm {


    /**
     * @param precision 精度
     * @return BigDecimal
     * @MethodName: PIValue
     * @Author 冰白寒祭
     * @Description: 获取高精度PI值LaTeX公式: \frac{1}{\pi}=\frac{\sqrt{8}}{9801}\sum_{n=0}^{\infty}{\frac{(4n)!(1103+26390n)}{(n!)^{4}396^{4n}}}
     */
    public static BigDecimal PIValue(int precision) {
        int m = precision / 8; //通过公式迭代一次可以获得8位有效数字
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        BigDecimal constantValue = (sqrt(BigDecimal.valueOf(8), precision).divide(BigDecimal.valueOf(9801), mc));
        BigDecimal iterationValue = BigDecimal.ZERO;

        for (int n = 0; n <= m; n++) {
            BigInteger factorONE_member = factorial(BigInteger.valueOf(4).multiply(BigInteger.valueOf(n)));
            BigInteger factorONE_denominator = factorial(BigInteger.valueOf(n)).pow(4);
            BigInteger factorTWO_member = BigInteger.valueOf(1103).add(BigInteger.valueOf(26390L * n));
            BigInteger factorTWO_denominator = BigInteger.valueOf(396).pow(4 * n);
            BigDecimal member = new BigDecimal(factorONE_member.multiply(factorTWO_member));
            BigDecimal denominator = new BigDecimal((factorONE_denominator.multiply(factorTWO_denominator)));
            iterationValue = iterationValue.add(member.divide(denominator, mc));
        }
        BigDecimal reciprocalPI = constantValue.multiply(iterationValue);
        BigDecimal PI = BigDecimal.ONE.divide(reciprocalPI, mc);
        return PI;

    }

    /**
     * @param num 需要进行阶乘运算的参数
     * @return BigInteger
     * @MethodName: factorial
     * @Author 冰白寒祭
     * @Description: 利用递归计算阶乘
     */
    public static BigInteger factorial(BigInteger num) {
        if (num.equals(BigInteger.ONE) || num.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        } else {
            return num.multiply(factorial(num.subtract(BigInteger.ONE)));
        }
    }


    /**
     * @param value 需要迭代的数
     * @param scale 精度规模
     * @return BigDecimal
     * @MethodName: factorial
     * @Author 冰白寒祭
     * @Description: BigDecimal使用牛顿迭代法计算平方根
     */


    public static BigDecimal sqrt(BigDecimal value, int scale) {
        BigDecimal num2 = BigDecimal.valueOf(2);
        MathContext mc = new MathContext(scale, RoundingMode.HALF_UP);
        BigDecimal deviation = value;
        int cnt = 0;
        while (cnt < scale) {
            deviation = (deviation.add(value.divide(deviation, mc))).divide(num2, mc);
            cnt++;
        }
        deviation = deviation.setScale(scale, RoundingMode.HALF_UP);
        return deviation;
    }

}
