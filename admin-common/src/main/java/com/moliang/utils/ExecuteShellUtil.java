package com.moliang.utils;

import cn.hutool.core.io.IoUtil;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * @Use 执行Shell命令工具类
 * @Author Chui moliang
 * @Date 2021/2/18 15:27
 * @Version 1.0
 */
@Slf4j
public class ExecuteShellUtil {

    private Vector<String> stdout;

    Session session;

    public ExecuteShellUtil(final String ipAddress, final String username, final String password,int port) {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, ipAddress, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(3000);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

    }

    public int execute(final String command) {
        int returnCode = 0;
        ChannelShell channel = null;
        PrintWriter printWriter = null;
        BufferedReader input = null;
        stdout = new Vector<String>();
        try {
            channel = (ChannelShell) session.openChannel("shell");
            channel.connect();
            input = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            printWriter = new PrintWriter(channel.getOutputStream());
            printWriter.println(command);
            printWriter.println("exit");
            printWriter.flush();
            log.info("The remote command is: ");
            String line;
            while ((line = input.readLine()) != null) {
                stdout.add(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return -1;
        }finally {
            IoUtil.close(printWriter);
            IoUtil.close(input);
            if (channel != null) {
                channel.disconnect();
            }
        }
        return returnCode;
    }

    public void close(){
        if (session != null) {
            session.disconnect();
        }
    }

    public String executeForResult(String command) {
        execute(command);
        StringBuilder sb = new StringBuilder();
        for (String str : stdout) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ExecuteShellUtil shellUtil = null;
        try {
            shellUtil = new ExecuteShellUtil("8.136.0.95", "root", "******", 22);
            long startTime = System.currentTimeMillis();
            int res = shellUtil.execute("ls");
            long endTime = System.currentTimeMillis();
            if(res == 0) System.out.println(endTime / 2 - startTime / 2);
        } catch (Exception e) {
            System.out.println("fass");
        } finally {
            if(shellUtil != null) {
                shellUtil.close();
            }
        }
    }
}
