package org.examples.rocketmq.consumer.message.parser;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.examples.rocketmq.consumer.message.ListMQMessage;
import org.examples.rocketmq.consumer.message.entity.MessageEntity;
import org.examples.rocketmq.consumer.utils.ThreadLocalMessageType;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Slf4j
public class DefaultListMQMessageParser<T extends MessageEntity> {

    public static <T extends MessageEntity> ListMQMessage<T> parse(MessageExt messageExt, Class<T> clz) {
        if(messageExt == null) {
            return null;
        }
        if(messageExt.getBody() == null) {
            return null;
        }
        String msgBody = new String(messageExt.getBody());
        log.info("{}received message: msgTopic=[{}], msgQueueId=[{}], msgQueueOffset=[{}], commitLogOffset=[{}], msgId=[{}], msgContent=[{}]",
                ThreadLocalMessageType.get(), messageExt.getTopic(), messageExt.getQueueId(), messageExt.getQueueOffset(),
                messageExt.getCommitLogOffset(), messageExt.getMsgId(), msgBody);
        try{
            log.info("{}JSON反序列化消息内容...", ThreadLocalMessageType.get());
            ListMQMessage<T> listMQMessage = new ListMQMessage<T>();
            listMQMessage.setMsgId(messageExt.getMsgId());
            listMQMessage.setEntityList(JSON.parseArray(msgBody, clz));
            return listMQMessage;
        }catch (Exception e) {
            log.error("{}JSON反序列化失败：消息格式错误!", ThreadLocalMessageType.get());
            return null;
        }

    }
}
