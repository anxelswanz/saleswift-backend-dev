package com.saleswift.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saleswift.entity.SwiftItems;
import com.saleswift.mapper.SwiftItemsMapper;
import com.saleswift.service.ISwiftItemsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ansel
 * @since 2023-08-31
 */
@Service
public class SwiftItemsServiceImpl extends ServiceImpl<SwiftItemsMapper, SwiftItems> implements ISwiftItemsService {

}
