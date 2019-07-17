package com.zq.seller.sign;

import com.zq.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 签名明文
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public interface SignText {
    //生成明文字符串
    default String toText(){
        return JsonUtil.toJson(this);
    }
}
