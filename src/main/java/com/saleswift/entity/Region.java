package com.saleswift.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ansel
 * @since 2023-08-23
 */
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * RegionName
     */
    private String pid;

    /**
     * cityName
     */
    private String region;

    /**
     * cityId
     */
    private String regionId;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        return "Region{" +
            "pid=" + pid +
            ", region=" + region +
            ", regionId=" + regionId +
        "}";
    }
}
