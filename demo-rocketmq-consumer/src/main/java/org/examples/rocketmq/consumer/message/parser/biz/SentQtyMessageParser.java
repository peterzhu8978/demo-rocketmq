package org.examples.rocketmq.consumer.message.parser.biz;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.examples.rocketmq.consumer.message.SingleMQMessage;
import org.examples.rocketmq.consumer.message.entity.SentQtyMessageEntity;
import org.examples.rocketmq.consumer.message.parser.DefaultSingleMQMessageParser;
import org.examples.rocketmq.consumer.message.parser.SingleMQMessageParser;
import org.springframework.stereotype.Component;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Slf4j
@Component
public class SentQtyMessageParser implements SingleMQMessageParser<SentQtyMessageEntity> {

    @Override
    public SingleMQMessage<SentQtyMessageEntity> parse(MessageExt messageExt) {
        //如果有特殊的解析逻辑,则在此实现，否则直接调用DefaultSingleMQMessageParser即可
        return DefaultSingleMQMessageParser.parse(messageExt, SentQtyMessageEntity.class);
    }
}
