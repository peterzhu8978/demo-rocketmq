package org.examples.rocketmq.consumer.message;

import lombok.Data;
import org.examples.rocketmq.consumer.message.entity.MessageEntity;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Data
public class SingleMQMessage <T extends MessageEntity> extends MQMessage {

    private T entity;

}
