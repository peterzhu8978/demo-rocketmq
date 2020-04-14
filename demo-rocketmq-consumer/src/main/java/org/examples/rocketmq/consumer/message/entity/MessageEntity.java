package org.examples.rocketmq.consumer.message.entity;

import lombok.Data;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Data
public class MessageEntity {

    private int operator;

    private String dataModel;

    private String product;

    private String label;

    private String sentDate;

    private String sentChannel;

    private String province;

}
