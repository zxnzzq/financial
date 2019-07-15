package com.zq.seller.repositories;

import com.zq.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 订单管理
 */
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

}