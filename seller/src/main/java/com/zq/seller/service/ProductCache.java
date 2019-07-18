package com.zq.seller.service;

import com.hazelcast.core.HazelcastInstance;
import com.zq.api.ProductRpc;
import com.zq.api.domain.ProductRpcReq;
import com.zq.entity.Product;
import com.zq.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品缓存
 */
@Component
public class ProductCache {
    Logger LOG = LoggerFactory.getLogger(ProductCache.class);

    static final String CACHE_NAME = "zq_product";

    @Autowired
    private ProductRpc productRpc;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    /**
     * 读取缓存，缓存中有则走缓存，缓存中没有则走数据库
     * @param id
     * @return
     */
    @Cacheable(cacheNames = CACHE_NAME)
    public Product readCache(String id){
        LOG.info("rpc查询单个产品，请求：{}", id);
        Product result = productRpc.findOne(id);
        LOG.info("rpc查询单个产品，结果：{}", result);
        return result;
    }

    /**
     * 新增或更新缓存
     * 以product的id作为key,方法每次都执行返回值product作为value放入缓存中
     * @param product
     * @return
     */
    @CachePut(cacheNames = CACHE_NAME,key = "#product.id")
    public Product putCache(Product product){
        return product;
    }

    /**
     * 去缓存里面通过id去查询，如果有缓存数据就清除掉
     * @param id
     */
    @CacheEvict(cacheNames = CACHE_NAME)
    public void removeCache(String id){
    }

    public List<Product> readAllCache(){
        Map map = hazelcastInstance.getMap(CACHE_NAME);

        List<Product> products = null;
        if (map.size() > 0){
            products = new ArrayList<>();
            products.addAll(map.values());
        }else {
            products = findAll();
        }
        return products;
    }

    public List<Product> findAll(){
        ProductRpcReq req = new ProductRpcReq();
        List<String> status = new ArrayList<>();
        status.add(ProductStatus.AUDITING.name());
        req.setStatusList(status);
        LOG.info("rpc查询全部产品，请求参数：{}", req);
        List<Product> result = productRpc.query(req);
        LOG.info("rpc查询全部产品，请求结果：{}", result);
        return result;
    }
}