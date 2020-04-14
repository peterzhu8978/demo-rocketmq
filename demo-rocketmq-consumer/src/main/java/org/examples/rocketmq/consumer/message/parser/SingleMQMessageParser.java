package org.examples.rocketmq.consumer.message.parser;

import org.apache.rocketmq.common.message.MessageExt;
import org.examples.rocketmq.consumer.message.SingleMQMessage;
import org.examples.rocketmq.consumer.message.entity.MessageEntity;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
public interface SingleMQMessageParser<T extends MessageEntity> {

    SingleMQMessage<T> parse(MessageExt messageExt);

}
