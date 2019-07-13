package com.zq.api;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.zq.api.domain.ProductRpcReq;
import com.zq.entity.Product;

import java.util.List;

/**
 * 产品相关prc服务
 */
@JsonRpcService("rpc/products")
public interface ProductRpc {
    /**
     * 查询多个产品
     * @param req
     * @return
     */
    List<Product> query(ProductRpcReq req);

    /**
     * 查询单个产品
     * @param id
     * @return
     */
    Product findOne(String id);
}