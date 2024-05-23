package com.renhejia.robot.guidelib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.renhejia.robot.guidelib.R;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class QRCodeUtil {
    private static final String TAG = QRCodeUtil.class.getSimpleName();
    private static final int BLACK = 0xff000000;
    public static Bitmap createBitmap(int size) throws Exception {
        //TODO 需要增加拼配网络请求的逻辑，这部分需要确认网络请求格后增加这部分逻辑，再再验证
        String url = "www.baidu.com_www.baidu.com";

        //        if (OverseasUtils.isInternationalized(RHJApp.getRhjApp())){
        //            url = ActivationConsts.LTP_ACTIVATE_OVERSEAS_URL;
        //        }
        //TODO 需要增加网络请求逻辑
        Log.i(TAG, "url===" + url);
        Bitmap bitmap = createQRCode(url, size);
        return bitmap;
    }
    public static Bitmap createQRCode(String str, int widthAndHeight) throws WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); //容错率
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix matrix = new QRCodeWriter().encode(str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     *
     * @param context
     * @param content 二维码内容
     * @param size 二维码边长
     * @return
     * @throws WriterException
     */
    public static Bitmap createQRCodWithLogo(Context context,String content, int size) throws WriterException {
        Bitmap qrBitmap = QRCodeUtil.createQRCode(content, size);
        Bitmap logoBitmap = QRCodeUtil.getLogoBitmap(context, R.drawable.logo);
        Bitmap mergedBitmap = QRCodeUtil.addLogo(qrBitmap, logoBitmap);
        return mergedBitmap;
    }

    /**
     *
     * @param context
     * @param content 二维码内容
     * @param size 二维码边长
     * @return
     * @throws WriterException
     */
    public static Bitmap createQRCodWithLogo(Context context, String content, ImageView imageView, int size) throws WriterException {
        Bitmap qrBitmap = QRCodeUtil.createQRCode(content, size);
        Bitmap logoBitmap = QRCodeUtil.getLogoBitmap(context, R.drawable.logo);
        Bitmap mergedBitmap = QRCodeUtil.addLogo(qrBitmap, logoBitmap);
        imageView.setBackgroundColor(context.getResources().getColor(R.color.white));
        return mergedBitmap;
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
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
//        float scaleFactor = srcWidth * 1.0f / 4 / logoWidth;
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


    /**

     * 用于创建一个二维码

     * @param content

     * @param width

     * @param height

     */

    public static Bitmap createQRCode(String content, int width, int height) {

        try {

            //1,创建实例化对象
            QRCodeWriter writer = new QRCodeWriter() ;

            //2,设置字符集
            Map<EncodeHintType, String> map = new HashMap<EncodeHintType, String>();
            map.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            //3，通过encode方法将内容写入矩阵对象
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height,map);

            //4，定义一个二维码像素点的数组，向每个像素点中填充颜色

            int[] pixels = new int[width*height];

            //5,往每一像素点中填充颜色（像素没数据则用黑色填充，没有则用彩色填充，不过一般用白色）

            for (int i = 0; i < height; i++) {

                for (int j = 0; j < width; j++) {

                    if (matrix.get(j, i)) {

                        pixels[i*width+j] = 0xff000000;

                    }else {

                        pixels[i*width+j] = 0xffffffff;

                    }

                }

            }

            //6,创建一个指定高度和宽度的空白bitmap对象

            Bitmap bm_QR = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            //7，将每个像素的颜色填充到bitmap对象

            bm_QR.setPixels(pixels, 0, width, 0, 0, width, height);

            //8，创建一个bitmap对象用于作为其图标

//            Bitmap bm_login = BitmapFactory.decodeResource(getResources(), R.drawable.img_kf_qq);
//
//            //9，创建一个方法在二维码上添加一张图片
//
//            if (addLogin(bm_QR,bm_login) != null) {
//
//                imageView_main.setImageBitmap(addLogin(bm_QR,bm_login));
//
//            }
            return bm_QR;

        } catch (WriterException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }
        return null;

    }

}
