/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author RAI
 */
public class Region {
    private int regionId, count;
    private String regionName;

    public Region(String regionName, int count) {
        this.regionName = regionName;
        this.count = count;
    }

    public Region(int regionId, String regionName, int count) {
        this(regionName, count);
        this.regionId = regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
    
    public int getRegionId() {
        return regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Region{" + "regionId=" + regionId + ", count=" + count + ", regionName=" + regionName + '}';
    }

    
    
}
