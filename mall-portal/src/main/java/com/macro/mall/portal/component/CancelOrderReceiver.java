package com.macro.mall.portal.component;

import com.macro.mall.portal.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 取消订单消息的接收者
 * Created by macro on 2018/9/14.
 */
@Component
@RabbitListener(queues = "mall.order.cancel")
public class CancelOrderReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderReceiver.class);
    @Autowired
    private OmsPortalOrderService portalOrderService;

    @RabbitHandler
    public void handle(Map<String, Object> messagePayload) {
        Long orderId = (Long) messagePayload.get("orderId");
        String username = (String) messagePayload.get("username");
        String password = (String) messagePayload.get("password");
        String nickname = (String) messagePayload.get("nickname");

        portalOrderService.cancelOrder(orderId);
        LOGGER.info("process orderId:{} with user info:{}", orderId, username);
    }
}
