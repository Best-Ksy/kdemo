package com.kj.kdemo.socket;


import com.kj.kdemo.dto.Message;
import com.kj.kdemo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket/{mysocketid}/{otsocketid}")
@Component
public class ChatSocket {

    public static AtomicInteger onlineNumber = new AtomicInteger(0);
    public static List<ChatSocket> webSockets = new CopyOnWriteArrayList<ChatSocket>();
    private Session session;
    private String mysocketid;
    private String otsocketid;

    private static KafkaProducerService kafkaProducerService;

    @Autowired
    public ChatSocket(KafkaProducerService kafkaProducerService){
        this.kafkaProducerService = kafkaProducerService;
    }

    public ChatSocket(){}


    /**
     * 建立连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("mysocketid") String mysocketid, @PathParam("otsocketid") String otsocketid){

        if (mysocketid == null || "".equals(mysocketid) || otsocketid == null || "".equals(otsocketid)){
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        for (ChatSocket chatWebSocket : webSockets) {
            if (mysocketid.equals(chatWebSocket.mysocketid) && otsocketid.equals(chatWebSocket.otsocketid)) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        onlineNumber.incrementAndGet();
        this.session = session;
        this.mysocketid = mysocketid;
        this.otsocketid = otsocketid;
        webSockets.add(this);

        System.out.println("有新连接加入！ 当前在线人数" + onlineNumber.get() + "socket对象数"+webSockets.size());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber.decrementAndGet();
        webSockets.remove(this);
        System.out.println("有连接关闭！ 当前在线人数" + onlineNumber.get());
    }

    @OnMessage
    public void onMessage(String message, @PathParam("mysocketid") String mysocketid , @PathParam("otsocketid") String otsocketid) {
        Message message1 = new Message(message, mysocketid, otsocketid);

        //放入kafka
        kafkaProducerService.pushMessage(message1);
    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getMysocketid() {
        return mysocketid;
    }

    public void setMysocketid(String mysocketid) {
        this.mysocketid = mysocketid;
    }

    public String getOtsocketid() {
        return otsocketid;
    }

    public void setOtsocketid(String otsocketid) {
        this.otsocketid = otsocketid;
    }

}
