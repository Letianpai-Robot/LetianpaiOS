package com.renhejia.robot.guidelib.qrcode;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renhejia.robot.guidelib.R;
import com.renhejia.robot.guidelib.utils.QRCodeUtil;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.guidelib.utils.ViewUtils;


/**
 * 引导页二维码展示页面
 *
 * @author liujunbin
 */
public class QRCodeView extends RelativeLayout {
    private Context mContext;
    private ImageView qrcode;
    private TextView tipsDesc;
    private TextView addDeviceTv;
    private TextView tvScan;
//    private ImageView qrcode;

    public QRCodeView(Context context) {
        super(context);
        inits(context);
    }

    public QRCodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inits(context);
    }

    public QRCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inits(context);
    }

    protected void inits(Context context) {
        this.mContext = context;
        inflate(mContext, R.layout.robot_qr_code_view, this);
        initView();
        resizeView();
    }

    private void resizeView() {
        int width = ViewUtils.getViewWidthSize(mContext, ((Activity) mContext).getWindow());
        int height = ViewUtils.getViewHeightSize(mContext, ((Activity) mContext).getWindow());
        if (width < height) {
            width = width * 5 / 12;
            ViewUtils.resizeImageViewSize(qrcode, width, width);
        } else {
            height = height * 5 / 12;
            ViewUtils.resizeImageViewSize(qrcode, height, height);
        }
    }

    protected void initView() {
        qrcode = findViewById(R.id.iv_qrcode);
        addDeviceTv = findViewById(R.id.add_device_tv);
        tipsDesc = findViewById(R.id.tips_desc);

        tvScan = findViewById(R.id.tv_weixin_scan);
        String ltpSN = SystemUtil.getLtpLastSn();
        tvScan.setText(mContext.getText(R.string.download_app) + ltpSN);

        // qrcode.setImageResource(R.drawable.qrcode_app);
        try {

            Bitmap bitmap = QRCodeUtil.createQRCodWithLogo(mContext,"https://cn.letianpai.com/?page_id=762&Robot-"+ltpSN, 400);
            // Bitmap bitmap = QRCodeUtil.createQRCodWithLogo(mContext,"#小程序://乐天派/9FmxXfvaVKWtVTJ", 400);
            qrcode.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            qrcode.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void setAddDeviceTvVisible(int visible){
        if (addDeviceTv != null){
            addDeviceTv.setVisibility(visible);
        }
    }

    public void setQrcodeVisible(int visible) {
        if (qrcode != null) {
            qrcode.setVisibility(visible);
        }
    }

    public void setTipsDescVisible(int visible) {
        if (tipsDesc != null) {
            tipsDesc.setVisibility(visible);
        }
    }

    public void setQrcode(int drawable) {
        if (qrcode != null) {
            qrcode.setImageResource(drawable);
        }
    }

    public void setTvScan(String scan) {
        if (tvScan != null) {
            tvScan.setText(scan);
        }
    }

}
