package com.renhejia.robot.launcher.ota;

import com.google.gson.annotations.SerializedName;

/**
 * Created by binzo on 2018/4/2.
 */

public class Data {

    @SerializedName("whole_package_url")
    private String wholeUrl;

    @SerializedName("md5")
    private String md5;

    @SerializedName("upgrade_desc")
    private String updateDesc;

    @SerializedName("byte_size")
    private long byteSize;

    @SerializedName("update_time")
    private long updateTime;

    @SerializedName("package_collection")
    private PackageCollectionModel packageCollectionModel;
    @SerializedName("version")
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getWholeUrl() {
        return wholeUrl;
    }

    public void setWholeUrl(String wholeUrl) {
        this.wholeUrl = wholeUrl;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUpdateDesc() {
        return updateDesc;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public long getByteSize() {
        return byteSize;
    }

    public void setByteSize(long byteSize) {
        this.byteSize = byteSize;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public PackageCollectionModel getPackageCollectionModel() {
        return packageCollectionModel;
    }

    public void setPackageCollectionModel(PackageCollectionModel packageCollectionModel) {
        this.packageCollectionModel = packageCollectionModel;
    }

}
