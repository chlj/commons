package cn.trinea.android.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *  eg: PreferencesUtils.putString("one",MainActivity.this,"name","abc");
 * @author Administrator
 *
 */
public class PreferencesUtils {
	/**
	 * 保存 key value为字符串格式
	 * 
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean putString(String xmlName, Context context,
			String key, String value) {
		SharedPreferences settings = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	/**
	 * 根据key 得到value,如果没有对应的key则返回null
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(String xmlName, Context context, String key) {
		return getString(xmlName, context, key, null);
	}

	/**
	 * 根据key 得到value,如果没有对应的key则返回默认值defaultValue
	 * 
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(String xmlName, Context context, String key,
			String defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		return settings.getString(key, defaultValue);
	}

	public static boolean putInt(String xmlName, Context context, String key,
			int value) {
		SharedPreferences settings = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	/**
	 * get int preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a int
	 * @see #getInt(Context, String, int)
	 */
	public static int getInt(String xmlName, Context context, String key) {
		return getInt(xmlName, context, key, -1);
	}

	/**
	 * get int preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a int
	 */
	public static int getInt(String xmlName, Context context, String key,
			int defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		return settings.getInt(key, defaultValue);
	}

	/**
	 * put long preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putLong(String xmlName, Context context, String key,
			long value) {
		SharedPreferences settings = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	/**
	 * get long preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a long
	 * @see #getLong(Context, String, long)
	 */
	public static long getLong(String xmlName, Context context, String key) {
		return getLong(xmlName, context, key, -1);
	}

	/**
	 * get long preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a long
	 */
	public static long getLong(String xmlName, Context context, String key,
			long defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		return settings.getLong(key, defaultValue);
	}

	/**
	 * put float preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putFloat(String xmlName, Context context, String key,
			float value) {
		SharedPreferences settings = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}

	/**
	 * get float preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a float
	 * @see #getFloat(Context, String, float)
	 */
	public static float getFloat(String xmlName, Context context, String key) {
		return getFloat(xmlName, context, key, -1);
	}

	/**
	 * get float preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a float
	 */
	public static float getFloat(String xmlName, Context context, String key,
			float defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		return settings.getFloat(key, defaultValue);
	}

	/**
	 * put boolean preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putBoolean(String xmlName, Context context,
			String key, boolean value) {
		SharedPreferences settings = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	/**
	 * get boolean preferences, default is false
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or false. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a boolean
	 * @see #getBoolean(Context, String, boolean)
	 */
	public static boolean getBoolean(String xmlName, Context context, String key) {
		return getBoolean(xmlName, context, key, false);
	}

	/**
	 * get boolean preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a boolean
	 */
	public static boolean getBoolean(String xmlName, Context context,
			String key, boolean defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		return settings.getBoolean(key, defaultValue);
	}

	/**
	 * 删除指定的PreferencesXML文件
	 * 
	 * @param context
	 * @param xmlName
	 */
	public static void delPreferencesXML(String xmlName, Context context) {
		SharedPreferences preferences = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		preferences.edit().clear().commit(); // 清理掉所有数据,防止缓存
		// 暴力删除文件
		File file = new File("/data/data/"
				+ context.getPackageName().toString() + "/shared_prefs",
				xmlName + ".xml");
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 得到所有的Key值List
	 * 
	 * @return
	 */
	public static List<String> getListKey(String xmlName, Context context) {
		Map<String, String> params = new HashMap<String, String>();
		List<String> list_key = new ArrayList<String>();
		SharedPreferences preferences = context.getSharedPreferences(xmlName,
				Context.MODE_PRIVATE);
		Map<String, Object> userMap = (Map<String, Object>) preferences
				.getAll();
		// 把map 集合转换成Iterator 迭代数据
		Iterator keyValuePairs = userMap.entrySet().iterator();
		for (int i = 0; i < userMap.size(); i++) {
			Map.Entry entry = (Map.Entry) keyValuePairs.next();
			String key = (String) entry.getKey();
			list_key.add(key);
		}
		return list_key;
	}
}
