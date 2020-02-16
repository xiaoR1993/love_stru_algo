package com.zyr._17二叉堆;

import java.util.Comparator;

/**
 * @author 张业荣
 * @Description 抽取堆
 * @Date 2020/2/15
 */
@SuppressWarnings("unchecked") public abstract class AbstractHeap<E> implements Heap<E>
{
	// 元素长度
	protected int size;
	// 比较器
	protected Comparator<E> comparator;

	// 构造器
	public AbstractHeap()
	{
		this(null);
	}

	public AbstractHeap(Comparator<E> comparator)
	{
		this.comparator = comparator;
	}

	@Override public int size()
	{
		return size;
	}

	@Override public boolean isEmpty()
	{
		return size == 0;
	}

	/**
	 * 返回比较结果
	 *
	 * @param e1
	 * @param e2
	 * @return
	 */
	protected int compare(E e1, E e2)
	{
		return comparator != null ? comparator.compare(e1, e2) : ((Comparable) e1).compareTo(e2);
	}

	protected void elementNotNullCheck(E element)
	{
		if (element == null)
		{
			throw new IllegalArgumentException("element must not be null");
		}
	}

}
