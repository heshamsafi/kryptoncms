package edu.asu.krypton.utilities;

import java.io.Serializable;
import java.util.ArrayList;

/** new added creating a generic class ********S.torkey ***************************/
public class SmartMap<KEY, VALUE> implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2410706028515317269L;
	private ArrayList<KEY>		keys;
	private ArrayList<VALUE>	values;

	public SmartMap() {
		this(null, null);
	}

	public SmartMap(final ArrayList<VALUE> valuesIn, final ArrayList<KEY> keysIn) {
		if (valuesIn == null) {
			values = new ArrayList<VALUE>();
		} else
			values = valuesIn;
		if (keysIn == null) {
			keys = new ArrayList<KEY>();
		} else
			keys = keysIn;
	}

	public void clearAll() {
		clearKeys();
		clearValues();
	}

	public void clearKeys() {
		keys.clear();
	}

	public void clearValues() {
		for (int i = 0; i < values.size(); i++)
			values.set(i, null);
	}

	public boolean containsKey(final KEY sp) {
		for (final KEY s : keys)
			if (sp == s)
				return true;
		return false;
	}

	public boolean containsValue(final VALUE sp) {
		for (final VALUE s : values)
			if (sp == s)
				return true;
		return false;
	}

	public void deleteByKey(final KEY key) {
		int count = 0;
		for (final KEY tmoKey : keys) {
			if (tmoKey == key) {
				keys.remove(tmoKey);
				break;
			} else
				count++;

		}
		if (keys.size() != values.size())
			values.remove(count);
	}

	public void deleteByValue(final VALUE value) {
		int count = 0;
		for (final VALUE tmpVal : values) {
			if (tmpVal == value) {
				values.remove(tmpVal);
				break;
			} else
				count++;

		}
		if (values.size() != keys.size())
			keys.remove(count);
	}

	public void deleteByIndex(final int index){
		keys.remove(index);
		values.remove(index);
	}
	
	public KEY getKey(final int i) {
		return keys.get(i);
	}
	
	public VALUE getValue(final int i){
		return values.get(i);
	}

	public KEY getKey(final VALUE value) {
		int count = 0;
		for (final VALUE tmpVal : values) {
			if (tmpVal == value) {
				return keys.get(count);
			} else
				count++;
		}
		return null;
	}

	public ArrayList<KEY> getKeys() {
		return keys;
	}

	public VALUE getValue(final KEY key) {
		int count = 0;
		for (final KEY A : keys) {
			if (A == key)
				return values.get(count);
			else
				count++;
		}
		return null;

	}

	public ArrayList<VALUE> getValues() {
		return values;
	}

	public void put(final VALUE value, final KEY key) {
		values.add(value);
		keys.add(key);
	}

	public void putAll(final SmartMap<KEY, VALUE> map) {
		values.addAll(map.getValues());
		keys.addAll(map.getKeys());
	}

	public void putAt(final int i, final VALUE val, final KEY key) {
		keys.add(i, key);
		values.add(i, val);
	}

	public void setKey(final VALUE value, final KEY key) {
		int count = 0;
		for (final VALUE tmpVal : values) {
			if (tmpVal.equals(value))
				break;
			else
				count++;
		}
		if (keys.size() > count)
			keys.set(count, key);
		else
			throw new RuntimeException("value is not found in SmartMap: "
					+ value.toString());
	}

	public void setValue(final KEY key, final VALUE value) {
		int count = 0;
		for (final KEY tmpKey : keys) {
			if (tmpKey == key)
				break;
			else
				count++;
		}
		if (values.size() > count)
			values.set(count, value);
		else {
			for (int i = 0; i < count - values.size() + 1; i++)
				values.add(null);
			values.set(count, value);
		}
		// throw new RuntimeException("key is not found in SmartMap: " +
		// key.toString());

	}

	public void setValue(final int index, VALUE value){
		values.set(index, value);
	}
	
	public void setKey(final int index, KEY key){
		keys.set(index, key);
	}
	
	public int size() {
		return keys.size();
	}

}
