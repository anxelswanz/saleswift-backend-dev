package com.saleswift.controller.user;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.saleswift.entity.SwiftFollow;
import com.saleswift.entity.SwiftUser;
import com.saleswift.mapper.SwiftFollowMapper;
import com.saleswift.mapper.SwiftUserMapper;
import com.saleswift.service.ISwiftFollowService;
import com.saleswift.service.ISwiftUserService;
import com.saleswift.tool.MD5Crypt;
import com.saleswift.vo.RespBean;
import com.saleswift.vo.RespBeanEnum;
import freemarker.template.utility.StringUtil;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ISwiftUserService userService;

    @Autowired
    private SwiftFollowMapper followMapper;

    @Autowired
    private ISwiftFollowService followService;

    @RequestMapping("/getAll")
    public RespBean getALl(){
        List<SwiftUser> list = userService.list();
        return RespBean.success(list);
    }

    /**
     *     获取用户
     */

    @RequestMapping("/getUser")
    public RespBean getUser(String username, String password){

        QueryWrapper<SwiftUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        SwiftUser user = userService.getOne(wrapper);
        //判断用户是否存在
        if (StrUtil.isBlank(username)){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if (user == null) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        //校验密码
        if (!password.equals(user.getPassword())){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }

        //日期 距离今天多少天
        String joinDay = user.getJoinDay();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date jd = sdf.parse(joinDay);
            Date now = new Date();
            long d = (now.getTime() - jd.getTime()) /24/60/60/1000;
            int days = (int) d;
            user.setDays(days);
            System.out.println("days=>"+days);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println(user);
        return RespBean.success(user);
    }

    /**
     * 保存uni code
     */

    @RequestMapping("/storecode")
    public RespBean storecode(String userid, String code) {
        System.out.println("code=>" + code); //e10adc3949ba59abbe56e057f20f883e
                                             //e10adc3949ba59abbe56e057f20f883e
        //$2a$10$s7/TrRmZBlgw/p74dvmpyONt7.JUn/1ndmSZD6fFFLNUreP/jbUyK
        String encode = MD5Crypt.encode(code);
        UpdateWrapper<SwiftUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id",userid);
        wrapper.set("code", encode);
        userService.update(wrapper);
        return RespBean.success(RespBeanEnum.SUCCESS);
    }

    /**
     * 验证uni code
     */

    @RequestMapping("/verifycode")
    public RespBean verifyCode(String userid, String code) {
        //通过userid查询
        QueryWrapper<SwiftUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userid);
        SwiftUser one = userService.getOne(wrapper);
        String originalCode = one.getCode();
        boolean res = MD5Crypt.match(code, originalCode);
        return RespBean.success(res);
    }


    @RequestMapping("/follow")
    public RespBean follow(String userId, String followId){

        QueryWrapper<SwiftFollow> swiftFollowQueryWrapper = new QueryWrapper<>();
        swiftFollowQueryWrapper.eq("following_id",followId);
        swiftFollowQueryWrapper.eq("user_id", userId);
        SwiftFollow one = followService.getOne(swiftFollowQueryWrapper);
        //如果查找不到就插入一条
        if (ObjectUtil.isEmpty(one)){
            SwiftFollow swiftFollow = new SwiftFollow();
            swiftFollow.setUserid(userId);
            swiftFollow.setFollowingId(followId);
            followMapper.insert(swiftFollow);
                //同时被关注的人的粉丝要加一条
                SwiftFollow followedPerson = new SwiftFollow();
                followedPerson.setUserid(followId);
                followedPerson.setFollowerId(userId);
                followMapper.insert(followedPerson);
                    //user需要加一
                    QueryWrapper<SwiftUser> userWrapper1 = new QueryWrapper<>();
                    userWrapper1.eq("user_id",userId);
                    SwiftUser user = userService.getOne(userWrapper1);
                    if (ObjectUtil.isEmpty(user)){
                        return RespBean.error(RespBeanEnum.ERROR);
                    }
                    // - 总关注人数
                    int totalFollowing = user.getFollowing();
                    int newTotalFollowing = totalFollowing + 1;
                    UpdateWrapper<SwiftUser> updateWrapper1 = new UpdateWrapper<>();
                    updateWrapper1.eq("user_id",userId);
                    updateWrapper1.set("following",newTotalFollowing);
                    userService.update(updateWrapper1);
                    //被关注的人粉丝需要+1
                    QueryWrapper<SwiftUser> userWrapper2 = new QueryWrapper<>();
                    userWrapper2.eq("user_id",followId);
                    SwiftUser followUser = userService.getOne(userWrapper2);
                    if (ObjectUtil.isEmpty(followUser)){
                        return RespBean.error(RespBeanEnum.ERROR);
                    }
                    int totalFollowers = followUser.getFollowers();
                    int newTotalFollowers = totalFollowers + 1;
                    UpdateWrapper<SwiftUser> updateWrapper2 = new UpdateWrapper<>();
                    updateWrapper2.eq("user_id",followId);
                    updateWrapper2.set("followers",newTotalFollowers);
                    userService.update(updateWrapper2);
                    return RespBean.success();
        }else{
            //如果查找到了就删除
            QueryWrapper<SwiftFollow> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("user_id",userId);
            wrapper2.eq("following_id",followId);
            followMapper.delete(wrapper2);
            //被关注的人也删除
            QueryWrapper<SwiftFollow> wrapper3 = new QueryWrapper<>();
            wrapper3.eq("user_id",followId);
            wrapper3.eq("follower_id",userId);
            followMapper.delete(wrapper3);
                //关注的人的关注总数-1
                QueryWrapper<SwiftUser> userWrapper1 = new QueryWrapper<>();
                userWrapper1.eq("user_id",userId);
                SwiftUser user = userService.getOne(userWrapper1);
                if (ObjectUtil.isEmpty(user)){
                    return RespBean.error(RespBeanEnum.ERROR);
                }
                int following = user.getFollowing();
                int newTotalFollowing = following - 1;
                UpdateWrapper<SwiftUser> updateWrapper1 = new UpdateWrapper<>();
                updateWrapper1.eq("user_id",userId);
                updateWrapper1.set("following",newTotalFollowing);
                userService.update(updateWrapper1);
                //被关注的人粉丝数-1
                QueryWrapper<SwiftUser> userWrapper2 = new QueryWrapper<>();
                userWrapper2.eq("user_id",followId);
                SwiftUser followUser = userService.getOne(userWrapper2);
                if (ObjectUtil.isEmpty(followUser)){
                    return RespBean.error(RespBeanEnum.ERROR);
                }
                int totalFollowers = followUser.getFollowers();
                int newTotalFollowers = totalFollowers - 1;
                UpdateWrapper<SwiftUser> updateWrapper2 = new UpdateWrapper<>();
                updateWrapper2.eq("user_id",followId);
                updateWrapper2.set("followers",newTotalFollowers);
                userService.update(updateWrapper2);
                return RespBean.success();
        }
    }

    @GetMapping("/getFollowers")
    public RespBean getFollowers(String userId){
        QueryWrapper<SwiftFollow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<SwiftFollow> swiftFollows = followMapper.selectList(queryWrapper);
        List<SwiftFollow> targetList = new ArrayList<>();
        targetList = swiftFollows.stream().filter(follow -> {
            return follow.getFollowingId() == null;
        }).collect(Collectors.toList());
        System.out.println("target => " + targetList);
        List<SwiftUser> users = new ArrayList<>();
        if (!ObjectUtil.isEmpty(targetList)){
            for (SwiftFollow swiftFollow : targetList) {
                QueryWrapper<SwiftUser> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("user_id", swiftFollow.getFollowerId());
                SwiftUser one = userService.getOne(userQueryWrapper);
                users.add(one);
            }
        }
        return RespBean.success(users);
    }

    @GetMapping("/getFollowing")
    public RespBean getFollowing(String userId){
        QueryWrapper<SwiftFollow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<SwiftFollow> swiftFollows = followMapper.selectList(queryWrapper);
        List<SwiftFollow> targetList = new ArrayList<>();
        targetList = swiftFollows.stream().filter(follow -> {
            return follow.getFollowerId() == null;
        }).collect(Collectors.toList());
        System.out.println(targetList);
        List<SwiftUser> users = new ArrayList<>();
        if (!ObjectUtil.isEmpty(targetList)){
            for (SwiftFollow swiftFollow : targetList) {
                QueryWrapper<SwiftUser> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("user_id", swiftFollow.getFollowingId());
                SwiftUser one = userService.getOne(userQueryWrapper);
                users.add(one);
            }
        }
        System.out.println(users);
        return RespBean.success(users);
    }


    @GetMapping("/getUserById")
    public RespBean getUserById(String userId){
        QueryWrapper<SwiftUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        SwiftUser one = userService.getOne(wrapper);
        return RespBean.success(one);
    }

    //判断是否关注
    @GetMapping("/ifFollow")
    public RespBean ifFollow(String userId, String followId){
        if (StrUtil.isAllBlank(userId,followId)){
            return RespBean.error(RespBeanEnum.ERROR);
        }
        QueryWrapper<SwiftFollow> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("following_id", followId);
        SwiftFollow ifFollow = followService.getOne(wrapper);
        if (!ObjectUtil.isEmpty(ifFollow)){
            return RespBean.success(true);
        }else {
            return RespBean.success(false);
        }
    }
}


//class TestDate {
//    public static void main(String[] args) throws ParseException {
//        String date1 = "2023-09-03";
//        Date date2 = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date d1 = sdf.parse(date1);
//        System.out.println((date2.getTime() - d1.getTime())/24/60/60/1000);
//    }
//}

