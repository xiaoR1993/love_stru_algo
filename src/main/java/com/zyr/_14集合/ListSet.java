package com.zyr._14集合;

import com.zyr._05链表.LinkedList;
import com.zyr._05链表.List;

/**
 * @author 张业荣
 * @Description 使用List实现的集合
 * @Date 2020/2/9
 */
public class ListSet<E> implements Set<E>
{
	private List<E> list = new LinkedList<>();

	/**
	 * 清除所有元素
	 */
	@Override public void clear()
	{
		list.clear();
	}

	/**
	 * 元素的数量
	 *
	 * @return
	 */
	@Override public int size()
	{
		return list.size();
	}

	/**
	 * 是否为空
	 *
	 * @return
	 */
	@Override public boolean isEmpty()
	{
		return list.isEmpty();
	}

	/**
	 * 是否包含某个元素
	 *
	 * @param element
	 * @return
	 */
	@Override public boolean contains(E element)
	{
		return list.contains(element);
	}

	/**
	 * 添加元素到尾部
	 *
	 * @param element
	 */
	@Override public void add(E element)
	{
		int index = list.indexOf(element);
		if (index != List.ELEMENT_NOT_FOUND)
		{
			// 存在就覆盖
			list.set(index, element);
		}
		else
		{
			// 不存在就添加
			list.add(element);
		}

	}

	/**
	 * 删除指定元素
	 *
	 * @param element
	 */
	@Override public void remove(E element)
	{
		int index = list.indexOf(element);
		if (index != List.ELEMENT_NOT_FOUND)
		{
			list.remove(index);
		}
	}

	@Override public void traversal(Visitor<E> visitor)
	{
		if (visitor == null)
			return;
		int size = list.size();
		for (int i = 0; i < size; i++)
		{
			if (visitor.visit(list.get(i)))
				return;

		}

	}
}
