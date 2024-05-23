package com.renhejia.robot.display.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

	public static String getRootPath() {
		String sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();// 获取跟目录
		}
		if (sdDir != null) {
			return sdDir;
		} else {
			return "";
		}
	}

	public static long getSurplusSpace() {
		return getAvailableSpace(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
	}

	public static long getAvailableSpace(String path) {
		StatFs statFs = new StatFs(path);
		//sd卡可用分区数
		long avCounts = statFs.getAvailableBlocksLong();
		//一个分区数的大小
		long blockSize = statFs.getBlockSizeLong();
		//sd卡可用空间
		long spaceLeft = avCounts * blockSize;
		return spaceLeft;
	}

	public static boolean delete(String path) {
		if (TextUtils.isEmpty(path)) {
			return true;
		}
		File file = new File(path);
		if (!file.exists()) {
			return true;
		}
		return file.delete();
	}

	public static boolean copyFile(String oldPath, String newPath) {
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (!oldfile.exists()) {
				return false;
			}
			File newFile = new File(newPath);
			File parentDir = newFile.getParentFile();
			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
			if (newFile.exists()) {
				newFile.delete();
			}
			newFile.createNewFile();
			inStream = new FileInputStream(oldPath); //读入原文件
			fs = new FileOutputStream(newPath);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			delete(newPath);
		} finally {
			closeSilence(inStream);
			closeSilence(fs);

		}
		return false;
	}


	public static void closeSilence(Closeable closeable) {
		if (null != closeable) {
			try {
				closeable.close();
			} catch (IOException e) {
//				e.printStackTrace();
			}
		}
	}

	public static boolean rename(String srcFilePath, String targetFilePath) {
		File srcFile = new File(srcFilePath);
		File targetFile = new File(targetFilePath);
		if (targetFile.exists()) {
			targetFile.delete();
		}
		if (srcFile.exists()) {
			return srcFile.renameTo(targetFile);
		}
		return false;
	}

	public static String getFileName(String downloadUrl) {
		if (downloadUrl == null) {
			return null;
		}
		if (downloadUrl.contains("/")) {
			String apkName[] = downloadUrl.split("\\/");
			if (apkName.length > 1) {
				return apkName[apkName.length - 1];
			}
		}
		return null;

	}

	/**
	 * 判断assets文件夹下的文件是否存在
	 *
	 * @return false 不存在    true 存在
	 */
	public static boolean isFileExists(Context context,String filename, String skinName) {
		AssetManager assetManager = context.getAssets();
		try {
			String[] names = assetManager.list(skinName);
			for (int i = 0; i < names.length; i++) {
				if (names[i].equals(filename.trim())) {
					System.out.println(filename + "存在");
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(filename + "不存在");
			return false;
		}
		System.out.println(filename + "不存在");
		return false;

	}
}