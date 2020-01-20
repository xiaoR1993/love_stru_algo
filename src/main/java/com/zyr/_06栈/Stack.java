package com.zyr._06栈;

import com.zyr._04数组.ArrayList;
import com.zyr._05链表.List;

/**
 * @author zyr
 * @Description 栈
 * @Date 2020/1/20
 */
public class Stack<E>
{
	// 使用动态数组来实现栈
	private List<E> list = new ArrayList<>();

	public void clear()
	{
		list.clear();
	}

	public int size()
	{
		return list.size();
	}

	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	public void push(E element)
	{
		list.add(element);
	}

	/**
	 * 从栈顶弹出1个元素
	 *
	 * @return
	 */
	public E pop()
	{
		return list.remove(list.size() - 1);
	}

	/**
	 * @return
	 */
	public E top()
	{
		return list.get(list.size() - 1);
	}

}
