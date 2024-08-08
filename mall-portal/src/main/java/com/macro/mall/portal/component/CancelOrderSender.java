package com.macro.mall.portal.component;

import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 取消订单消息的发送者
 * Created by macro on 2018/9/14.
 */
@Component
public class CancelOrderSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderSender.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId, UmsMember member, final long delayTimes){
        //给延迟队列发送消息
        Map<String, Object> messagePayload = new HashMap<>();
        messagePayload.put("orderId", orderId);
        messagePayload.put("username", member.getUsername());
        messagePayload.put("password", member.getPassword());
        messagePayload.put("nickname", member.getNickname());

        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), messagePayload, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            }
        });
        LOGGER.info("send orderId:{} with user info:{}", orderId, member.getUsername());
    }
}
