package com.saleswift.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ansel
 * @since 2023-08-31
 */
@TableName("swift_items")
public class SwiftItems implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * item name
     */
    private String name;

    /**
     * item price
     */
    private Double price;

    /**
     * item address
     */
    private String address;

    /**
     * item city
     */
    private String city;

    /**
     * item photo path
     */
    private String filepath;

    /**
     * item id
     */
    private String itemId;

    /**
     * description
     */
    private String description;

    /**
     * category
     */
    private String category;

    /**
     * 用户id
     * @return
     */
    private String userId;

    /**
     * 用户名
     * @return
     */
    private String username;

    /**
     * 发布日期
     * @return
     */
    private String releaseDate;

    /**
     * 是否出售
     * @return
     */
    private int ifSaled;

    public int getIfSaled() {
        return ifSaled;
    }

    public void setIfSaled(int ifSaled) {
        this.ifSaled = ifSaled;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "SwiftItems{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", filepath='" + filepath + '\'' +
                ", itemId='" + itemId + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", ifSaled=" + ifSaled +
                '}';
    }
}
