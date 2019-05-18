package sample.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mapにnullを突っ込んだ際の挙動の確認.
 * @author mikoto
 */
public class MapPutNull {

	/** Mapに格納する際のキー. */
	private static final String KEY = "key";

	/** Mapに格納する際の値. */
	private static final String VALUE = "value";

	public static void main(String[] args) {

		System.out.println("【HashMap】");
		var hashMap = new HashMap<String, String>();
		// キーにnullを設定
		key(hashMap);
		foreach(hashMap);
		value(hashMap);
		foreach(hashMap);
		both(hashMap);
		foreach(hashMap);

		System.out.println("【ConcurrentHashMap】");
		var concurrentHashMap = new ConcurrentHashMap<String, String>();
		try {
			key(concurrentHashMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("キーにnullを格納できない");
		}
		try {
			value(concurrentHashMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("値にnullを格納できない");
		}
		try {
			both(concurrentHashMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("それぞれで格納できないのだから両方一度になんて格納できるはずもなく");
		}

	}

	/**
	 * キーにnullを格納する.
	 * @param map 格納するMap
	 */
	private static void key(Map<String, String> map) {

		System.out.println("[key]");

		map.clear();

		map.put(null, VALUE);

		System.out.println("containsKey=" + map.containsKey(null));
		System.out.println("containsValue=" + map.containsValue(VALUE));

		// foreachの内容を確認
		foreach(map);
	}

	/**
	 * 値にnullを格納する.
	 * @param map 格納するMap
	 */
	private static void value(Map<String, String> map) {

		System.out.println("[value]");

		map.clear();

		map.put(KEY, null);

		System.out.println("containsKey=" + map.containsKey(KEY));
		System.out.println("containsValue=" + map.containsValue(null));

		// foreachの内容を確認
		foreach(map);
	}

	/**
	 * キーと値にnullを格納する.
	 * @param map 格納するMap
	 */
	private static void both(Map<String, String> map) {

		System.out.println("[both]");

		map.clear();

		map.put(null, null);

		System.out.println("containsKey=" + map.containsKey(null));
		System.out.println("containsValue=" + map.containsValue(null));

		// foreachの内容を確認
		foreach(map);
	}

	/**
	 * ループの内容を確認.
	 * @param map 確認するMap
	 */
	private static void foreach(Map<String, String> map) {

		// keyで回す
		for (var key: map.keySet()) {
			System.out.println("key=" + key);
			System.out.println("get=" + map.get(key));
		}
		// 値で回す
		for (var value: map.values()) {
			System.out.println("value=" + value);
		}
		// エントリーで回す
		for (var entry: map.entrySet()) {
			System.out.println("entry=" + entry);
			System.out.println("entry.key=" + entry.getKey());
			System.out.println("entry.value=" + entry.getValue());
		}

	}

}
