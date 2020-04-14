package org.examples.rocketmq.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Version:
 * @Date: 2020/4/9
 * @Company: ruixiaoyun.ltd
 */
@Slf4j
@Configuration
public class RocketMQProducerConfig {

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

   //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
    @Value("${rocketmq.producer.instanceName}")
    private String instanceName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;


    @Bean
    public DefaultMQProducer defaultMQProducer() {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setProducerGroup(groupName);
        defaultMQProducer.setInstanceName(instanceName);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
//        defaultMQProducer.setMaxMessageSize();
//        defaultMQProducer.setSendMsgTimeout();
        defaultMQProducer.setRetryAnotherBrokerWhenNotStoreOK(true);
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(2);
        defaultMQProducer.setRetryTimesWhenSendFailed(2);
        try {
            defaultMQProducer.start();
            log.info("defaultMQProducer(groupName=[{}],instanceName=[{}],namesrvAddr=[{}]) started!",
                    defaultMQProducer.getProducerGroup(),
                    defaultMQProducer.getInstanceName(),
                    defaultMQProducer.getNamesrvAddr());
        } catch (MQClientException e) {
            log.error(e.getErrorMessage(), e.getCause());
        }
        return defaultMQProducer;
    }

}
