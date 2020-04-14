package org.examples.rocketmq.consumer.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.examples.rocketmq.consumer.message.parser.biz.AccessMessageParser;
import org.examples.rocketmq.consumer.processor.biz.AccessMessageProcessor;
import org.examples.rocketmq.consumer.utils.ThreadLocalMessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Version:
 * @Date: 2020/4/10
 * @Company: ruixiaoyun.ltd
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AccessMessageListener implements MessageListenerConcurrently {

    private final AccessMessageParser accessMessageParser;

    private final AccessMessageProcessor accessMessageProcessor;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext context) {
        ThreadLocalMessageType.set("【用户点击】");
        if(msgList != null && msgList.size() > 0) {
            for(MessageExt messageExt : msgList) {
                accessMessageProcessor.process(accessMessageParser.parse(messageExt));
            }

        }
        ThreadLocalMessageType.clear();
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
