package com.saleswift.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * 公共返回对象枚举
 * @author Ansel Zhong
 * coding time
 *
 */
@ToString
@Getter
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"SERVICE FAILURE"),
    //商铺模块
    STORE_ERROR_DELETE(500100, "DELETE FAILURE"),
    STORE_ERROR_ADD(500101, "ADD FAILURE"),
    STORE_ERROR_MODIFY(500102, "MODIFY FAILURE"),
    STORE_ERROR_NULL(500103, "ESSENTIAL DATA CANNOT BE NULL"),
    //登录模块5002
    LOGIN_ERROR(500210,"用户名或密码错误"),
    MOBILE_ERROR(500211, "手机号格式不正确"),
    BIND_ERROR(500212,"参数校验异常"),
    MOBILE_NOT_EXIST(500213,"手机号码不存在"),
    SESSION_ERROR(500214,"用户不存在"),
    //秒杀模块 5005
    EMPTY_STOCK(500500,"库存不足"),
    REPEAT_ERROR(500501,"该商品每人限购一件"),

    //订单
    ORDER_NOT_EXIST(500300,"订单不存在"),
    ;
    /**
     * 状态码
     * 信息
     */
    private final Integer code;
    private final String message;
}
