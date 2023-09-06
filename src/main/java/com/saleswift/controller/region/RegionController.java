package com.saleswift.controller.region;


import com.saleswift.entity.Region;
import com.saleswift.service.IRegionService;
import com.saleswift.vo.RespBean;
import com.saleswift.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private IRegionService regionService;

    @GetMapping("/getList")
    public RespBean getRegions(){
        List<Region> list = regionService.list();
        return RespBean.success(list);
    }

    @RequestMapping("/test")
    public String test(){

        return "OK";
    }
}
