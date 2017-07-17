package com.pieprzyca.dawid.skiapp;

/**
 * Created by Dawid on 16.07.2017.
 */

public class ResortData {
    private Integer skiResortId;
    private String resortName;
    private String resrtAddress;

    public ResortData() {
        this.skiResortId = 0;
        this.resortName = "";
        this.resrtAddress = "";
    }

    public ResortData(Integer skiResortId, String resortName, String resortAddress) {
        this.skiResortId = skiResortId;
        this.resortName = resortName;
        this.resrtAddress = resortAddress;
    }

    public void setSkiResortId(Integer skiResortId) {
        this.skiResortId = skiResortId;
    }

    public void setResortName(String resortName) {
        this.resortName = resortName;
    }

    public void setResortAddress(String resrtAddress) {
        this.resrtAddress = resrtAddress;
    }

    public Integer getSkiResortId() {
        return skiResortId;
    }

    public String getResortName() {
        return resortName;
    }

    public String getResortAddress() {
        return resrtAddress;
    }
}
