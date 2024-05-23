package com.letianpai.robot.mcuservice.utils;


import top.keepempty.sph.library.LogUtil;

public class ConvertUtils {

    public static String intToHex(int n) {
        //StringBuffer s = new StringBuffer();
        StringBuilder sb = new StringBuilder(8);
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            sb = sb.append(b[n%16]);
            n = n/16;
        }
        a = sb.reverse().toString();
//        LogUtils.logi("","16进制： "+ a);
        return a;
    }

    public static String convertToHexString(String content) {
        LogUtil.e("convertToHexString: "+content);
       String result = "";
       for (int i = 0; i< content.length();i++){
//           LogUtils.logi("","content.charAt(i)_"+i +"_"+ content.charAt(i));
//           LogUtils.logi("","content.charAt(i)_"+i +"_"+  intToHex(content.charAt(i)));
           result = result + intToHex(content.charAt(i));
       }

       return result;
    }

    /**
     * 十六进制转十进制
     * @param hex
     * @return
     */
    public static int hexToDec(String hex){
        return Integer.parseInt(hex, 16);
    }


    public static String decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }
        String [] results = new String[hexString.length() / 2];
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            builder.append((char)(hexToDec(hexString.substring(i,i+2))));
        }

        return builder.toString();
    }



}
