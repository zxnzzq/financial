package com.zq.seller.controller;

import com.zq.entity.Order;
import com.zq.seller.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    private Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Order apply(@RequestBody Order order){
        LOG.info("申购请求：{}", order);
        order = orderService.apply(order);
        LOG.info("申购结果：{}", order);
        return order;
    }
}
