package com.zyr._05链表.single;

import com.zyr._05链表.AbstractList;

/**
 * @author 张业荣
 * @Description 链表
 * @Date 2020/1/5
 */
//单向链表 添加虚拟头节点
public class SingleLinkedList2<E> extends AbstractList<E>
{

	/**
	 * 第一个Node
	 */
	private Node<E> first;

	public SingleLinkedList2()
	{
		first = new Node<>(null, null);
	}

	//内部类 设置为private 只有内部能访问 不对外公开
	private static class Node<E>
	{
		E element;//当前元素

		Node<E> next;//下一个元素

		Node(E element, Node<E> next)
		{
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
		first.next = null;
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

		Node<E> prev = (index == 0) ? first : node(index - 1);
		//前一个Node指向新的Node 新的Node指向原来的索引Node
		prev.next = new Node<>(element, prev.next);

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

		Node<E> node = first;

		//普通情况
		Node<E> prev = (index == 0) ? first : node(index - 1);
		node = prev.next;
		prev.next = node.next;

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
		Node<E> node = first.next;
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
		Node<E> node = first.next;
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
		Node<E> node = first.next;
		for (int i = 0; i < index; i++)
		{
			node = node.next;
		}
		return node;

	}

}
