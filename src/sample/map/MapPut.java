package sample.map;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * いくつかのMapで複数のスレッドから同時に処理を実施する.
 * @author mikoto
 */
public class MapPut {

	/** リスト件数. */
	private static final int listCount = 100000;

	public static void main(String[] args) {

		// 1000件のリストを生成
		List<String> list = new ArrayList<>(listCount);
		for (int i = 0; i < listCount; i++) {
			list.add(Integer.toString(i));
		}

		MapPutBean hashMapBean = new MapPutBean();
		hashMapBean.setMap(new HashMap<>());
		MapPutBean concurrentHashMapBean = new MapPutBean();
		concurrentHashMapBean.setMap(new ConcurrentHashMap<>());
		MapPutBean linkedHashMapBean = new MapPutBean();
		linkedHashMapBean.setMap(new LinkedHashMap<>());

		for (int i = 1; i <= 1000; i++) {
			System.out.println(String.format("【%,d回目 並列処理】", i));
			parallel(list, hashMapBean);
			parallel(list, concurrentHashMapBean);
			parallel(list, linkedHashMapBean);
			System.out.println();
			System.out.println();

			System.out.println(String.format("【%,d回目 直列処理】", i));
			serial(list, hashMapBean);
			serial(list, concurrentHashMapBean);
			serial(list, linkedHashMapBean);
			System.out.println();
			System.out.println();
		}

		// 処理時間
		printTime(hashMapBean, concurrentHashMapBean, linkedHashMapBean);
	}

	/**
	 * 処理を並列で実施.
	 * @param list Mapに詰め込む内容
	 * @param mapBean 色々と格納したBean
	 */
	private static void parallel(List<String> list, MapPutBean mapBean) {

		// mapクリア
		mapBean.getMap().clear();

		// 処理開始時間を減算
		mapBean.setParallelTime(mapBean.getParallelTime() - new Date().getTime());
		// mapに格納
		list.parallelStream()
			.forEach(k -> {
				mapBean.getMap().put(k, k);
			});
		// 処理終了時間を加算
		mapBean.setParallelTime(mapBean.getParallelTime() + new Date().getTime());

		// 格納状況を確認
		print(mapBean);
	}

	/**
	 * 処理を直列で実施.
	 * @param list Mapに詰め込む内容
	 * @param mapBean 色々と格納したBean
	 */
	private static void serial(List<String> list, MapPutBean mapBean) {

		// mapクリア
		mapBean.getMap().clear();

		// 処理開始時間を減算
		mapBean.setSerialTime(mapBean.getSerialTime() - new Date().getTime());
		// mapに格納
		list.stream()
			.forEach(k -> {
				mapBean.getMap().put(k, k);
			});
		// 処理終了時間を加算
		mapBean.setSerialTime(mapBean.getSerialTime() + new Date().getTime());

		// 格納状況を確認
		print(mapBean);
	}

	/**
	 * 処理結果件数を出力.
	 */
	private static void print(MapPutBean mapBean) {

		if (listCount != mapBean.getMap().size()) {
			System.out.println(String.format("%s=%,d", mapBean.getMap().getClass().getSimpleName(), mapBean.getMap().size()));
		}
	}

	/**
	 * 処理時間を出力.
	 */
	private static void printTime(MapPutBean hashMapBean, MapPutBean concurrentHashMapBean, MapPutBean linkedHashMapBean) {

		System.out.println();

		System.out.println("【並列処理】");
		System.out.println(String.format("hashMap=%,dms", hashMapBean.getParallelTime()));
		System.out.println(String.format("concurrentHashMap=%,dms", concurrentHashMapBean.getParallelTime()));
		System.out.println(String.format("linkedHashMap=%,dms", linkedHashMapBean.getParallelTime()));

		System.out.println();

		System.out.println("【直列処理】");
		System.out.println(String.format("hashMap=%,dms", hashMapBean.getSerialTime()));
		System.out.println(String.format("concurrentHashMap=%,dms", concurrentHashMapBean.getSerialTime()));
		System.out.println(String.format("linkedHashMap=%,dms", linkedHashMapBean.getSerialTime()));

		System.out.println();
	}

}
