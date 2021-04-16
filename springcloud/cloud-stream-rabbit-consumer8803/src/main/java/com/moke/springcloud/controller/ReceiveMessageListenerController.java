package com.moke.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/16
 */
@Component
@EnableBinding(Sink.class) // 消费者Sink
@Slf4j
public class ReceiveMessageListenerController {
    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> msg) {
        log.info("消费者B,接受消息：" + msg.getPayload() + "，port：" + serverPort);
    }
}
