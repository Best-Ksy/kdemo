package com.kj.kdemo.service.impl;

import com.kj.kdemo.dto.Message;
import com.kj.kdemo.service.KafkaProducerService;
import com.kj.kdemo.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ClassName: KafkaProducerServiceImpl
 * Description:
 * date: 2020/10/10 12:00
 *
 * @author Ksy
 */
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void pushMessage(@RequestBody Message message) {
        String messageJson = JsonUtil.convertObjectToJSON(message);
        kafkaTemplate.send("topic1",messageJson);
    }
}
