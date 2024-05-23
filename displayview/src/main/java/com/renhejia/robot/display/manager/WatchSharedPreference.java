package com.renhejia.robot.display.manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import java.util.Map;

public class WatchSharedPreference {
	public static final int UPDATE_TYPE_NONE = 0;

	private Context mContext = null;
	private String mFileName = null;
	/**
	 * 使用的系统SharedPreferences对象
	 */
	private SharedPreferences.Editor mEditor = null;
	private SharedPreferences mSharedPref = null;

	/**
	 * sharedpreference对于的资源id,默认-1
	 */
	private int mMode = Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS;

	/**
	 * 内存数据的map
	 */
	private Map<String, Object> mMap = null;

	/**
	 * 广播相关
	 */
	public static final String ACTION_INTENT_CONFIG_CHANGE = "com.letianpai.robot.SETTING_CHANGE";

//	private static final String BROADCAST_PID = "broadcastPid";
//	private static final String UPDATE_TYPE = "updateType";

	public static final String SHARE_PREFERENCE_NAME = "WatchConfig";

	/**
	 * 表示内存数据是否发生过改变，避免不必要的写文件操作
	 */
	private boolean mHasChanged = false;
	
	private static final int HANDLE_SETTING_CHANGED = 10;
	private static final long DELAY_SEND_BROADCAST = 200;
	private Handler mHandler = null;

