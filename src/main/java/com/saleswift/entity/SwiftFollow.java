package com.saleswift.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ansel
 * @since 2023-09-03
 */
@TableName("swift_follow")
public class SwiftFollow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 关注的人id
     */
    private String followingId;

    /**
     * 关注你的人的id
     */
    private String followerId;

    public String getUserid() {
        return userId;
    }

    public void setUserid(String userid) {
        this.userId = userid;
    }
    public String getFollowingId() {
        return followingId;
    }

    public void setFollowingId(String followingId) {
        this.followingId = followingId;
    }
    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    @Override
    public String toString() {
        return "SwiftFollow{" +
            "userid=" + userId +
            ", followingId=" + followingId +
            ", followerId=" + followerId +
        "}";
    }
}
