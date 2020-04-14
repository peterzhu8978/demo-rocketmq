package org.examples.rocketmq.consumer.processor.context;

import lombok.Builder;
import lombok.Data;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
@Data
@Builder
public class MessagePostProcessContext {

    private String msgId;

    private boolean success;

    private String remark;
}
