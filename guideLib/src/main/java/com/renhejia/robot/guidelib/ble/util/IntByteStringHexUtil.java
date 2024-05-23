package com.renhejia.robot.guidelib.ble.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

/**
 *
 */
public class IntByteStringHexUtil {

    /**
     * 将一个整形化为十六进制，并以字符串的形式返回
     */
    private final static String[] hexArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String intToHexStr(int n) {
        if (n < 0) {
            n = n + 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexArray[d1] + hexArray[d2];
    }

    public static int hexStrToInt(String str) {
        return Integer.parseInt(str, 16);
    }

    public static long hexStrToLong(String str) {
        return Long.parseLong(str, 16);
    }

    //单个字符转成十六进制
    public static String oneStrToHexStr(String string) {
        if (string.equals("")) {
            return "00";
        }

        int n = Integer.valueOf(string);

        if (n < 0) {
            n = n + 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexArray[d1] + hexArray[d2];
    }

    //将字符串中的每个字符转成十六进制
    public static String strToHexStr(String str) {

        String hexStr = "";

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            hexStr += Integer.toHexString(chars[i]);
        }

        return hexStr;
    }

    public static String strToHexStrTemp(String str) {

        String hexStr = "";

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            String temStr = Integer.toHexString(chars[i]);
            if (temStr.length() == 1) {
                temStr = "0"+temStr;
            }
            hexStr += temStr;
        }

        return hexStr;
    }
    /**
     * 十六进制 转换成ASCII字符串
     * @param hex
     * @return
     */
    public static String hex2Str(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String h = hex.substring(i, (i + 2));
            if(h.equals("00")){
                continue;
            }
            int decimal = Integer.parseInt(h, 16);
            sb.append((char) decimal);
        }
        return sb.toString();
    }

    public static String utf8ToStr(String utf8) {
        String strUTF8 = null;
        try {
            String s1 = utf8.replaceAll ("(.{2})", "$1%");
            utf8 = "%"+s1.substring(0,s1.length()-1);
            strUTF8 = URLDecoder.decode(utf8, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strUTF8;
    }

    //将字符串中的每个字符的ASCII转成十六进制
    public static String strASCIIToHexStr(String str) {

        String hexStr = "";

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int number = (int) chars[i];
            hexStr += intToHexStr(number);
        }

        return hexStr;
    }

    public static String hexStrToCharStr(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char[] charArray = new char[str.length() / 2];
        for (int i = 0; i < charArray.length; i++) {
            String subStr = str.substring(2 * i, 2 * i + 2);
            char c= (char) Integer.parseInt(subStr, 16);
            sb.append(c);
        }
        return sb.toString();
    }

    public static byte[] hexStrToByteArray(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String StrToAddHexStr(String[] strings) {
        long all = Long.parseLong("0", 16);
        for (int i = 0; i < strings.length; i++) {
            long one = Long.parseLong(strings[i], 16);
            all = all + one;
        }
        return Long.toHexString(256 - (all % 256));
    }

    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     *
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    public static String intToHexString(int n) {
        String s = Integer.toHexString(n);
        if (s.length() % 2 != 0) {
            s = "0" + s;
        }
        return s;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(hexStrToByteArray("55AA0100010001")));
    }

}