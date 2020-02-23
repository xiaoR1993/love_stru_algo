package com.zyr._14集合;

import com.zyr._08二叉搜索树.BinaryTree;
import com.zyr._13红黑树.RBTree;

import java.util.Comparator;

/**
 * @author 张业荣
 * @Description 使用二叉搜索树实现集合
 * @Date 2020/2/9
 */
public class TreeSet<E> implements Set<E>
{
	private RBTree<E> tree;

	public TreeSet()
	{
		this(null);
	}

	public TreeSet(Comparator<E> comparator)
	{
		tree = new RBTree<>(comparator);
	}

	/**
	 * 清除所有元素
	 */
	@Override public void clear()
	{
		tree.clear();
	}

	/**
	 * 元素的数量
	 *
	 * @return
	 */
	@Override public int size()
	{
		return tree.size();
	}

	/**
	 * 是否为空
	 *
	 * @return
	 */
	@Override public boolean isEmpty()
	{
		return tree.isEmpty();
	}

	/**
	 * 是否包含某个元素
	 *
	 * @param element
	 * @return
	 */
	@Override public boolean contains(E element)
	{
		return tree.contains(element);
	}

	/**
	 * 添加元素到尾部
	 *
	 * @param element
	 */
	@Override public void add(E element)
	{
		tree.add(element);
	}

	/**
	 * 删除指定元素
	 *
	 * @param element
	 */
	@Override public void remove(E element)
	{
		tree.remove(element);
	}

	@Override public void traversal(Visitor<E> visitor)
	{
		tree.inorder2(new BinaryTree.Visitor<E>()
		{
			@Override public boolean visit(E element)
			{
				return visitor.visit(element);
			}
		});
	}
}
