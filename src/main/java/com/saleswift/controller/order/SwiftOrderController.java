package com.saleswift.controller.order;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.saleswift.entity.SwiftItems;
import com.saleswift.entity.SwiftOrder;
import com.saleswift.entity.SwiftUser;
import com.saleswift.mapper.SwiftItemsMapper;
import com.saleswift.mapper.SwiftOrderMapper;
import com.saleswift.mapper.SwiftUserMapper;
import com.saleswift.service.ISwiftItemsService;
import com.saleswift.service.ISwiftOrderService;
import com.saleswift.service.ISwiftUserService;
import com.saleswift.tool.UUIDName;
import com.saleswift.vo.RespBean;
import com.saleswift.vo.RespBeanEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ansel
 * @since 2023-09-06
 */
@RestController
@RequestMapping("/order")
public class SwiftOrderController {


    @Autowired
    private ISwiftItemsService itemsService;

    @Autowired
    private ISwiftUserService userService;


    @Autowired
    private ISwiftOrderService orderService;

    @Autowired
    private SwiftOrderMapper orderMapper;

    @Autowired
    private SwiftItemsMapper swiftItemsMapper;

    @GetMapping("/createOrder")
    @ApiOperation("Create Order")
    public RespBean order(String itemId, String userId){


        //通过itemId获取item
        QueryWrapper<SwiftItems> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id", itemId);
        SwiftItems item = itemsService.getOne(queryWrapper);
        Double price =null;
        String itemName = null;
        String sendAddress = null;
        String city = null;
        String sellerId = null;
        String address = null;
        if (!ObjectUtil.isEmpty(item)){
            itemName = item.getName();
            address = item.getAddress();
            price = item.getPrice();
            city = item.getCity();
            sellerId = item.getUserId();
            sendAddress = city + " " + address;
        }else {
            System.out.println("商品为空");
            return RespBean.error(RespBeanEnum.ERROR);
        }


        //进行扣减
        int result =0;
        try {
           result = orderService.makePayment(userId,price);
           if (result == 0) {
               throw new RuntimeException();
           }
            System.out.println("扣减成功");
        } catch (Exception e) {
            System.out.println("扣减失败");
            return RespBean.error(RespBeanEnum.ERROR);
        }


        //获取卖家信息
        QueryWrapper<SwiftUser> sellerWrapper = new QueryWrapper<>();
        sellerWrapper.eq("user_id", sellerId);
        SwiftUser seller = userService.getOne(sellerWrapper);
        String sellerName = seller.getName();


        //获取用户信息
        QueryWrapper<SwiftUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_id", userId);
        System.out.println("用户id"+userId);
        SwiftUser user = userService.getOne(userQueryWrapper);
        String username = user.getName();
        String receiveAddress = user.getReceiveAddress();


        //生成订单
        SwiftOrder order = new SwiftOrder();
        order.setItemId(itemId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        String time = sdf.format(date);
        order.setOrderTime(time);
        order.setIfDelivery("No");
        order.setPrice(price);
        order.setItemCount(1);
        order.setSellerId(sellerId);
        order.setSellerName(sellerName);
        order.setUsername(username);
        order.setUserId(userId);
        order.setTotalPrice(price+"");
        order.setItemId(itemId);
        order.setItemName(itemName);
        order.setReceiveAddress(receiveAddress);
        order.setSendAddress(sendAddress);
        String orderId = UUIDName.getUUID() + System.currentTimeMillis();
        order.setOrderId(orderId);

        int insert = orderMapper.insert(order);


        //商品标记以出售
        UpdateWrapper<SwiftItems> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("item_id",itemId);
        item.setIfSaled(1);
        swiftItemsMapper.update(item,updateWrapper);
        System.out.println("更新商品卖出信息");
        if (insert != 0 && result == 1 ) {
            System.out.println("成功生成订单");
            return RespBean.success(order);
        }else {
            System.out.println("订单生成失败"+ "订单"+ insert +"扣减结果"+ result);
            return RespBean.error(RespBeanEnum.ERROR);
        }

    }
}
