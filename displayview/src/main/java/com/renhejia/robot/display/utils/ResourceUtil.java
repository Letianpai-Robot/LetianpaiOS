package com.renhejia.robot.display.utils;

import android.content.Context;

/**
 *
 */
public class ResourceUtil {

    public static int getId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "id");
    }

    public static int getLayoutId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "layout");
    }

    public static int getStringId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "string");
    }

    public static int getDrawableId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "drawable");
    }

    public static int getMipmapId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "mipmap");
    }

    public static int getColorId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "color");
    }

    public static int getDimenId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "dimen");
    }

    public static int getAttrId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "attr");
    }

    public static int getStyleId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "style");
    }

    public static int getAnimId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "anim");
    }

    public static int getArrayId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "array");
    }

    public static int getIntegerId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "integer");
    }

    public static int getBoolId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "bool");
    }

    private static int getIdentifierByType(Context context, String resourceName, String defType) {
        return context.getResources().getIdentifier(resourceName,
                defType,
                context.getPackageName());
    }
}
