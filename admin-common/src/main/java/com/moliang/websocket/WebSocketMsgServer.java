package com.moliang.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/24 1:52
 * @Version 1.0
 */
@Slf4j
@ServerEndpoint("/webSocket/{sid}")
@Component
public class WebSocketMsgServer {

    private static CopyOnWriteArraySet<WebSocketMsgServer> servers = new CopyOnWriteArraySet<>();

    private Session session;

    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
        this.session = session;
        //如果存在就先删除一个，防止重复推送消息
        servers.removeIf(webSocket -> webSocket.sid.equals(sid));
        servers.add(this);
        this.sid=sid;
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        servers.remove(this);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来"+sid+"的信息:"+message);
        //群发消息
        for (WebSocketMsgServer item : servers) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     **/
    public static void sendInfo(SocketMsg socketMsg,@PathParam("sid") String sid) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(socketMsg);
        log.info("推送消息到" + sid + "，推送内容:" + message);
        for (WebSocketMsgServer item : servers) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(sid==null) {
                    item.sendMessage(message);
                }else if(item.sid.equals(sid)){
                    item.sendMessage(message);
                }
            } catch (IOException ignored) { }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebSocketMsgServer that = (WebSocketMsgServer) o;
        return Objects.equals(session, that.session) &&
                Objects.equals(sid, that.sid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, sid);
    }

}
