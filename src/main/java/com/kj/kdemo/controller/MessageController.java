package com.kj.kdemo.controller;

import com.kj.kdemo.common.ResponseData;
import com.kj.kdemo.dto.Message;
import com.kj.kdemo.service.KafkaProducerService;
import com.kj.kdemo.socket.ChatSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.Session;
import java.util.List;

/**
 * ClassName: MessageController
 * Description:
 * date: 2020/10/10 9:40
 *
 * @author Ksy
 */

@Controller
@RequestMapping("/test/conn")
public class MessageController {


    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**
     * 一对一用户第一次连接
     * @param message
     * @return
     */
    @PostMapping("/send")
    public ResponseData pushMessage(@RequestBody Message message){
//        String newSocketid = null;
//        String mUserid = message.getMuserid();
//        String oUserid = message.getOuserid();
//        int compare = mUserid.compareTo(oUserid);
//        //根据双方的userid生成socketid
//        if (compare < 0){
//            newSocketid = mUserid + "_" + oUserid;
//        }else{
//            newSocketid = oUserid + "_" + mUserid;
//        }
//        message.setSocketid(newSocketid);
        kafkaProducerService.pushMessage(message);
        return new ResponseData();
    }
}
