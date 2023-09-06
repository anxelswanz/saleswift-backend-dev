package com.saleswift.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ansel
 * @since 2023-09-06
 */
@TableName("swift_order")
public class SwiftOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private String itemId;

    /**
     * 商品名字
     */
    private String itemName;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单时间
     */
    private String orderTime;

    /**
     * 买家
     */
    private String userId;

    /**
     * 卖家
     */
    private String sellerId;

    /**
     * 买家姓名
     */
    private String username;

    /**
     * 卖家姓名
     */
    private String sellerName;

    /**
     * 是否邮寄
     */
    private String ifDelivery;

    /**
     * 商品价格
     */
    private double price;

    /**
     * 商品数量
     */
    private Integer itemCount;

    /**
     * 总价
     */
    private String totalPrice;

    /**
     * 发货地址
     * @return
     */
    private String sendAddress;

    /**
     * 收货地址
     * @return
     */

    private String receiveAddress;

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
    public String getIfDelivery() {
        return ifDelivery;
    }

    public void setIfDelivery(String ifDelivery) {
        this.ifDelivery = ifDelivery;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "SwiftOrder{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", userId='" + userId + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", username='" + username + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", ifDelivery='" + ifDelivery + '\'' +
                ", price=" + price +
                ", itemCount=" + itemCount +
                ", totalPrice='" + totalPrice + '\'' +
                ", sendAddress='" + sendAddress + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                '}';
    }
}
