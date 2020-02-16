package com.zyr._16哈希表.model;

import com.zyr._14集合.Set;

/**
 * @author 张业荣
 * @Description hashSet
 * @Date 2020/2/14
 */
public class HashSet<E> implements Set<E>
{
	// 使用hashMap。来实现hashSet
	HashMap<E, Object> map = new HashMap<>();

	/**
	 * 清除所有元素
	 */
	@Override public void clear()
	{
		map.clear();
	}

	/**
	 * 元素的数量
	 *
	 * @return
	 */
	@Override public int size()
	{
		return map.size();
	}

	/**
	 * 是否为空
	 *
	 * @return
	 */
	@Override public boolean isEmpty()
	{
		return false;
	}

	/**
	 * 是否包含某个元素
	 *
	 * @param element
	 * @return
	 */
	@Override public boolean contains(Object element)
	{
		return false;
	}

	/**
	 * 添加元素到尾部
	 *
	 * @param element
	 */
	@Override public void add(Object element)
	{

	}

	/**
	 * 删除指定元素
	 *
	 * @param element
	 */
	@Override public void remove(Object element)
	{

	}

	@Override public void traversal(Visitor visitor)
	{

	}
}
