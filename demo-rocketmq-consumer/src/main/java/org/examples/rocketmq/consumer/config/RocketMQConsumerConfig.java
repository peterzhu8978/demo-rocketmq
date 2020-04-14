package org.examples.rocketmq.consumer.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.examples.rocketmq.consumer.listener.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class RocketMQConsumerConfig {

    @Value("${rocketmq.consumer.groupName}")
    private String consumerGroup;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.topics.accessTopic}")
    private String accessTopic;

    @Value("${rocketmq.consumer.topics.sentQtyTopic}")
    private String sentQtyTopic;

    @Value("${rocketmq.consumer.topics.sentStatusTopic}")
    private String sentStatusTopic;

    @Value("${rocketmq.consumer.topics.recycleQtyTopic}")
    private String recycleQtyTopic;

    private final AccessMessageListener accessMessageListener;

    private final SentQtyMessageListener sentQtyMessageListener;

    private final SentStatusMessageListener sentStatusMessageListener;

    private final RecycleQtyMessageListener recycleQtyMessageListener;


    private Map<String, DefaultMQPushConsumer> consumerContainer = new HashMap<>();


    @PostConstruct
    public void createAndStartConsumers() {
        createAndStartConsumer(sentQtyTopic, sentQtyMessageListener);
        createAndStartConsumer(sentStatusTopic, sentStatusMessageListener);
        createAndStartConsumer(recycleQtyTopic, recycleQtyMessageListener);
        createAndStartConsumer(accessTopic, accessMessageListener);
    }


    @PreDestroy
    public void destroy() {
        if(consumerContainer.size() == 0) {
            return;
        }
        for(Map.Entry<String, DefaultMQPushConsumer> entry : consumerContainer.entrySet()) {
            shutdownConsumer(entry.getValue(), entry.getKey());
        }
    }

    private void createAndStartConsumer(String topic, MessageListenerConcurrently messageListener) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer = new DefaultMQPushConsumer();
        //注意同一项目里多个Consumer的consumerGroup不能相同，否则消费有问题
        consumer.setConsumerGroup(consumerGroup + "_" + consumerContainer.size());
        consumer.setInstanceName(UUID.randomUUID().toString());
        consumer.setNamesrvAddr(namesrvAddr);
        //每次获取一条消息
        consumer.setConsumeMessageBatchMaxSize(1);
        consumer.setConsumeThreadMin(5);
        consumer.setConsumeThreadMax(20);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //一个Consumer只能注册一个监听器
        consumer.registerMessageListener(messageListener);

        try {
            //一个Consumer可订阅多个topic
            String[] topicArr = topic.split(",");
            for(String el : topicArr) {
                consumer.subscribe(el, "*");
            }
            consumer.start();
            log.info("MQPushConsumer(groupName=[{}],instanceName=[{}],namesrvAddr=[{}],subscribeTopic=[{}]) started!",
                    consumer.getConsumerGroup(),
                    consumer.getInstanceName(),
                    consumer.getNamesrvAddr(),
                    topic);
            consumerContainer.put(topic, consumer);
        } catch (MQClientException e) {
            log.error(e.getErrorMessage(), e);
        }

    }

    private void shutdownConsumer(DefaultMQPushConsumer consumer, String topic) {
        consumer.shutdown();
        log.info("MQPushConsumer(groupName=[{}],instanceName=[{}],namesrvAddr=[{}],subscribeTopic=[{}]) stopped!",
                consumer.getConsumerGroup(),
                consumer.getInstanceName(),
                consumer.getNamesrvAddr(),
                topic);

    }
}
