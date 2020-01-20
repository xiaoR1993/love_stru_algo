package com.zyr._07队列;

import com.zyr._05链表.LinkedList;
import com.zyr._05链表.List;

/**
 * @author zyr
 * @Description 队列
 * @Date 2020/1/20
 */
public class Queue<E>
{
	// 使用双向链表实现队列
	private List<E> list = new LinkedList<>();

	public int size()
	{
		return list.size();
	}

	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	public void clear()
	{
		list.clear();
	}

	/**
	 * 入队
	 *
	 * @param element
	 */
	public void enQueue(E element)
	{
		list.add(element);
	}

	/**
	 * 出队
	 *
	 * @return
	 */
	public E deQueue()
	{
		return list.remove(0);
	}

	/**
	 * 获取队列的头元素
	 *
	 * @return
	 */
	public E front()
	{
		return list.get(0);
	}

}
