package sample.map;

import java.util.Map;

/**
 * 変数が多くなったので外出し.
 * @author mikoto
 */
public class MapPutBean {

	/** 対象のMap. */
	private Map<String, String> map;

	/** 並列処理の所要時間. */
	private long parallelTime;

	/** 直列処理の所要時間. */
	private long serialTime;

	/**
	 * コンストラクタ.
	 */
	public MapPutBean() {
		this.map = null;
		this.parallelTime = 0l;
		this.serialTime = 0l;
	}

	/**
	 * 対象のMapを取得.
	 * @return 対象のMap
	 */
	public Map<String, String> getMap() {
		return this.map;
	}

	/**
	 * 対象のMapを設定.
	 * @param map 対象のMap
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * 並列処理の所要時間を取得.
	 * @return 並列処理の所要時間
	 */
	public long getParallelTime() {
		return this.parallelTime;
	}

	/**
	 * 並列処理の所要時間を設定.
	 * @param parallelTime 並列処理の所要時間
	 */
	public void setParallelTime(long parallelTime) {
		this.parallelTime = parallelTime;
	}

	/**
	 * 直列処理の所要時間を取得.
	 * @return 直列処理の所要時間
	 */
	public long getSerialTime() {
		return this.serialTime;
	}

	/**
	 * 直列処理の所要時間を設定.
	 * @param serialTime 直列処理の所要時間
	 */
	public void setSerialTime(long serialTime) {
		this.serialTime = serialTime;
	}

}
