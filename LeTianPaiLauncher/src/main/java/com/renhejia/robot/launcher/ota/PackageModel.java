package com.renhejia.robot.launcher.ota;

import com.google.gson.annotations.SerializedName;

public class PackageModel {

    @SerializedName("is_update")
    private String isUpdate;
    @SerializedName("rom_package_url")
    private String url;

    @SerializedName("version")
    private String version;

    @SerializedName("byte_size")
    private long byteSize;

    @SerializedName("md5")
    private String md5;

    @SerializedName("update_time")
    private long updateTime;

    @SerializedName("upgrade_desc")
    private String updateDesc;


    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getByteSize() {
        return byteSize;
    }

    public void setByteSize(long byteSize) {
        this.byteSize = byteSize;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateDesc() {
        return updateDesc;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public String getUrl() {
        String replaced = url.replace("\u0026", "&");
        return replaced;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("    data: {\n");
        sb.append("        isUpdate: ").append(isUpdate).append("\n");
        sb.append("        url: ").append(url).append("\n");
        sb.append("        version: ").append(version).append("\n");
        sb.append("        size: ").append(byteSize).append("\n");
        sb.append("        sign: ").append(md5).append("\n");
        sb.append("    }\n");
        return sb.toString();
    }
}
