package com.zyr._17二叉堆;

import com.zyr._08二叉搜索树.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author 张业荣
 * @Description 二叉堆 最大堆
 * @Date 2020/2/15
 */
@SuppressWarnings("unchecked") public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo
{

	// 数组
	public E[] elements;

	private static final int DEFAULT_CAPACITY = 10;

	// 构造器
	public BinaryHeap()
	{
		this(null, null);
	}

	public BinaryHeap(Comparator<E> comparator)
	{
		this(null, comparator);
	}

	public BinaryHeap(E[] elements, Comparator<E> comparator)
	{
		super(comparator);
		if (elements == null || elements.length == 0)
		{
			this.elements = (E[]) new Object[DEFAULT_CAPACITY];
		}
		else
		{
			int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
			this.elements = (E[]) new Object[capacity];
			this.size = elements.length;
			for (int i = 0; i < elements.length; i++)
			{
				this.elements[i] = elements[i];
			}

			// 批量建堆
			heapify();

		}

	}

	public BinaryHeap(E[] elements)
	{
		this(elements, null);
	}

	@Override public void clear()
	{
		size = 0;
		for (E element : elements)
		{
			element = null;
		}

	}

	@Override public void add(E element)
	{
		elementNotNullCheck(element);
		// 扩容判断
		ensureCapacity(size + 1);
		elements[size] = element;
		size++;
		// 上滤
		siftUp(size - 1);
	}

	@Override public E get()
	{
		return elements[0];
	}

	@Override public E remove()
	{
		// 用最后的元素替换头元素
		E element = elements[0];
		elements[0] = elements[size - 1];
		// 尾部元素删除
		elements[--size] = null;
		// 下滤
		siftDown(0);
		return element;
	}

	@Override public E replace(E element)
	{
		elementNotNullCheck(element);
		E top = null;
		if (size == 0)
		{
			elements[size++] = element;
		}
		else
		{
			top = elements[0];
			elements[0] = element;
			siftDown(0);
		}
		return top;
	}

	/**
	 * 返回从小到大的排序数据
	 *
	 * @return
	 */
	public E[] sort2()
	{
		// 构造1个数组 与原来的数组同序
		// 然后对最大堆进行获取元素 放进新的数组
		// 直到所有数据取出
		// 这个的空间复杂度是根数据量有关的 O(n)
		E[] arr = (E[]) new Object[size];
		for (int i = size - 1; i >= 0; i--)
		{
			arr[i] = remove();
		}
		return arr;
	}

	/**
	 * 返回从小到大的排序数据
	 *
	 * @return
	 */
	public void sort()
	{
		// 构造1个数组 与原来的数组同序
		// 然后对最大堆进行获取元素 放进新的数组
		// 直到所有数据取出
		// 这个的空间复杂度是根数据量有关的 O(n)
		for (int i = size - 1; i >= 0; i--)
		{
			E tmp = elements[i];
			elements[i] = elements[0];
			elements[0] = tmp;
			size --;
			siftDown(0);
		}
		// 空间复杂度位O(1)级别
		size = elements.length;
	}

	/**
	 * who is the root node
	 */
	@Override public Object root()
	{
		return 0;

	}

	/**
	 * how to get the left child of the node
	 *
	 * @param node
	 */
	@Override public Object left(Object node)
	{
		int index = ((int) node << 1) + 1;
		return index >= size ? null : index;

	}

	/**
	 * how to get the right child of the node
	 *
	 * @param node
	 */
	@Override public Object right(Object node)
	{
		int index = ((int) node << 1) + 2;
		return index >= size ? null : index;

	}

	@Override public Object string(Object node)
	{
		return elements[(int) node];
	}

	// =============================== 私有方法 =============================== //

	/**
	 * 批量建堆
	 */
	private void heapify()
	{
		// 自上而下的上滤
		//		for(int i =1;i<size;i++){
		//			siftUp(i);
		//		}

		// 自下而上的下滤
		for (int i = (size >> 1) - 1; i >= 0; i--)
		{
			siftDown(i);
		}
	}

	/**
	 * 上滤
	 *
	 * @param index
	 */
	private void siftUp(int index)
	{
		// 取出元素
		E element = elements[index];
		// 与父节点判断大小
		while (index != 0)
		{
			// 取出父节点
			int parentIndex = (index - 1) >> 1;
			E parent = elements[parentIndex];
			// 进行比较
			// 子节点<=父节点 则停止
			if (compare(element, parent) <= 0)
				break;
			// 将父节点的值放进子节点里面
			elements[index] = parent;
			index = parentIndex;
		}

		// 结束之后 将目标值放进对应索引
		elements[index] = element;

	}

	/**
	 * 下滤
	 *
	 * @param index
	 */
	private void siftDown(int index)
	{
		// 取出元素
		E element = elements[index];
		// 判断条件为index没有子节点或者不比子节点小
		// 没有子节点的根据是那个完全二叉树的叶子节点的前1个
		// 叶子节点的开头索引
		int leafIndex = (size >> 1);
		while (index < leafIndex)
		{
			// 左子节点
			int childIndex = (index << 1) + 1;
			E child = elements[childIndex];
			// 需要判断左子节点和右子节点哪个大
			int rightIndex = childIndex + 1;
			if (rightIndex < size && (compare(child, elements[rightIndex]) < 0))
			{
				child = elements[childIndex = rightIndex];
			}

			if (compare(element, child) >= 0)// 不比子节点小
				break;

			elements[index] = child;
			index = childIndex;
		}

		elements[index] = element;

	}

	/**
	 * 保证要有capacity的容量
	 *
	 * @param capacity
	 */
	private void ensureCapacity(int capacity)
	{
		//扩容的条件判断
		if (elements.length <= capacity)
		{
			//容量
			int newCpacity = capacity + (capacity >> 1);
			//新数组
			E[] newElements = (E[]) new Objects[newCpacity];
			//替换旧数组数据到新数据
			for (int i = 0; i < elements.length; i++)
			{
				newElements[i] = elements[i];
			}
			elements = newElements;
		}
	}
}
