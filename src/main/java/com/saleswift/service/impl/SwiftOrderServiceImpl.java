package com.saleswift.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saleswift.entity.SwiftOrder;
import com.saleswift.entity.SwiftUser;
import com.saleswift.mapper.SwiftOrderMapper;
import com.saleswift.mapper.SwiftUserMapper;
import com.saleswift.service.ISwiftItemsService;
import com.saleswift.service.ISwiftOrderService;
import com.saleswift.service.ISwiftUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ansel
 * @since 2023-09-06
 */
@Service
public class SwiftOrderServiceImpl extends ServiceImpl<SwiftOrderMapper, SwiftOrder> implements ISwiftOrderService {

    @Autowired
    private ISwiftUserService swiftUserService;

    @Autowired
    private SwiftUserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int makePayment(String userId, Double price) {
        QueryWrapper<SwiftUser> swiftUserQueryWrapper = new QueryWrapper<>();
        swiftUserQueryWrapper.eq("user_id",userId);
        SwiftUser user = swiftUserService.getOne(swiftUserQueryWrapper);
        double balance = user.getBalance();
        if (balance < price) {
            return 0;
        }
        double newBalance = balance - price;
        user.setBalance(newBalance);
        int insert = 0;
        try {
            UpdateWrapper<SwiftUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", userId);
            insert = userMapper.update(user,updateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (insert != 0){
            return 1;
        }else {
            return 0;
        }
    }
}
