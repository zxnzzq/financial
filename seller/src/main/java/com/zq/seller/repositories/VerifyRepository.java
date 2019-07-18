package com.zq.seller.repositories;

import com.zq.entity.VerificationOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * 对账相关
 */
public interface VerifyRepository extends JpaRepository<VerificationOrder, String>, JpaSpecificationExecutor<VerificationOrder> {

    /**
     * 查询某一个渠道某一段时间[start,end)内的对账数据
     * @param chanId
     * @param start
     * @param end
     * @return 对账数据列表
     */
    @Query(value = "select CONCAT_WS ('|',order_id,chan_id,product_id,chan_user_id,order_type,outer_order_id,amount,DATE_FORMAT(create_at,'%Y-%m-%d %H:%i:%s')) from order_t where order_status='success' AND chan_id=?1 AND create_at>=?2 AND create_at<?3", nativeQuery = true)
    List<String> queryVerificationOrders(String chanId, Date start, Date end);

    /**
     * 查询长款订单
     * @param chanId
     * @param start
     * @param end
     * @return
     */
    @Query(value = "select t.order_id from order_t t left join verification_order v on t.order_id=v.outer_order_id where t.chan_id = ?1 and v.order_id is null and t.create_at>=?2 and t.create_at<?3", nativeQuery = true)
    List<String> queryExcessOrders(String chanId, Date start, Date end);

    /**
     * 查询漏单
     * @param chanId
     * @param start
     * @param end
     * @return
     */
    @Query(value = "select v.order_id from order_t t right join verification_order v on t.order_id=v.outer_order_id where v.chan_id = ?1 and t.order_id is null and v.create_at>=?2 and v.create_at<?3", nativeQuery = true)
    List<String> queryMissOrders(String chanId, Date start, Date end);

    /**
     * 查询不一致订单
     * @param chanId
     * @param start
     * @param end
     * @return
     */
    @Query(value = "select t.order_id from order_t t join verification_order v on t.order_id=v.outer_order_id where t.chan_id = ?1 and CONCAT_WS('|',t.chan_id,t.chan_user_id,t.product_id,t.order_type,t.amount,t.create_at) != CONCAT_WS('|',v.chan_id,v.chan_user_id,v.product_id,v.order_type,v.amount,v.create_at) and t.create_at>=?2 and t.create_at<?3", nativeQuery = true)
    List<String> queryDifferentOrders(String chanId, Date start, Date end);


}
