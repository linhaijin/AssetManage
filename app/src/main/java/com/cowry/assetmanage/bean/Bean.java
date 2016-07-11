package com.cowry.assetmanage.bean;

/**
 * Created by acer on 2016/7/3.
 */
public class Bean {
    /**资产编号*/
    private String bId;
    /**资产名称*/
    private String bName;
    /**资产图片*/
    private int bImg;
    /**资产类型*/
    private String bType;
    /**资产品牌*/
    private String bBrand;
    /**入库时间*/
    private long bInTime;
    /**数量*/
    private String bAmount;
    /**使用部门*/
    private String bDept;
    /**使用状态*/
    private String bStatus;
    /**登记日期*/
    private String bRegeistTime;
    /**登记人*/
    private String bRegeistMan;
    /**使用人*/
    private String bUser;

    public String getbUser() {
        return bUser;
    }

    public void setbUser(String bUser) {
        this.bUser = bUser;
    }

    public int getbImg() {
        return bImg;
    }

    public void setbImg(int bImg) {
        this.bImg = bImg;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbType() {
        return bType;
    }

    public void setbType(String bType) {
        this.bType = bType;
    }

    public String getbBrand() {
        return bBrand;
    }

    public void setbBrand(String bBrand) {
        this.bBrand = bBrand;
    }

    public long getbInTime() {
        return bInTime;
    }

    public void setbInTime(long bInTime) {
        this.bInTime = bInTime;
    }

    public String getbAmount() {
        return bAmount;
    }

    public void setbAmount(String bAmount) {
        this.bAmount = bAmount;
    }

    public String getbDept() {
        return bDept;
    }

    public void setbDept(String bDept) {
        this.bDept = bDept;
    }

    public String getbStatus() {
        return bStatus;
    }

    public void setbStatus(String bStatus) {
        this.bStatus = bStatus;
    }

    public String getbRegeistTime() {
        return bRegeistTime;
    }

    public void setbRegeistTime(String bRegeistTime) {
        this.bRegeistTime = bRegeistTime;
    }

    public String getbRegeistMan() {
        return bRegeistMan;
    }

    public void setbRegeistMan(String bRegeistMan) {
        this.bRegeistMan = bRegeistMan;
    }
}
