package com.zq.seller.service;

import com.zq.entity.Order;
import com.zq.entity.Product;
import com.zq.entity.enums.OrderStatus;
import com.zq.entity.enums.OrderType;
import com.zq.seller.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRpcService productRpcService;
    /**
     * 订单申购
     * @return
     */
    public Order apply(Order order){
        //数据校验
        checkOrder(order);
        //完善订单数据
        completeOrder(order);

        order = orderRepository.saveAndFlush(order);
        return order;
    }

    /**
     * 完善订单数据
     * @param order
     */
    private void completeOrder(Order order) {
        order.setOrderId(UUID.randomUUID().toString().replaceAll("-",""));
        order.setOrderType(OrderType.APPLY.name());
        order.setOrderStatus(OrderStatus.SUCCESS.name());
        order.setUpdateAt(new Date());
    }

    /**
     * 校验数据
     * @param order
     */
    private void checkOrder(Order order) {
        //必填字段
        Assert.notNull(order.getOuterOrderId(), "需要外部订单编号");
        Assert.notNull(order.getChanId(), "需要渠道编号");
        Assert.notNull(order.getChanUserId(), "需要用户编号");
        Assert.notNull(order.getProductId(), "需要产品编号");
        Assert.notNull(order.getAmount(), "需要购买金额");
        Assert.notNull(order.getCreateAt(), "需要订单时间");
        //产品是否存在及金额是否符合要求
        Product product = productRpcService.findOne(order.getProductId());
        Assert.notNull(product, "产品不存在");
        //金额要满足有起投金额时，要大于等于起投金额，如果有投资步长时，超过起投金额的部分要是投资步长的整数倍
        Assert.isTrue(order.getAmount().compareTo(product.getThresholdAmount()) > 0, "金额要大于等于起投金额");
//        BigDecimal amount = order.getAmount();
//        BigDecimal stepAmount = product.getStepAmount();
//        BigDecimal thresholdAmount = product.getThresholdAmount();
//        BigDecimal divide = amount.divide(thresholdAmount);
//        System.out.println(amount);
//        System.out.println(stepAmount);
//        System.out.println(thresholdAmount);
//        System.out.println(divide);
//        BigDecimal subtract = amount.subtract(thresholdAmount);
//        System.out.println(subtract);
//        BigDecimal[] bigDecimals = subtract.divideAndRemainder(stepAmount);
//        for (int i = 0; i < bigDecimals.length; i++) {
//            System.out.println(bigDecimals[i]);
//        }
        Assert.isTrue(order.getAmount().subtract(product.getThresholdAmount()).divideAndRemainder(product.getStepAmount())[1].intValue()==0,
                "超过起投金额的部分要是投资步长的整数倍"  );
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("readOrderRepository")
    private OrderRepository readOrderRepository;
    @PostConstruct
    public void queryOrder() throws IllegalAccessException, InstantiationException {
        Class c = readOrderRepository.getClass();
        OrderRepository o = (OrderRepository) c.newInstance();
        System.out.println("输出");
        System.out.println(orderRepository.findAll());
        System.out.println(readOrderRepository.findAll());
    }

}
