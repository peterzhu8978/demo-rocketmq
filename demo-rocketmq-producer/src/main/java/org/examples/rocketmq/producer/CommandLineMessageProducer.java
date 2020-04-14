package org.examples.rocketmq.producer;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.examples.rocketmq.producer.message.AccessMessageEntity;
import org.examples.rocketmq.producer.message.RecycleQtyMessageEntity;
import org.examples.rocketmq.producer.message.SentQtyMessageEntity;
import org.examples.rocketmq.producer.message.SentStatusMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * @Version:
 * @Date: 2020/4/9
 * @Company: ruixiaoyun.ltd
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CommandLineMessageProducer implements CommandLineRunner {

    private final DefaultMQProducer defaultMQProducer;

    @Value("${rocketmq.producer.topics.accessTopic}")
    private String accessTopic;

    @Value("${rocketmq.producer.topics.sentQtyTopic}")
    private String sentQtyTopic;

    @Value("${rocketmq.producer.topics.sentStatusTopic}")
    private String sentStatusTopic;

    @Value("${rocketmq.producer.topics.recycleQtyTopic}")
    private String recycleQtyTopic;


    @Override
    public void run(String... args) throws Exception {

       // manualSendMsg();

        mockSendBizMsg();

    }

    private void mockSendBizMsg() {
        new Thread(() -> {
            while(true) {
                Scanner sc = new Scanner(System.in);
                System.out.printf("%n%s", ">>>请输入消息类型(0-发送数量，1-发送状态，2-回收数量，3-用户访问)：");
                int type = sc.nextInt();

                String msgBody = null;
                String topic = null;

                switch (type) {
                    case 0:
                        topic = sentQtyTopic;
                        msgBody = JSON.toJSONString(buildSentQtyMessage());
                        break;
                    case 1:
                        topic = sentStatusTopic;
                        msgBody = JSON.toJSONString(buildSentStatusMessage());
                        break;
                    case 2:
                        topic = recycleQtyTopic;
                        msgBody = JSON.toJSONString(buildRecycleQtyMessage());
                        break;
                    case 3:
                        topic = accessTopic;
                        msgBody = JSON.toJSONString(buildAccessMessage());
                        break;
                    default:
                        System.out.printf("%n%s", ">>>无效的消息类型！");
                        break;
                }

                if(msgBody != null) {
                    Message rocketMqMsg = new Message(topic, msgBody.getBytes(Charset.defaultCharset()));
                    SendResult sendResult = null;
                    try {
                        sendResult = defaultMQProducer.send(rocketMqMsg);
                        System.out.printf("%n>>>发送结果：%s", sendResult.toString());
                    } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
                        System.out.printf("%n>>>发送失败：%s", e.getMessage());
                        log.error(e.getMessage(), e.getCause());
                    }
                }



            }
        }).start();


    }

    private AccessMessageEntity buildAccessMessage() {
        AccessMessageEntity entity = new AccessMessageEntity();
        entity.setDataModel("瑞小云小贷点击用户-多头贷款");
        entity.setOperator(0);
        entity.setProduct("BDYQH");
        entity.setLabel("BDYQH_20200414_01");
        entity.setSentDate("2020-04-14");
        entity.setSentChannel("888888");
        entity.setProvince("江苏");
        entity.setIp("10.10.10.10");
        entity.setRySecret("V3_" + UUID.randomUUID().toString().replace("-", ""));
        return entity;
    }

    private SentQtyMessageEntity buildSentQtyMessage() {
        SentQtyMessageEntity entity = new SentQtyMessageEntity();
        entity.setDataModel("瑞小云小贷点击用户-多头贷款");
        entity.setOperator(0);
        entity.setProduct("BDYQH");
        entity.setLabel("BDYQH_20200414_01");
        entity.setSentDate("2020-04-14");
        entity.setSentChannel("888888");
        entity.setProvince("江苏");
        entity.setSentQty(10000);
        return entity;
    }

    private RecycleQtyMessageEntity buildRecycleQtyMessage() {
        RecycleQtyMessageEntity entity = new RecycleQtyMessageEntity();
        entity.setDataModel("瑞小云小贷点击用户-多头贷款");
        entity.setOperator(0);
        entity.setProduct("BDYQH");
        entity.setLabel("BDYQH_20200414_01");
        entity.setSentDate("2020-04-14");
        entity.setSentChannel("888888");
        entity.setProvince("江苏");
        entity.setRecycleQty(10000);
        return entity;
    }

    private List<SentStatusMessageEntity> buildSentStatusMessage() {

        List<SentStatusMessageEntity> entityList = new ArrayList<>();

        int num = RandomUtils.nextInt(1, 50);
        for(int i=0 ; i<num; i++) {
            SentStatusMessageEntity entity = new SentStatusMessageEntity();
            entity.setDataModel("瑞小云小贷点击用户-多头贷款");
            entity.setOperator(0);
            entity.setProduct("BDYQH");
            entity.setLabel("BDYQH_20200414_01");
            entity.setSentDate("2020-04-14");
            entity.setSentChannel("888888");
            entity.setProvince("江苏");
            entity.setSentStatus(RandomUtils.nextBoolean());
            entityList.add(entity);
        }
        return entityList;
    }



    private void manualSendMsg() {
        new Thread(() -> {
            while(true) {
                Scanner sc = new Scanner(System.in);

                System.out.printf("%n%s", ">>>请输入消息Topic：");
                String topic = sc.nextLine();

//                System.out.printf("%n%s", ">>>请输入消息类型(0-发送量统计，1-状态回调统计，2-失败回收统计，3-点击统计)：");
                System.out.printf("%n%s", ">>>请输入消息Content：");
                String messageBody = sc.nextLine();

                if(messageBody != null) {
                    Message rocketMqMsg = new Message(topic, messageBody.getBytes(Charset.defaultCharset()));
                    SendResult sendResult = null;
                    try {
                        sendResult = defaultMQProducer.send(rocketMqMsg);
                        System.out.printf("%n>>>发送结果：%s", sendResult.toString());
                    } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
                        System.out.printf("%n>>>发送失败：%s", e.getMessage());
                        log.error(e.getMessage(), e.getCause());
                    }
                }


            }
        }).start();
    }
}
