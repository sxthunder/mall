package com.macro.mall.portal.component;

import com.macro.mall.portal.domain.OrderParam;
import com.macro.mall.portal.domain.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单创建消息的发送者
 * Created by macro on 2023/04/01.
 */
@Component
public class OrderCreationSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreationSender.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(OrderParam orderParam, Long memberId) {
        OrderCreationMessage message = new OrderCreationMessage();
        message.setOrderParam(orderParam);
        message.setMemberId(memberId);
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_CREATE.getExchange(), QueueEnum.QUEUE_ORDER_CREATE.getRouteKey(), message);
        LOGGER.info("send order creation message for memberId: {}", memberId);
    }

    public static class OrderCreationMessage {
        private OrderParam orderParam;
        private Long memberId;

        public OrderParam getOrderParam() {
            return orderParam;
        }

        public void setOrderParam(OrderParam orderParam) {
            this.orderParam = orderParam;
        }

        public Long getMemberId() {
            return memberId;
        }

        public void setMemberId(Long memberId) {
            this.memberId = memberId;
        }
    }
}
