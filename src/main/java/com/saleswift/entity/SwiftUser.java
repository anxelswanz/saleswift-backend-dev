package com.saleswift.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ansel
 * @since 2023-08-28
 */
@TableName("swift_user")
public class SwiftUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 被关注
     */

    private int followers;

    /**
     * 关注
     */
    private int following;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 余额
     */
    private double balance;

    /**
     * 收藏
     */

    private String favGoodsId;


    /**
     * 支付密码
     * @return
     */
    private String code;

    /**
     * 加入日期
     * @return
     */

    /**
     * 天数
     * @return
     */
    @TableField(exist = false)
    private Integer days;


    private String joinDay;

    /**
     * 收货地址
     * @return
     */

    private String receiveAddress;

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }



    public String getJoinDay() {
        return joinDay;
    }

    public void setJoinDay(String joinDay) {
        this.joinDay = joinDay;
    }

    public String getFavGoodsId() {
        return favGoodsId;
    }

    public void setFavGoodsId(String favGoodsId) {
        this.favGoodsId = favGoodsId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "SwiftUser{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", userName='" + userName + '\'' +
                ", balance=" + balance +
                ", favGoodsId='" + favGoodsId + '\'' +
                ", code='" + code + '\'' +
                ", days=" + days +
                ", joinDay='" + joinDay + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                '}';
    }
}
