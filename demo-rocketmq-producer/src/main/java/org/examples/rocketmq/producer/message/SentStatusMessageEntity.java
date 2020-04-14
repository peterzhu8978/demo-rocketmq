package org.examples.rocketmq.producer.message;

import lombok.Data;

/**
 * @Version:
 * @Date: 2020/4/13
 * @Company: ruixiaoyun.ltd
 */
@Data
public class SentStatusMessageEntity extends MessageEntity {

    private boolean sentStatus;
}
