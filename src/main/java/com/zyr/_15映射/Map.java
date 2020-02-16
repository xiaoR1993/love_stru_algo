package com.zyr._15映射;

/**
 * @author 张业荣
 * @Description 映射
 * @Date 2020/2/10
 */
public interface Map<K, V>
{
	/**
	 * 清除所有元素
	 */
	void clear();

	/**
	 * 元素的数量
	 *
	 * @return
	 */
	int size();

	/**
	 * 是否为空
	 *
	 * @return
	 */
	boolean isEmpty();

	/**
	 * put数据
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	V put(K key, V value);

	/**
	 * 获取value
	 *
	 * @param key
	 * @return
	 */
	V get(K key);

	/**
	 * 根据key删除数据
	 *
	 * @param key
	 * @return
	 */
	V remove(K key);

	/**
	 * 是否包含key
	 *
	 * @param key
	 * @return
	 */
	boolean containsKey(K key);

	/**
	 * 是否包含value
	 *
	 * @param value
	 * @return
	 */
	boolean containsValue(V value);

	/**
	 * 遍历方法
	 *
	 * @param visitor
	 */
	void traversal(Visitor<K, V> visitor);

	public static abstract class Visitor<K, V>
	{
		// 控制停止装置
		boolean stop;

		/**
		 * @return 如果返回true，就代表停止遍历
		 */
		public abstract boolean visit(K key, V value);

	}

}
