package com.moliang.run.quartz.config;

import com.moliang.websocket.MsgType;
import com.moliang.websocket.SocketMsg;
import com.moliang.websocket.WebSocketMsgServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/12 3:36
 * @Version 1.0
 */
@Slf4j
@Component
public class TestJob {

    private static int jobExecuteCounts = 0;

    private StringBuilder sb;

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    private void testJob() {
        if (sb == null) {
            Thread thread = Thread.currentThread();
            StackTraceElement[] stack = thread.getStackTrace();
            this.sb = getMsg(this.getClass().getName(), stack[1].getMethodName());
        }
        sendMsg(sb.append("<br>任务执行次数 : ")
                .append(++jobExecuteCounts)
                .append("<br>执行时间 : ")
                .append(df.format(new Date()))
                .toString());
        log.info("任务执行成功" + " : " + jobExecuteCounts);
    }

    private void sendMsg(String msg) {
        try {
            WebSocketMsgServer.sendInfo(new SocketMsg(msg, MsgType.INFO), "定时任务测试");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static StringBuilder getMsg(String className, String methodName) {
        StringBuilder sb = new StringBuilder();
        return sb.append("警告 : 为避免不必要的资源浪费, 请及时关闭不需要的定时任务<br>")
                .append("任务执行成功<br>").append("类名 : ")
                .append(className).append("<br>方法名 : ")
                .append(methodName);
    }
}
