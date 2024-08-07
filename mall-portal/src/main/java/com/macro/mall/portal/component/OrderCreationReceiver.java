package com.macro.mall.portal.component;

import com.macro.mall.portal.domain.OrderParam;
import com.macro.mall.portal.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单创建消息的接收者
 * Created by macro on 2023/04/01.
 */
@Component
@RabbitListener(queues = "mall.order.create")
public class OrderCreationReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreationReceiver.class);
    @Autowired
    private OmsPortalOrderService portalOrderService;

    @RabbitHandler
    public void handle(OrderCreationMessage message) {
        OrderParam orderParam = message.getOrderParam();
        Long memberId = message.getMemberId();
        portalOrderService.handleOrderCreation(orderParam, memberId);
        LOGGER.info("Order creation processed for memberId: {}", memberId);
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
