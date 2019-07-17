package com.zq.seller.sign;

import com.zq.seller.service.SignService;
import com.zq.util.RSAUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 验签aop
 */
@Component
@Aspect
public class SignAop {
    Logger LOG = LoggerFactory.getLogger(SignAop.class);
    @Autowired
    public SignService signService;

    //限制为 任何返回值 com.bawei.seller.controller目录下的任何类的任何方法的任意参数并且参数限制为(authId,sign,text,以及任何参数)
    @Before(value = "execution(* com.zq.seller.controller.*.*(..)) && args(authId,sign,text,..)")
    public void verify(String authId, String sign, SignText text){
        LOG.info("拦截请求，authId:{},sign:{},text:{}", authId,sign,text.toText());
        String publicKey = signService.getPublicKey(authId);
        LOG.info("拦截请求，获取到的公钥:{}", publicKey);
        Assert.isTrue(RSAUtil.verify(text.toText(),sign,publicKey), "验签失败");
    }
}