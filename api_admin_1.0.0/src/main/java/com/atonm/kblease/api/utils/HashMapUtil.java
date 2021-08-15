package com.atonm.kblease.api.utils;

import org.apache.ibatis.type.Alias;

@Alias("CmHashMap")
public class HashMapUtil<K, V> extends java.util.HashMap<K, V> {

	private static final long serialVersionUID = 362498820763181265L;

	public void setString(K key, V value) {
		setString(key, value, null);
	}

	public void setString(K key, V value, V _default) {
		if (value == null)
			value = _default;
		this.put(key, value);
	}

	public String getString(String key) {
		return key != null ? (String) this.getOrDefault(key, null) : "";
	};

	public void setInt(K key, V value, V _default) {
		if (value == null)
			value = _default;
		this.put(key, value);
	}

	public void setInt(K key, V value) {
		this.put(key, value);
	}

	public int getInt(String key) {
		return key != null ? Integer.parseInt((String) this.getOrDefault(key, null)) : 0;
	};

	@SuppressWarnings("unchecked")
	@Override
	public V put(Object key, Object value) {
		if(value == null) {
			value = new String("");
		}else if(value instanceof Boolean) {
			value = (boolean) value ? "1" : "0";
		}else {
			value = value.toString();
		}
		return super.put((K) key, (V) value);
	}
}
