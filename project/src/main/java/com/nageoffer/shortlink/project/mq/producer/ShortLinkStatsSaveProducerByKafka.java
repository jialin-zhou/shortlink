package com.nageoffer.shortlink.project.mq.producer;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.nageoffer.shortlink.project.common.constant.RedisKeyConstant.SHORT_LINK_STATS_KAFKA_TOPIC_KEY;

/**
 * 短链接监控状态保存-Kafka消息队列-生产这
 */
@Component
@Slf4j
public class ShortLinkStatsSaveProducerByKafka {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(Map<String, String> producerMap) {
        log.info("发送消息：{}", producerMap);
        kafkaTemplate.send(SHORT_LINK_STATS_KAFKA_TOPIC_KEY, JSON.toJSONString(producerMap));
    }
}
