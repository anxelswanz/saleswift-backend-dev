package com.saleswift.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saleswift.entity.SwiftOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ansel
 * @since 2023-09-06
 */
public interface ISwiftOrderService extends IService<SwiftOrder> {


    int makePayment(String userId, Double price);
}
