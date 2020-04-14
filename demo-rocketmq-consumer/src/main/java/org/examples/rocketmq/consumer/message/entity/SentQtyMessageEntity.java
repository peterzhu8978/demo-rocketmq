package org.examples.rocketmq.consumer.message.entity;

import lombok.Data;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Data
public class SentQtyMessageEntity extends MessageEntity {

    private int sentQty;
}
