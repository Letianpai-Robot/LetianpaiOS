package com.renhejia.robot.launcher.ota;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class PackageCollectionModel {
    @SerializedName("rom_package")
    PackageModel romPackage;

    @SerializedName("mcu_a_package")
    PackageModel mcuPackage;

    public PackageModel getRomPackage() {
        return romPackage;
    }

    public void setRomPackage(PackageModel romPackage) {
        this.romPackage = romPackage;
    }

    public PackageModel getMcuPackage() {
        return mcuPackage;
    }

    public void setMcuPackage(PackageModel mcuPackage) {
        this.mcuPackage = mcuPackage;
    }

    @NonNull
    @Override
    public String toString() {
        return romPackage.toString() + "\n\r" + mcuPackage.toString();
    }
}
