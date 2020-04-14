package org.examples.rocketmq.consumer.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.examples.rocketmq.consumer.message.parser.biz.SentQtyMessageParser;
import org.examples.rocketmq.consumer.processor.biz.SentQtyMessageProcessor;
import org.examples.rocketmq.consumer.utils.ThreadLocalMessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class SentQtyMessageListener implements MessageListenerConcurrently {

    private final SentQtyMessageParser sentQtyMessageParser;

    private final SentQtyMessageProcessor sentQtyMessageProcessor;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        ThreadLocalMessageType.set("【短信发送】");
        if(msgs != null && msgs.size() > 0) {
            for(MessageExt messageExt : msgs) {
                sentQtyMessageProcessor.process(sentQtyMessageParser.parse(messageExt));
            }

        }
        ThreadLocalMessageType.clear();
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
