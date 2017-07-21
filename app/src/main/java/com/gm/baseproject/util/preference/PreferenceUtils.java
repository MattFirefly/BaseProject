package com.gm.baseproject.util.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {
	public static void clearAll(Context context){
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().clear().commit();
	}
	public static void setPrefInt(Context context, final String key,
								  final int value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putInt(key, value).commit();
	}

	public static int getPrefInt(Context context, final String key,
								 final int defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getInt(key, defaultValue);
	}

	public static void setPrefLong(Context context, final String key,
								   final long value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putLong(key, value).commit();
	}

	public static long getPrefLong(Context context, final String key,
								   final long defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getLong(key, defaultValue);
	}

	public static String getPrefString(Context context, String key,
									   final String defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getString(key, defaultValue);
	}

	public static void setPrefString(Context context, final String key,
									 final String value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putString(key, value).commit();
	}

	public static boolean getPrefBoolean(Context context, final String key,
										 final boolean defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getBoolean(key, defaultValue);
	}

	public static void setPrefBoolean(Context context, final String key,
									  final boolean value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putBoolean(key, value).commit();
	}


}
