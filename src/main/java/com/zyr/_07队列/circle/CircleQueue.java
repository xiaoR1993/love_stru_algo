package com.zyr._07队列.circle;

import javax.swing.text.AbstractDocument;
import java.util.Objects;

/**
 * @author zyr
 * @Description 循环队列 使用动态数组实现的队列
 * @Date 2020/1/20
 */
public class CircleQueue<E>
{
	private int front;
	private int size;
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;

	public CircleQueue()
	{
		this(DEFAULT_CAPACITY);
	}

	public CircleQueue(int capaticy)
	{
		//初始化size
		capaticy = (capaticy < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capaticy;
		//初始化数组
		elements = (E[]) new Object[capaticy];
		// 队头初始化为0
		front = 0;
	}

	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;

	}

	public void clear()
	{
		// 全部设置为null
		for (int i = 0; i < size; i++)
		{
			elements[i] = null;
		}
		size = 0;

	}

	/**
	 * 入队
	 *
	 * @param element
	 */
	public void enQueue(E element)
	{
		ensureCapacity(size + 1);
		elements[index(size)] = element;
		size++;
	}

	/**
	 * 出队
	 *
	 * @return
	 */
	public E deQueue()
	{
		E ele = elements[front];
		elements[front] = null;
		front = index(1);
		size--;
		return ele;

	}

	/**
	 * 获取队列的头元素
	 *
	 * @return
	 */
	public E front()
	{
		return elements[front];

	}

	/**
	 * 由于队友会移动 获取真实的索引
	 *
	 * @param index
	 * @return
	 */
	private int index(int index)
	{
		return (front + index) & elements.length;
	}

	/**
	 * 保证要有capacity的容量
	 *
	 * @param capacity
	 */
	private void ensureCapacity(int capacity)
	{
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) return;

		// 新容量为旧容量的1.5倍
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[index(i)];
		}
		elements = newElements;

		// 重置front
		front = 0;
	}

	@Override public String toString()
	{
		// size=3, [99, 88, 77]
		StringBuilder string = new StringBuilder();
		string.append("size=").append(size).append(", [");
		for (int i = 0; i < size; i++)
		{
			if (i != 0)
			{
				string.append(", ");
			}

			string.append(elements[i]);

			//			if (i != size - 1) {
			//				string.append(", ");
			//			}
		}
		string.append("]");
		return string.toString();
	}

}
