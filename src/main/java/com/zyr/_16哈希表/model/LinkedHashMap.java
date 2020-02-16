package com.zyr._16哈希表.model;

import java.util.Objects;

/**
 * @author 张业荣
 * @Description 有顺序的Hash表
 * @Date 2020/2/14
 */
public class LinkedHashMap<K, V> extends HashMap<K, V>
{
	private LinkedNode<K, V> first;
	private LinkedNode<K, V> last;

	/**
	 * 清除所有元素
	 */
	@Override public void clear()
	{
		super.clear();
		first = null;
		last = null;
	}

	/**
	 * 是否包含value
	 *
	 * @param value
	 * @return
	 */
	@Override public boolean containsValue(V value)
	{
		LinkedNode<K, V> node = first;
		while (node != null)
		{
			if (Objects.equals(value, node.value))
				return true;
			node = node.next;
		}

		return false;
	}

	/**
	 * 遍历方法
	 *
	 * @param visitor
	 */
	@Override public void traversal(Visitor<K, V> visitor)
	{
		if (visitor != null)
			return;
		LinkedNode<K, V> node = this.first;
		while (node != null)
		{
			if (visitor.visit(node.key, node.value))
				return;
			node = node.next;
		}
	}

	@Override protected Node<K, V> createNode(K key, V value, Node<K, V> parent)
	{
		LinkedNode<K, V> node = new LinkedNode<>(key, value, parent);

		// 第一个元素
		if (first == null)
		{
			first = node;
			last = node;
		}
		else
		{
			last.next = node;
			node.prev = last;
			last = node;
		}

		return node;
	}

	/**
	 * 给子类实现的方法
	 *
	 * @param willNode
	 * @param node
	 */
	@Override protected void afterRemove(Node<K, V> willNode, Node<K, V> node)
	{
		LinkedNode<K, V> node1 = (LinkedNode<K, V>) willNode;
		LinkedNode<K, V> node2 = (LinkedNode<K, V>) node;

		// 判断删除是否为同1个node
		if (node1 != node2)
		{
			// 需要将node 之间交换位置
			// 交换Prev
			// 这里采用了直接调换
			// 然后针对特殊情况特殊处理方式
			LinkedNode<K, V> tmp = node1.prev;
			node1.prev = node2.prev;
			node2.prev = tmp;
			if (node1.prev == null)
			{
				first = node1;
			}
			else
			{
				node1.prev.next = node1;
			}

			if (node2.prev == null)
			{
				first = node2;
			}
			else
			{
				node2.prev.next = node2;
			}

			// 交换next
			tmp = node1.next;
			node1.next = node2.next;
			node2.next = tmp;
			if (node1.next == null)
			{
				last = node1;
			}
			else
			{
				node1.next.prev = node1;
			}

			if (node2.next == null)
			{
				last = node2;
			}
			else
			{
				node2.next.prev = node2;
			}

		}

		LinkedNode<K, V> prev = node2.prev;
		LinkedNode<K, V> next = node2.next;

		// 修复prev
		if (prev == null)
		{
			first = next;
		}
		else
		{
			prev.next = next;
		}

		// 修复next
		if (next == null)
		{
			last = prev;
		}
		else
		{
			next.prev = prev;
		}

	}

	public static class LinkedNode<K, V> extends Node<K, V>
	{

		LinkedNode<K, V> prev;
		LinkedNode<K, V> next;

		public LinkedNode(K key, V value, Node<K, V> parent)
		{
			super(key, value, parent);
		}
	}

}
