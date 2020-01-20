package com.zyr._07队列;

import com.zyr._05链表.LinkedList;
import com.zyr._05链表.List;

/**
 * @author zyr
 * @Description double ended queue 双端队列
 * @Date 2020/1/20
 */
public class Deque<E>
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
	 * 从队尾入队
	 *
	 * @param element
	 */
	public void enQueueRear(E element)
	{
		list.add(element);

	}

	/**
	 * 从队头入队
	 *
	 * @param element
	 */
	public void enQueueFront(E element)
	{
		list.add(0, element);

	}

	/**
	 * 从队尾出队
	 *
	 * @return
	 */
	public E deQueueRear()
	{
		return list.remove(list.size() - 1);
	}

	/**
	 * 从队头出队
	 *
	 * @return
	 */
	public E deQueueFront()
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

	/**
	 * 获取队尾的元素
	 *
	 * @return
	 */
	public E rear()
	{
		return list.get(list.size() - 1);
	}

}
