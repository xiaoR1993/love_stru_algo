package com.zyr._05链表.circle;

import com.zyr._05链表.AbstractList;

/**
 * @author 张业荣
 * @Description 链表
 * @Date 2020/1/5
 */
//双向循环链表
public class CircleLinkedList<E> extends AbstractList<E>
{

	/**
	 * 第一个Node
	 */
	private Node<E> first;
	private Node<E> last;

	//内部类 设置为private 只有内部能访问 不对外公开
	private static class Node<E>
	{
		E element;//当前元素

		Node<E> prev;//上一个元素
		Node<E> next;//下一个元素

		Node(Node<E> prev, E element, Node<E> next)
		{
			this.prev = prev;
			this.element = element;
			this.next = next;
		}
	}

	/**
	 * 清除所有元素
	 */
	@Override public void clear()
	{
		size = 0;
		first = null;
		last = null;
	}

	/**
	 * 获取index位置的元素
	 *
	 * @param index
	 * @return
	 */
	@Override public E get(int index)
	{
		return node(index).element;
	}

	/**
	 * 设置index位置的元素
	 *
	 * @param index
	 * @param element
	 * @return 原来的元素ֵ
	 */
	@Override public E set(int index, E element)
	{
		Node<E> node = node(index);
		E old = node.element;
		node.element = element;
		return old;
	}

	/**
	 * 在index位置插入一个元素
	 *
	 * @param index
	 * @param element
	 */
	@Override public void add(int index, E element)
	{
		super.rangeCheckForAdd(index);

		// 往最后1个添加
		if (index == size)
		{
			Node<E> oldLast = last;
			last = new Node<>(last, element, first);
			//只有1个元素
			if (oldLast == null)
			{
				first = last;
				first.next = first;
				first.prev = first;
			}
			else
			{
				oldLast.next = last;
				first.prev = last;
			}
		}
		else
		{
			// 普通情况
			// 获取前一个和下一个元素
			Node<E> next = node(index);
			Node<E> prev = next.prev;

			Node<E> current = new Node<>(prev, element, next);
			next.prev = current;

			prev.next = current;

			if (next == first)
			{
				first = current;
			}

			prev.next = current;

			if (next == first)
			{
				first = current;
			}

		}

		size++;

	}

	/**
	 * 删除index位置的元素
	 *
	 * @param index
	 * @return
	 */
	@Override public E remove(int index)
	{
		super.rangeCheck(index);

		// 普通情况
		Node<E> node = node(index);

		if (size == 1)
		{
			first = null;
			last = null;
		}
		else
		{
			Node<E> prev = node.prev;
			Node<E> next = node.next;
			prev.next = next;
			next.prev = prev;

			if (node == first)
			{
				first = next;
			}

			if (node == last)
			{
				last = prev;
			}

		}

		size--;

		return node.element;

	}

	/**
	 * 查看元素的索引
	 *
	 * @param element
	 * @return
	 */
	@Override public int indexOf(E element)
	{
		Node<E> node = first;
		//对象须要1注意空指针
		if (element == null)
		{
			for (int i = 0; i < size; i++)
			{
				if (first.element == null)
					return i;
				node = node.next;
			}
		}
		//element不为null
		for (int i = 0; i < size; i++)
		{
			if (element.equals(node.element))
				return i;
			node = node.next;
		}

		return ELEMENT_NOT_FOUND;

	}

	@Override public String toString()
	{
		// size=3, [99, 88, 77]
		StringBuilder string = new StringBuilder();
		Node<E> node = first;
		string.append("size=").append(size).append(", [");
		for (int i = 0; i < size; i++)
		{
			if (i != 0)
			{
				string.append(", ");
			}

			string.append(node.element);
			node = node.next;

			//			if (i != size - 1) {
			//				string.append(", ");
			//			}
		}
		string.append("]");
		return string.toString();
	}

	/**
	 * 获取指定索引处的Node
	 *
	 * @param index
	 * @return
	 */
	private Node<E> node(int index)
	{
		super.rangeCheck(index);
		//正序获取
		if (index < (size >> 1))
		{
			Node<E> node = first;
			for (int i = 0; i < index; i++)
			{
				node = node.next;
			}
			return node;
		}
		//反序获取
		else
		{
			Node<E> node = last;
			for (int i = size - 1; i > index; i--)
			{
				node = node.prev;
			}
			return node;

		}

	}

}
