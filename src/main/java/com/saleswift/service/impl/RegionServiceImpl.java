package com.saleswift.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saleswift.entity.Region;
import com.saleswift.mapper.RegionMapper;
import com.saleswift.service.IRegionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ansel
 * @since 2023-08-23
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

}
