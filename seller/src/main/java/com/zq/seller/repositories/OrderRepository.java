package com.zq.seller.repositories;

import com.zq.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * 订单管理
 */
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

    @Query(value = "select CONCAT_WS ('|',outer_order_id,chan_id,product_id,chan_user_id,order_type,order_id,amount,DATE_FORMAT(create_at,'%Y-%m-%d %H:%i:%s')) from order_t where order_status='success' AND chan_id=?1 AND create_at>=?2 AND create_at<?3", nativeQuery = true)
    List<String> queryOrders(String chanId, Date start, Date end);
}