package com.saleswift.controller.items;


import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saleswift.entity.SwiftFavourite;
import com.saleswift.entity.SwiftItems;
import com.saleswift.mapper.SwiftFavouriteMapper;
import com.saleswift.mapper.SwiftItemsMapper;
import com.saleswift.service.ISwiftFavouriteService;
import com.saleswift.service.ISwiftItemsService;
import com.saleswift.tool.UUIDName;
import com.saleswift.vo.RespBean;
import com.saleswift.vo.RespBeanEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ansel
 * @since 2023-08-31
 */
@RestController
@RequestMapping("/items")
@Api("SwiftItems")
public class SwiftItemsController {

    @Autowired
    private ISwiftItemsService itemsService;

    @Autowired
    private SwiftItemsMapper itemsMapper;
    @PostMapping("/save")
    @ApiOperation("save")
    public RespBean save(@RequestBody SwiftItems data){

        data.setItemId(UUIDName.getUUID());
        //设置时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        data.setReleaseDate(format);
        itemsService.save(data);
        return RespBean.success();
    }


    @GetMapping("/getByCategory")
    @ApiOperation("getByCategory")
    public RespBean getByCategory(String name){
        System.out.println("name "+ name);
        QueryWrapper<SwiftItems> wrapper = new QueryWrapper<>();
        wrapper.eq("category", name);
        List<SwiftItems> list = itemsService.list(wrapper);
        return RespBean.success(list);
    }

    String endpoint = "oss-eu-west-1.aliyuncs.com";
    String accessKeyId = "LTAI5tBSgLvf2NDpxSzsMbvy";
    String accessKeySecret = "1KZEpXgRldxBIUYrETpOA9tPXp2yo4";
    String bucketName = "project-livesgood-dev";

    @PostMapping("/upload")
    @ApiOperation("Upload photos")
    public RespBean upload(MultipartFile file) {
        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String filename = null;
        try {
            InputStream inputStream = file.getInputStream();
            filename = file.getOriginalFilename();
            client.putObject(bucketName,filename,inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            client.shutdown();
            //https://project-livesgood-dev.oss-eu-west-1.aliyuncs.com/babybed.png
            String filePath = "https://" + bucketName + "." + endpoint +  "/" + filename;
            return RespBean.success(filePath);
        }
    }
    //    @RequestMapping("upload")
//    public RespBean upload(MultipartFile file){
//
//        System.out.println(file);
//        //获取file名字
//        String fileName = file.getName() + System.currentTimeMillis() ;
//        System.out.println(fileName);
//        //获取类型
//        System.out.println(file.getContentType());
//        String type = file.getContentType();
//        String[] split = type.split("/");
//        //放入桶内
//        Random r = new Random();
//        int bucket = r.nextInt(10) + 1;
//        //根路径
//        String root = "D:\\java_framework_database\\react\\livegoods\\src\\assets\\post\\";
//        //图片位置路径
//        String beforePath = root + bucket;
//        File mkfile = new File(beforePath);
//        //如果没有文件夹就创建
//        if (!mkfile.exists()){
//            if (mkfile.mkdir()) {
//                System.out.println("文件夹创建成功" + " => " + bucket);
//            }else {
//                System.out.println("文件夹创建失败"+ " => " + bucket);
//            }
//        }
//        //最终路径
//        String filePath = beforePath + "\\" + fileName+ "."+split[1];
//
//        try {
//            FileOutputStream fos = new FileOutputStream(filePath);
//            fos.write(file.getBytes());
//            fos.flush();
//            fos.close();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//
//        String targetFile = "/items/" + fileName;
//        return RespBean.success(targetFile);
//    }

    @GetMapping("/getItemByItemId")
    @ApiOperation("getItemByItemId")
    public RespBean getItemByItemId(String itemId){
        QueryWrapper<SwiftItems> wrapper = new QueryWrapper<>();
        wrapper.eq("item_id", itemId);
        SwiftItems one = itemsService.getOne(wrapper);
        if (one != null) {
            return RespBean.success(one);
        }else {
            return RespBean.error(RespBeanEnum.ERROR);
        }
    }

    @Autowired
    private SwiftFavouriteMapper favouriteMapper;

    @Autowired
    private ISwiftFavouriteService favouriteService;
    @GetMapping("/ifAddFav")
    @ApiOperation("If the item is added")
    public RespBean ifAddFav(String userId, String itemId){
        QueryWrapper<SwiftFavourite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("item_id",itemId);
        SwiftFavourite fav = favouriteService.getOne(queryWrapper);
        if (!ObjectUtil.isEmpty(fav)){
            favouriteMapper.delete(queryWrapper);
            return RespBean.success(false);
        }else {
            SwiftFavourite swiftFavourite = new SwiftFavourite();
            swiftFavourite.setUserId(userId);
            swiftFavourite.setItemId(itemId);
            favouriteMapper.insert(swiftFavourite);
            return RespBean.success(true);
        }
    }

    @GetMapping("/favInit")
    @ApiOperation("If the item is added init")
    public RespBean favInit(String userId, String itemId){
        System.out.println(userId);
        System.out.println(itemId);
        QueryWrapper<SwiftFavourite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("item_id",itemId);
        SwiftFavourite fav = favouriteService.getOne(queryWrapper);
        System.out.println(fav);
        if (ObjectUtil.isEmpty(fav)){
            return RespBean.success(false);
        }else {
            return RespBean.success(true);
        }
    }


    @GetMapping("/getFavItems")
    public RespBean getFavItems(String userId){
        QueryWrapper<SwiftFavourite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<SwiftFavourite> list = favouriteMapper.selectList(queryWrapper);
        ArrayList<SwiftItems> items = new ArrayList<>();
        if (!list.isEmpty()){
            for (SwiftFavourite swiftFavourite : list) {
                QueryWrapper<SwiftItems> swiftItemsQueryWrapper = new QueryWrapper<>();
                swiftItemsQueryWrapper.eq("item_id", swiftFavourite.getItemId());
                SwiftItems one = itemsService.getOne(swiftItemsQueryWrapper);
                items.add(one);
            }
            return RespBean.success(items);
        }else {
            return RespBean.success(false);
        }
    }

    @GetMapping("/getMyPost")
    @ApiOperation("/show my post")
    public RespBean getMyPost(String userId){
        QueryWrapper<SwiftItems> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<SwiftItems> swiftItems = itemsMapper.selectList(queryWrapper);
        if (swiftItems.isEmpty()){
            return RespBean.success(false);
        }
        return RespBean.success(swiftItems);
    }


    @GetMapping("/deleteMyPost")
    @ApiOperation("/delete")
    public RespBean deleteMyPost(String itemId){
        QueryWrapper<SwiftItems> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id", itemId);
        int delete = itemsMapper.delete(queryWrapper);
        System.out.println("delete "+ delete);
        if (delete != 0) {
            return RespBean.success(true);
        }else {
            return RespBean.success(false);
        }

    }

}

