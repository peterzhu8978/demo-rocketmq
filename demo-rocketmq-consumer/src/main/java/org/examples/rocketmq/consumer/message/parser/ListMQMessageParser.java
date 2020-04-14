package org.examples.rocketmq.consumer.message.parser;

import org.apache.rocketmq.common.message.MessageExt;
import org.examples.rocketmq.consumer.message.ListMQMessage;
import org.examples.rocketmq.consumer.message.entity.MessageEntity;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
public interface ListMQMessageParser<T extends MessageEntity> {

    ListMQMessage<T> parse(MessageExt messageExt);

}
