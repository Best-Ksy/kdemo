package com.kj.kdemo.service;

import com.kj.kdemo.dto.Message;

/**
 * ClassName: KafkaProducerService
 * Description:
 * date: 2020/10/10 11:56
 *
 * @author Ksy
 */
public interface KafkaProducerService {

    void pushMessage(Message message);

}
