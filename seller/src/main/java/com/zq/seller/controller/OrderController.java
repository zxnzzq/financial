package com.zq.seller.controller;

import com.zq.entity.Order;
import com.zq.seller.params.OrderParam;
import com.zq.seller.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    private Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Order apply(@RequestHeader String authId, @RequestHeader String sign, @RequestBody OrderParam param){
        LOG.info("申购请求：{}", param);
        Order order = new Order();
        BeanUtils.copyProperties(param, order);
        order = orderService.apply(order);
        LOG.info("申购结果：{}", order);
        return order;
    }
}
