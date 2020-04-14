package org.examples.rocketmq.consumer.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.examples.rocketmq.consumer.message.parser.biz.SentStatusMessageParser;
import org.examples.rocketmq.consumer.processor.biz.SentStatusMessageProcessor;
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
public class SentStatusMessageListener implements MessageListenerConcurrently {

    private final SentStatusMessageParser sentStatusMessageParser;

    private final SentStatusMessageProcessor sentStatusMessageProcessor;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        //注意：这里的逻辑已经走线程池，无需自己另起线程池
        ThreadLocalMessageType.set("【状态回调】");
        if(msgs != null && msgs.size() > 0) {
            for(MessageExt messageExt : msgs) {
                sentStatusMessageProcessor.process(sentStatusMessageParser.parse(messageExt));
            }

        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
