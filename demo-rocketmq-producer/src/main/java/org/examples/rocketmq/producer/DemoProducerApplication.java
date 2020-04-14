package org.examples.rocketmq.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Version:
 * @Date: 2020/4/9
 * @Company: ruixiaoyun.ltd
 */
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DemoProducerApplication implements InitializingBean, DisposableBean {

    public static void main(String[] args) {
        SpringApplication.run(DemoProducerApplication.class, args);
    }

    private final DefaultMQProducer defaultMQProducer;

    @Override
    public void afterPropertiesSet() throws Exception {

    }


    @Override
    public void destroy() throws Exception {
        defaultMQProducer.shutdown();
        log.info("defaultMQProducer(groupName=[{}],instanceName=[{}],namesrvAddr=[{}]) stopped!",
                defaultMQProducer.getProducerGroup(),
                defaultMQProducer.getInstanceName(),
                defaultMQProducer.getNamesrvAddr());
    }
}
