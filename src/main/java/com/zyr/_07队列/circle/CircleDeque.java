package com.zyr._07队列.circle;

/**
 * @author zyr
 * @Description 双向循环队列 使用动态数组实现的队列
 * @Date 2020/1/20
 */

/**
 * 因为有可能真实索引为负数的情况 需要进行转换
 * @param <E>
 */
public class CircleDeque<E>
{
	private int front;
	private int size;
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;

	public CircleDeque()
	{
		this(DEFAULT_CAPACITY);
	}

	public CircleDeque(int capaticy)
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
	public void enQueueFront(E element)
	{
		ensureCapacity(size + 1);
		front = index(-1);
		elements[front] = element;

		size++;

	}

	/**
	 * 从队尾入队
	 *
	 * @return
	 */
	public void enQueueRear(E element)
	{
		ensureCapacity(size + 1);
		elements[index(size)] = element;
		size++;
	}

	/**
	 * 从队尾出队
	 *
	 * @return
	 */
	public E deQueueRear()
	{
		int rear = rearIndex();
		E ele = elements[rear];
		elements[rear] = null;
		size--;
		return ele;
	}

	/**
	 * 从队头出队
	 *
	 * @return
	 */
	public E deQueueFront()
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
	 * 获取队尾的元素
	 *
	 * @return
	 */
	public E rear()
	{
		return elements[rearIndex()];
	}

	/**
	 * 由于队友会移动 获取真实的索引
	 *
	 * @param index
	 * @return
	 */
	private int index(int index)
	{
		// 这里是进行负值的处理
		index += front;
		if (index < 0)
		{
			return index + elements.length;
		}
		return index - (index >= elements.length ? elements.length : 0);
	}

	/**
	 * 获取队尾的真实索引
	 *
	 * @return
	 */
	private int rearIndex()
	{
		return index(size - 1);
	}

	/**
	 * 保证要有capacity的容量
	 *
	 * @param capacity
	 */
	private void ensureCapacity(int capacity)
	{
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity)
			return;

		// 新容量为旧容量的1.5倍
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++)
		{
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
