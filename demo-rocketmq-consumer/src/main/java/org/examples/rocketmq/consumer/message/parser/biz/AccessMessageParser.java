package org.examples.rocketmq.consumer.message.parser.biz;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.examples.rocketmq.consumer.message.SingleMQMessage;
import org.examples.rocketmq.consumer.message.entity.AccessMessageEntity;
import org.examples.rocketmq.consumer.message.parser.DefaultSingleMQMessageParser;
import org.examples.rocketmq.consumer.message.parser.SingleMQMessageParser;
import org.examples.rocketmq.consumer.utils.ThreadLocalMessageType;
import org.springframework.stereotype.Component;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Slf4j
@Component
public class AccessMessageParser implements SingleMQMessageParser<AccessMessageEntity> {

    @Override
    public SingleMQMessage<AccessMessageEntity> parse(MessageExt messageExt) {
        //如果有特殊的解析逻辑,则在此实现，否则直接调用DefaultSingleMQMessageParser即可
        return DefaultSingleMQMessageParser.parse(messageExt, AccessMessageEntity.class);
    }

}
