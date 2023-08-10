package com.bingbaihanji.utils.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemCorrelation {

    /**
     * @param cmd 需要执行的命令
     * @param dir 执行命令的子进程的工作目录, null 表示和当前主进程工作目录相同
     * @MethodName: systemOrder
     * @Author 冰白寒祭
     * @Description: // 执行系统命令, 返回执行结果
     * @Date: 2023/7/22 0022 0:23
     * @return: String  返回执行结果
     */

    public static String systemOrder(String cmd, File dir) {
        StringBuilder result = new StringBuilder();

        Process process = null;
        BufferedReader bufrIn = null;
        BufferedReader bufrError = null;

        try {
            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            process = Runtime.getRuntime().exec(cmd, null, dir);

            // 方法阻塞, 等待命令执行完成（成功会返回0）
            process.waitFor();

            // 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), System.getProperty("file.encoding")));
            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), System.getProperty("file.encoding")));

            // 读取输出
            String line;
            while ((line = bufrIn.readLine()) != null) {
                result.append(line).append('\n');
            }
            while ((line = bufrError.readLine()) != null) {
                result.append(line).append('\n');
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (bufrIn != null) {
                try {
                    bufrIn.close();
                } catch (Exception e) {
                    // nothing
                }
            }
            if (bufrError != null) {
                try {
                    bufrError.close();
                } catch (Exception e) {
                    // nothing
                }
            }
            // 销毁子进程
            if (process != null) {
                process.destroy();
            }
        }
        // 返回执行结果
        return result.toString();
    }
}
