package com.saleswift.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saleswift.entity.SwiftUser;
import com.saleswift.mapper.SwiftUserMapper;
import com.saleswift.service.ISwiftUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ansel
 * @since 2023-08-28
 */
@Service
public class SwiftUserServiceImpl extends ServiceImpl<SwiftUserMapper, SwiftUser> implements ISwiftUserService {

}
