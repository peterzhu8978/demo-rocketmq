package org.examples.rocketmq.consumer.message.parser.biz;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.examples.rocketmq.consumer.message.ListMQMessage;
import org.examples.rocketmq.consumer.message.entity.SentStatusMessageEntity;
import org.examples.rocketmq.consumer.message.parser.DefaultListMQMessageParser;
import org.examples.rocketmq.consumer.message.parser.ListMQMessageParser;
import org.springframework.stereotype.Component;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Slf4j
@Component
public class SentStatusMessageParser implements ListMQMessageParser<SentStatusMessageEntity> {

    @Override
    public ListMQMessage<SentStatusMessageEntity> parse(MessageExt messageExt) {
        //如果有特殊解析逻辑则在此实现，否则直接调用DefaultListMQMessageParser即可
        return DefaultListMQMessageParser.parse(messageExt, SentStatusMessageEntity.class);
    }
}
