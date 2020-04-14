package org.examples.rocketmq.consumer.message;

import lombok.Data;
import org.examples.rocketmq.consumer.message.entity.MessageEntity;

import java.util.List;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Data
public class ListMQMessage <T extends MessageEntity> extends MQMessage {

    private List<T> entityList;

}
