package com.renhejia.robot.launcherbaselib.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.renhejia.robot.launcherbaselib.app.RHJApp;
import com.renhejia.robot.launcherbaselib.overseas.OverseasUtils;

public class QRCodeUtil {
    private static final String TAG = QRCodeUtil.class.getSimpleName();
    private static final int BLACK = 0xff000000;
    public static Bitmap createBitmap(int size) throws Exception {
        //TODO 需要增加拼配网络请求的逻辑，这部分需要确认网络请求格后增加这部分逻辑，再再验证
        String url = ActivationConsts.LTP_ACTIVATE_URL;

        if (OverseasUtils.isInternationalized(RHJApp.getRhjApp())){
            url = ActivationConsts.LTP_ACTIVATE_OVERSEAS_URL;
        }
        //TODO 需要增加网络请求逻辑
        Log.i(TAG, "url===" + url);
        Bitmap bitmap = createQRCode(url, size);
        return bitmap;
    }
    public static Bitmap createQRCode(String str, int widthAndHeight)  {
//    public static Bitmap createQRCode(String str, int widthAndHeight) throws WriterException {
//        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); //容错率
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        hints.put(EncodeHintType.MARGIN, 1);
//        BitMatrix matrix = new QRCodeWriter().encode(str,
//                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight, hints);
//        int width = matrix.getWidth();
//        int height = matrix.getHeight();
//        int[] pixels = new int[width * height];
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                if (matrix.get(x, y)) {
//                    pixels[y * width + x] = BLACK;
//                }
//            }
//        }
//        Bitmap bitmap = Bitmap.createBitmap(width, height,
//                                            Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        return bitmap;
        return null;
    }

    public static String decToHex(int dec) {
        String hex = "";
        while (dec != 0 && dec != -1) {
            String h = Integer.toString(dec & 0xff, 16);
            if ((h.length() & 0x01) == 1) {
                h = '0' + h;
            }
            hex = hex + h;
            dec = dec >> 8;
        }
        return hex;
    }

    /**
     * 加密 RC4>Base64
     * @param
     * @return
     */
    public static String encodeWithOnlyHardCode(int[] data) {
        // TODO 需要做加密逻辑
        return null;
    }

    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(int[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
    * 在二维码中间添加Logo图案
    */
    public static Bitmap addLogo(Bitmap src, Bitmap logo) {

        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
//        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        float scaleFactor = srcWidth * 1.0f / 4 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

//            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }

    public static Bitmap getLogoBitmap(Context context,int res) {

        Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), res);
        return logoBitmap;

    }


}
