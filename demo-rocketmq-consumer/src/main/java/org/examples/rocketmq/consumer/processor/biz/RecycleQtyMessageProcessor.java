package org.examples.rocketmq.consumer.processor.biz;

import lombok.extern.slf4j.Slf4j;
import org.examples.rocketmq.consumer.message.SingleMQMessage;
import org.examples.rocketmq.consumer.message.entity.RecycleQtyMessageEntity;
import org.examples.rocketmq.consumer.processor.SingleMessageProcessor;
import org.examples.rocketmq.consumer.processor.context.MessagePostProcessContext;
import org.examples.rocketmq.consumer.processor.context.MessagePreProcessContext;
import org.examples.rocketmq.consumer.utils.ThreadLocalMessageType;
import org.springframework.stereotype.Component;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Slf4j
@Component
public class RecycleQtyMessageProcessor extends SingleMessageProcessor {

    @Override
    protected boolean preProcess(MessagePreProcessContext context) {
        String msgId = context.getMsgId();
        log.info("{}消息(msgId={})事前处理...", ThreadLocalMessageType.get(), msgId);

        //幂等性校验
        if(!super.idempotenceCheck(context.getMsgId())) {
            log.warn("{}消息(msgId={})非幂等", ThreadLocalMessageType.get(), msgId);
            return false;
        }

        //一般来说对msgId作幂等性校验是防止重复消费处理
        //有些业务除了要对msgId作控制外，还需要对业务ID做幂等性控制，如订单ID

        //其它私有预处理和校验逻辑

        return true;
    }

    @Override
    public void process(SingleMQMessage singleMQMessage) {
        if(singleMQMessage == null) {
            return;
        }
        String msgId = singleMQMessage.getMsgId();

        //preProcess
        MessagePreProcessContext preProcessContext = MessagePreProcessContext.builder().msgId(msgId).build();
        if(!preProcess(preProcessContext)) {
            return;
        }

        //process
        RecycleQtyMessageEntity messageEntity = (RecycleQtyMessageEntity) singleMQMessage.getEntity();
        log.info("{}消息(msgId={})事中处理...", ThreadLocalMessageType.get(), msgId);


        //postProcess
        MessagePostProcessContext postProcessContext = MessagePostProcessContext.builder()
                .msgId(msgId).success(true).remark("ok").build();
        postProcess(postProcessContext);

    }


    @Override
    protected void postProcess(MessagePostProcessContext context) {
        // 其它私有事后处理逻辑

        String msgId = context.getMsgId();
        log.info("{}消息(msgId={})事后处理...", ThreadLocalMessageType.get(), msgId);

        if(context.isSuccess()) {
            //...
        }else {
            //...
        }
    }
}
