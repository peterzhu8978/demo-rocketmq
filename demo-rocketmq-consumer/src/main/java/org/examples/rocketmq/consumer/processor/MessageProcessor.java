package org.examples.rocketmq.consumer.processor;

import lombok.extern.slf4j.Slf4j;
import org.examples.rocketmq.consumer.message.entity.MessageEntity;
import org.examples.rocketmq.consumer.processor.context.MessagePostProcessContext;
import org.examples.rocketmq.consumer.processor.context.MessagePreProcessContext;
import org.examples.rocketmq.consumer.utils.ThreadLocalMessageType;

/**
 * @InterfaceName: BizProcessor
 * @Description:
 * @Version:
 * @Date: 2020/4/14
 * @Author: rong.zhu
 * @Email: zhurong@ruixiaoyun.com
 */
@Slf4j
public abstract class MessageProcessor<T extends MessageEntity> {


    /**
     * 消息处理前的逻辑（预处理，如msgId的幂等性校验、其它业务ID的幂等性校验、查询业务上下文信息）
     * @param context
     */
    protected abstract boolean preProcess(MessagePreProcessContext context);

    /**
     * 消息处理后的逻辑（如根据处理状态执行相关业务逻辑）
     * @param context
     */
    protected abstract void postProcess(MessagePostProcessContext context);


    /**
     * 针对msgId的通用幂等性校验
     * @param msgId
     * @return
     */
    protected boolean idempotenceCheck(String msgId) {
        log.info("{}开始利用redis setnx()命令进行msgId的幂等性校验...", ThreadLocalMessageType.get());
        //...
        return true;
    }

}
