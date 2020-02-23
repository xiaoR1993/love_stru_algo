package com.zyr._20Trie;

import com.zyr._16哈希表.model.HashMap;

/**
 * @author 张业荣
 * @Description 前缀树
 * @Date 2020/2/23
 */
public class Trie<V>
{
	private int size;
	private Node<V> root;

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
		size = 0;
		root = null;
	}

	public V get(String key)
	{
		Node<V> node = node(key);
		return node != null ? node.value : null;

	}

	public boolean contains(String key)
	{
		Node<V> node = node(key);
		return node != null;
	}

	public V add(String key, V value)
	{
		keyCheck(key);

		// 创建根节点
		if (root == null)
			root = new Node<V>(null);

		Node<V> node = root;

		for (char c : key.toCharArray())
		{
			boolean emptyChildren = node.children == null;
			Node<V> childNode = emptyChildren ? null : node.children.get(c);
			if (childNode == null)
			{
				childNode = new Node<V>(node);
				childNode.character = c;
				node.children = emptyChildren ? new HashMap<>() : node.children;
				node.children.put(c, childNode);
			}
			node = childNode;
		}

		// 已经存在这个单词
		if (node.word)
		{
			V oldValue = node.value;
			node.value = value;
			return oldValue;
		}

		// 新增一个单词
		node.word = true;
		node.value = value;
		size++;
		return null;

	}

	public V remove(String key)
	{
		// 找到最后一个节点
		Node<V> node = node(key);

		// 如果不是单词结尾，不用作任何处理
		if (node == null || !node.word)
			return null;

		size--;

		V oldValue = node.value;

		// 如果还有子节点
		if (node.children != null && !node.children.isEmpty())
		{
			node.word = false;
			node.value = null;
			return oldValue;
		}

		// 如果没有子节点
		Node<V> parent = null;
		while ((parent = node.parent) != null)
		{
			parent.children.remove(node.character);
			if (parent.word || !parent.children.isEmpty())
				break;
			node = parent;

		}

		return oldValue;

	}

	boolean startsWith(String prefix)
	{
		return node(prefix) != null;

	}

	// =============================== 私有方法 =============================== //

	/**
	 * 判断key是否非法
	 *
	 * @param key
	 */
	private void keyCheck(String key)
	{
		if (key == null || key.length() == 0)
		{
			throw new IllegalArgumentException("key must not be empty");
		}

	}

	/**
	 * 根据key找到这个节点
	 *
	 * @param key
	 * @return
	 */
	private Node<V> node(String key)
	{
		keyCheck(key);

		Node<V> node = root;

		for (char c : key.toCharArray())
		{
			if (node == null || node.children == null || node.children.isEmpty())
				return null;
			node = node.children.get(c);

		}

		return node;

	}

	private static class Node<V>
	{
		Node<V> parent;
		HashMap<Character, Node<V>> children;
		Character character;
		V value;
		boolean word; // 是否为单词的结尾(是否为1个完整的单词)

		public Node(Node<V> parent)
		{
			this.parent = parent;
		}
	}

}
