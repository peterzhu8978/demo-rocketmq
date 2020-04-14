package org.examples.rocketmq.consumer.message.parser.biz;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.examples.rocketmq.consumer.message.SingleMQMessage;
import org.examples.rocketmq.consumer.message.entity.RecycleQtyMessageEntity;
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
public class RecycleQtyMessageParser implements SingleMQMessageParser<RecycleQtyMessageEntity> {
    @Override
    public SingleMQMessage<RecycleQtyMessageEntity> parse(MessageExt messageExt) {
        //如果有特殊解析逻辑则在此实现，否则直接调用DefaultSingleMQMessageParser即可
        return DefaultSingleMQMessageParser.parse(messageExt, RecycleQtyMessageEntity.class);
    }
}
