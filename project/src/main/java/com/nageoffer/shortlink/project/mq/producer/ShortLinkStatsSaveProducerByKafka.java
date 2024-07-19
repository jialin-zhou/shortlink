package com.nageoffer.shortlink.project.mq.producer;

import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.nageoffer.shortlink.project.common.constant.RedisKeyConstant.SHORT_LINK_STATS_STREAM_TOPIC_KEY;

/**
 * 短链接监控状态保存-Kafka消息队列-生产这
 */
@Component
public class ShortLinkStatsSaveProducerByKafka {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(Map<String, String> producerMap) {
        kafkaTemplate.send(SHORT_LINK_STATS_STREAM_TOPIC_KEY, JSON.toJSONString(producerMap));
    }
}
