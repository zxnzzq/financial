package com.zq.seller;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class HazelcastMapTest {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @PostConstruct
    public void put(){
        //分布式的，也就是可能在任意节点上的，多个节点上的内存就可以当做一个节点来使用
        Map map = hazelcastInstance.getMap("bawei");
        map.put("name", "bawei");
    }
}
