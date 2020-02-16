package com.zyr._13红黑树;

import com.zyr._08二叉搜索树.BST;

import java.util.Comparator;

/**
 * @author 张业荣
 * @Description 自平衡二叉搜索树
 * @Date 2020/2/8
 */
public class BBST<E> extends BST<E>
{
	public BBST()
	{
	}

	public BBST(Comparator<E> comparator)
	{
		super(comparator);
	}

	/**
	 * 左旋转
	 *
	 * @param grand
	 */
	protected void rotateLeft(Node<E> grand)
	{
		Node<E> parent = grand.right;
		Node<E> child = parent.left;
		grand.right = child;
		parent.left = grand;
		afterRotate(grand, parent, child);
	}

	/**
	 * 右旋转
	 *
	 * @param grand
	 */
	protected void rotateRight(Node<E> grand)
	{
		Node<E> parent = grand.left;
		Node<E> child = parent.right;
		grand.left = child;
		parent.right = grand;
		afterRotate(grand, parent, child);
	}

	/**
	 * 旋转后的更新操作
	 *
	 * @param grand
	 * @param parent
	 * @param child
	 */
	protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child)
	{
		// 让parent称为子树的根节点
		parent.parent = grand.parent;
		// 更新child的parent
		if (grand.isLeftChild())
		{
			grand.parent.left = parent;
		}
		else if (grand.isRightChild())
		{
			grand.parent.right = parent;
		}
		else
		{
			// grand是root节点
			root = parent;
		}

		// 更新child的parent
		if (child != null)
		{
			child.parent = grand;
		}

		// 更新grand的parent
		grand.parent = parent;

	}

	/**
	 * 统一所有情况的旋转
	 *
	 * @param r 子树的根节点
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @param f
	 */
	protected void rotate(Node<E> r, // 子树的根节点
			Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f)
	{

		// 让d成为这棵子树的根节点
		d.parent = r.parent;
		if (r.isLeftChild())
		{
			r.parent.left = d;
		}
		else if (r.isRightChild())
		{
			r.parent.right = d;
		}
		else
		{
			root = d;
		}

		//b-c
		b.right = c;
		if (c != null)
		{
			c.parent = b;
		}

		// e-f
		f.left = e;
		if (e != null)
		{
			e.parent = f;
		}

		// b-d-f
		d.left = b;
		d.right = f;
		b.parent = d;
		f.parent = d;

	}
}
