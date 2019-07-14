package com.zq.manager.service;

import com.zq.api.events.ProductStatusEvent;
import com.zq.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * 产品状态管理
 */
@Component
public class ProductStatusManager {
    static Logger LOG = LoggerFactory.getLogger(ProductStatusManager.class);
    static final String MQ_DESTINATION = "VirtualTopic.PRODUCT_STATUS";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void changeStatus(String id, ProductStatus status){
        ProductStatusEvent event = new ProductStatusEvent(id, status);
        LOG.info("send message:{}", event);
        jmsTemplate.convertAndSend(MQ_DESTINATION, event);
    }
}
