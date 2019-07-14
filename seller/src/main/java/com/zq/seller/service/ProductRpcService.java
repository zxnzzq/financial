package com.zq.seller.service;

import com.zq.api.ProductRpc;
import com.zq.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ProductRpcService implements ApplicationListener<ContextRefreshedEvent> {
    Logger LOG = LoggerFactory.getLogger(ProductRpcService.class);
    @Autowired
    private ProductRpc productRpc;

    @Autowired
    private ProductCache productCache;

    /**
     * 查询全部产品
     * @return
     */
    public List<Product> findAll(){
        return productCache.readAllCache();
    }

    public Product findOne(String id){
        Product product = productCache.readCache(id);
        if (product == null){
            productCache.removeCache(id);
        }
        return product;
    }

    @PostConstruct
    public void init(){
//        findOne("001");
        findAll();
    }

    /**
     * 容器初始化完之后触发的事件：初始化缓存
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<Product> products = findAll();
        products.forEach(product -> {
            productCache.putCache(product);
        });
    }
}