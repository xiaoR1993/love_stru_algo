package com.zyr._18优先级队列;

import com.zyr._17二叉堆.BinaryHeap;
import com.zyr._17二叉堆.Heap;

import java.util.Comparator;

/**
 * @author 张业荣
 * @Description 优先级队列
 * @Date 2020/2/15
 */
public class PriorityQueue<E>
{
	private Heap<E> heap;

	public PriorityQueue()
	{
		this(null);
	}

	public PriorityQueue(Comparator<E> comparator)
	{
		heap = new BinaryHeap<>(comparator);
	}

	public int size()
	{
		return heap.size();
	}

	public boolean isEmpty()
	{
		return heap.isEmpty();
	}

	public void clear()
	{
		heap.clear();
	}

	/**
	 * 入队
	 *
	 * @param element
	 */
	public void enQueue(E element)
	{
		heap.add(element);
	}

	/**
	 * 出队
	 *
	 * @return
	 */
	public E deQueue()
	{
		return heap.remove();
	}

	/**
	 * 获取队列的头元素
	 *
	 * @return
	 */
	public E front()
	{
		return heap.get();
	}

}
