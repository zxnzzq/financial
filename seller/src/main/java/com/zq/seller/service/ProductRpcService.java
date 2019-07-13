package com.zq.seller.service;

import com.zq.api.ProductRpc;
import com.zq.api.domain.ProductRpcReq;
import com.zq.entity.Product;
import com.zq.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品相关服务
 */
@Service
public class ProductRpcService {
    private static Logger LOG = LoggerFactory.getLogger(ProductRpcService.class);

    @Autowired
    private ProductRpc productRpc;

    /**
     * 查询全部产品
     * @return
     */
    public List<Product> findAll(){
        ProductRpcReq req = new ProductRpcReq();
        List<String> status = new ArrayList<>();
        status.add(ProductStatus.AUDITING.name());
        req.setStatusList(status);
        LOG.info("rpc查询全部产品，请求：{}", req);
        List<Product> result = productRpc.query(req);
        LOG.info("rpc查询全部产品，结果：{}", result);
        return result;
    }

    @PostConstruct
    public void testFindAll(){
        findAll();
    }
}
