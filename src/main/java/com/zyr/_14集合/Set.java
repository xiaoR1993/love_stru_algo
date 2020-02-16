package com.zyr._14集合;

/**
 * @author 张业荣
 * @Description 集合
 * @Date 2020/2/9
 */
public interface Set<E>
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
	 * 是否包含某个元素
	 *
	 * @param element
	 * @return
	 */
	boolean contains(E element);

	/**
	 * 添加元素到尾部
	 *
	 * @param element
	 */
	void add(E element);

	/**
	 * 删除指定元素
	 *
	 * @param element
	 */
	void remove(E element);

	void traversal(Visitor<E> visitor);

	public static abstract class Visitor<E>
	{
		// 控制停止装置
		boolean stop;

		/**
		 * @return 如果返回true，就代表停止遍历
		 */
		public abstract boolean visit(E element);

	}

}
