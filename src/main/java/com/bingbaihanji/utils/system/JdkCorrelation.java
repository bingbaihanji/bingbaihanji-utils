package com.bingbaihanji.utils.system;

import java.io.File;

public class JdkCorrelation {

    /**
     * @param jmodPath   jmods目录
     * @param resultPath 输出目录
     * @MethodName: jmodsParseToJar
     * @Author 冰白寒祭
     * @Description: // 将高版本jdk中的jmod文件转为jar
     * @Date: 2023/7/22 0022 0:23
     */

    public static void jmodsParseToJar(String jmodPath, String resultPath) {
        File dateDir = new File(resultPath);
        if (!dateDir.exists()) {
            dateDir.mkdirs();
        }

        File files = new File(jmodPath);
        File[] files1 = files.listFiles();
        assert files1 != null;
        for (File file : files1) {
            File catalogue = new File(resultPath + file.getName());
            if (catalogue.mkdir()) {
                System.out.println(file.getName() + "目录创建成功");
                String format = String.format("jmod extract %s%s --dir %s%s",
                        jmodPath,
                        file.getName(),
                        resultPath,
                        file.getName()
                );
                String createJar = String.format("jar cf %s.jar -C classes .", file.getName());
                System.out.println(format);
                System.out.println(createJar);
                System.out.println();
                // 执行系统命令
                SystemCorrelation.systemOrder(format, null);
                SystemCorrelation.systemOrder(createJar, new File(resultPath + file.getName()));
            }
        }
    }
}
