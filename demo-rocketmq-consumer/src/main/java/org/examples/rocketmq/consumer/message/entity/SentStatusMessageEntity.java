package org.examples.rocketmq.consumer.message.entity;

import lombok.Data;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Data
public class SentStatusMessageEntity extends MessageEntity {

    private boolean sentStatus;

}
