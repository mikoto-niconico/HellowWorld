package sample;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * BeanクラスのtoString()用のインターフェース.
 * @author mikoto
 */
public interface BeanToString {

	/**
	 * Beanクラス用のtoString()メソッド.
	 * @param obj Beanクラスのインスタンス
	 * @return 各項目を文字列連結した値
	 */
	public static String toString(BeanToString obj) {

		StringBuilder sb = new StringBuilder();

		try {
			var map = createFieldMap(obj);

			for (Class<?> clazz: map.keySet()) {
				// クラスの件数分ループ

				for (var field: map.get(clazz)) {
					// フィールドの件数分ループ

					field.setAccessible(true);
					// フィールド名
					var name = field.getName();
					// 値
					var value = field.get(obj);

					if (value instanceof Iterable) {
						// 対象のフィールドが繰り返し可能なクラスの場合、全件実施
						sb.append(name).append("={").append(System.lineSeparator());
						for (var listValue: (Iterable<?>)value) {

							if (listValue instanceof BeanToString) {
								// 再度、当関数を呼び出し可能
								sb.append(toString((BeanToString)listValue));
							} else {
								// 呼び出せない場合は、とにかく連結
								sb.append(listValue).append(System.lineSeparator());
							}
						}
						sb.append("}").append(System.lineSeparator());
					} else {
						sb.append(name).append("={");

						if (value instanceof BeanToString) {
							// 再度、当関数を呼び出し可能
							sb.append(toString((BeanToString)value));
						} else {
							// 呼び出せない場合は、とにかく連結
							sb.append(value);
						}
						sb.append("}").append(System.lineSeparator());
					}
				}
			}
			return sb.toString();

		} catch (RuntimeException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 「何のクラスが何のフィールドを保持するか」を保持するMapを生成
	 * @param obj インスタンス
	 * @return 「何のクラスが何のフィールドを保持するか」を保持するMap
	 */
	private static Map<Class<?>, List<Field>> createFieldMap(Object obj) {

		// 冪等性を保つために子クラスから親クラスへの順序を覚える
		var map = new LinkedHashMap<Class<?>, List<Field>>();

		Class<?> clazz = obj.getClass();
		// 自クラスのフィールドリストをMapに追加
		putFieldMap(map, clazz);

		// 親クラスのフィールドリストをMapに追加
		var spClazz = obj.getClass().getSuperclass();
		// 親クラスの数だけ実施
		while(spClazz != null) {
			putFieldMap(map, spClazz);
			spClazz = spClazz.getSuperclass();
		}

		return map;
	}

	/**
	 * フィールドを取得してMapに格納.
	 * @param map 格納するMap
	 * @param clazz フィールドを取得するクラス
	 */
	private static void putFieldMap(Map<Class<?>, List<Field>> map, Class<?> clazz) {

		var list =
				Arrays.asList(clazz.getDeclaredFields()).stream()
				// 冪等性を保つためにフィールド名でソート
				.sorted(Comparator.comparing(Field::getName))
				.collect(Collectors.toList());
		map.put(clazz, list);
	}

}
