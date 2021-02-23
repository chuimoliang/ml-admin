package com.moliang.websocket;

import lombok.Data;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/24 2:09
 * @Version 1.0
 */
@Data
public class SocketMsg {
    private String msg;
    private MsgType msgType;

    public SocketMsg(String msg, MsgType msgType) {
        this.msg = msg;
        this.msgType = msgType;
    }
}
