package com.kj.kdemo.consumer;

import com.kj.kdemo.socket.ChatSocket;
import com.kj.kdemo.utils.JsonUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class KafkaConsumer {


    @KafkaListener(topics = {"topic1"})
    public void onMessage(ConsumerRecord<?,?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println(record.topic()+"-"+record.partition()+"-"+record.value());
        Object message = record.value();
        Map<String, String> messageMap = JsonUtil.stringToCollect(message.toString());
        List<ChatSocket> webSockets = ChatSocket.webSockets;
        for (ChatSocket cs:webSockets) {
            if (messageMap.get("mysocketid").equals(cs.getOtsocketid())) {
                 cs.sendMessage(messageMap.get("text"));
            }
            if (messageMap.get("otsocketid").equals(cs.getMysocketid())){
                cs.sendMessage(messageMap.get("text"));
            }

        }
    }
}
