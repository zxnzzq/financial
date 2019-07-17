package com.zq.seller.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名服务
 */
@Service
public class SignService {
    static Map<String, String> PUBLIC_KEYS = new HashMap<>();
    static {
        //authId : 公钥
        PUBLIC_KEYS.put("1000", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDX4LKchQ28ykdt9cPxa4OLxkTX\n" +
                "C/XAY+PJS35cT5F1T4poVVrTEEOsGRcaQ+JUKrri1Brlvgdg+KqCDLb7CDuSrTkq\n" +
                "Ci+85yVVLi6hoRDa4D/gyqZRXsSEcCP4O6gpf1wVmq7JQQCO34rDAWgaEaUF0DWz\n" +
                "EpWTBy6P+IAndtaVwQIDAQAB");
    }
    /**
     * 根据授权编号authId获取公钥
     * @param authId
     * @return
     */
    public String getPublicKey(String authId){
        return PUBLIC_KEYS.get(authId);
    }
}
