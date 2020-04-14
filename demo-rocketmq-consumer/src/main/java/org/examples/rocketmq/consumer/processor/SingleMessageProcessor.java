package org.examples.rocketmq.consumer.processor;

import org.examples.rocketmq.consumer.message.SingleMQMessage;
import org.examples.rocketmq.consumer.message.entity.MessageEntity;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
public abstract class SingleMessageProcessor<T extends MessageEntity> extends MessageProcessor {

    public abstract void process(SingleMQMessage<T> message);
}