	public WatchSharedPreference(Context context, String fileName, String action) {
		mContext = context;
		
		mMode = Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS;

		this.mFileName = fileName;
		reloadSharedPref(false);
		
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(msg != null) {
					switch(msg.what) {
					case HANDLE_SETTING_CHANGED :
						sendSettingChangeBroadcast();
						break;
					}
				}
			}
		};

	}


	/**
	 * 文件操作，重新加载配置文件
	 * 
	 * @param syncIPCFlag
	 *            true的时候会通知所有的进程全部重新加载，否则只加载调用进程
	 */
	public void reloadSharedPref(boolean syncIPCFlag) {
		mSharedPref = mContext.getSharedPreferences(mFileName, mMode);
		mEditor = mSharedPref.edit();
		mHasChanged = false;

		reloadMap();

		if (syncIPCFlag) {
			 //sendIPCSyncBroadcast();
			sendSettingChangeBroadcast();
		}
	}

	private void sendSettingChangeBroadcast() {
		Intent intent = new Intent(ACTION_INTENT_CONFIG_CHANGE);
	    mContext.sendBroadcast(intent);
    }
	
	private void sendMessageDelay(int handleid, long delay) {
		if(mHandler != null) {
			mHandler.removeMessages(handleid);
			mHandler.sendEmptyMessageDelayed(handleid, delay);
		}
	}


	@SuppressWarnings("unchecked")
	public void reloadMap() {
		if (mMap != null) {
			mMap.clear();
		}
		mMap = (Map<String, Object>) mSharedPref.getAll();
	}

	public Map<String, Object> getMap() {
		return this.mMap;
	}

	/**
	 * 内存操作，释放对象占用的资源，取消广播，清空内存数据
	 */
	public void terminate() {
		try {
			// mContext.unregisterReceiver(mConfigChangeReceiver);
			// if (mMap != null) {
			// mMap.clear();
			// mMap = null;
			// }
		}
		catch (Exception e) {

		}
	}

	/**
	 * 判断Map中是否包含指定key
	 * 
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean contains(String key) {
		return mMap.containsKey(key);
	}

	/**
	 * 文件操作，提交数据到文件，此函数进行磁盘io写文件, 成功后会通知使用该文件数据的数据重新加载数据
	 * 
	 * @return boolean true写文件成功；false写文件失败
	 */
	public boolean commit() {
		if (!mHasChanged) {
			return false;
		}
		if (mEditor != null) {
			if (mEditor.commit()) {
				mHasChanged = false;
				sendMessageDelay(HANDLE_SETTING_CHANGED, DELAY_SEND_BROADCAST);
				//sendSettingChangeBroadcast();
				return true;
			}
		}
		return false;
	}

	/**
	 * 内存操作，移除包含特定key的数据
	 * 
	 * @param key
	 *            void
	 */
	public void remove(String key) {
		mEditor = mEditor.remove(key);
		mMap.remove(key);
		mHasChanged = true;
	}

	/**
	 * 内存操作，清空数据
	 * 
	 * @return boolean true成功；false失败
	 */
	public boolean clear() {
		if (mEditor != null) {
			mEditor.clear();
			mMap.clear();
			mHasChanged = true;
			return true;
		}
		return false;
	}

	/**
	 * 私有公用方法，添加数据，value是object
	 * 
	 * @param key
	 * @param defValue
	 * @return boolean true成功，false失败
	 */
	private boolean setValue(String key, Object defValue) {
		Object preValue = mMap.put(key, defValue);
		if (preValue == null || !preValue.equals(defValue)) {
			mHasChanged = true;
			return true;
		}
		return false;
	}

	/**
	 * 内存操作，添加数据，value是boolean
	 * 
	 * @param key
	 * @param defValue
	 */
	public void putBoolean(String key, boolean defValue) {
		if (setValue(key, defValue)) {
			mEditor = mEditor.putBoolean(key, defValue);
		}
	}

	/**
	 * 内存操作，添加数据，value是int
	 * 
	 * @param key
	 * @param defValue
	 *            void
	 */
	public void putInt(String key, int defValue) {
		if (setValue(key, defValue)) {
			mEditor = mEditor.putInt(key, defValue);
		}

	}

	/**
	 * 内存操作，添加数据，value是long
	 * 
	 * @param key
	 * @param defValue
	 *            void
	 */
	public void putLong(String key, long defValue) {
		if (setValue(key, defValue)) {
			mEditor = mEditor.putLong(key, defValue);
		}
	}
	
	/**
	 * 内存操作，添加数据，value是float
	 * 
	 * @param key
	 * @param defValue
	 *            void
	 */
	public void putFloat(String key, float defValue) {
		if (setValue(key, defValue)) {
			mEditor = mEditor.putFloat(key, defValue);
		}
	}

	/**
	 * 内存操作，添加数据，value是String
	 * 
	 * @param key
	 * @param defValue
	 *            void
	 */
	public void putString(String key, String defValue) {
		if (setValue(key, defValue)) {
			mEditor = mEditor.putString(key, defValue);
		}
	}

	/**
	 * 内存操作，获取boolean类型的数据
	 * 
	 * @param key
	 * @param defValue
	 *            默认值
	 * @return boolean
	 */
	public boolean getBoolean(String key, boolean defValue) {
		Boolean v = (Boolean) mMap.get(key);
		return v != null ? v : defValue;
	}

	/**
	 * 内存操作，获取float类型的数据
	 * 
	 * @param key
	 * @param defValue
	 *            默认值
	 * @return float
	 */
	public float getFloat(String key, float defValue) {
		Float v = (Float) mMap.get(key);
		return v != null ? v : defValue;
	}

	/**
	 * 内存操作，获取int类型的数据
	 * 
	 * @param key
	 * @param defValue
	 *            默认值
	 * @return int
	 */
	public int getInt(String key, int defValue) {
		Integer v = (Integer) mMap.get(key);
		return v != null ? v : defValue;
	}

	/**
	 * 内存操作，获取long类型的数据
	 * 
	 * @param key
	 * @param defValue
	 *            默认值
	 * @return long
	 */
	public long getLong(String key, long defValue) {
		Long v = (Long) mMap.get(key);
		return v != null ? v : defValue;
	}

	/**
	 * 内存操作，获取string类型的数据
	 * 
	 * @param key
	 * @param defValue
	 *            默认值
	 * @return String
	 */
	public String getString(String key, String defValue) {
		String v = (String) mMap.get(key);
		return v != null ? v : defValue;
	}

}
