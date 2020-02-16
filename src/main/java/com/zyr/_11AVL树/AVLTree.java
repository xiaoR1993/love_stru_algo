package com.zyr._11AVL树;

import com.zyr._13红黑树.BBST;

import java.util.Comparator;

/**
 * @author 张业荣
 * @Description AVL树
 * @Date 2020/2/5
 */
public class AVLTree<E> extends BBST<E>
{
	public AVLTree()
	{
		this(null);
	}

	public AVLTree(Comparator<E> comparator)
	{
		super(comparator);
	}

	/**
	 * 给子类重写的方法
	 *
	 * @param node
	 */
	@Override protected void afterAdd(Node<E> node)
	{
		while ((node = node.parent) != null)
		{
			if (isBalanced(node))
			{
				// 更新高度
				updateHeight(node);
			}
			else
			{
				// 恢复平衡
				rebalance(node);
				break;
			}
		}

	}

	/**
	 * 旋转后的更新操作
	 *
	 * @param grand
	 * @param parent
	 * @param child
	 */
	@Override protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child)
	{
		super.afterRotate(grand, parent, child);
		// 更新高度
		updateHeight(grand);
		updateHeight(parent);

	}

	/**
	 * 删除node之后的调整
	 *
	 * @param node 被删除的节点
	 */
	@Override protected void afterRemove(Node<E> node)
	{
		while ((node = node.parent) != null)
		{
			if (isBalanced(node))
			{
				// 更新高度
				updateHeight(node);
			}
			else
			{
				// 恢复平衡
				rebalance(node);
			}
		}

	}

	/**
	 * 让节点平衡
	 *
	 * @param grand
	 */
	private void rebalance(Node<E> grand)
	{
		Node<E> parent = ((AVLNode<E>) grand).tallerChild();
		Node<E> node = ((AVLNode<E>) parent).tallerChild();
		if (parent.isLeftChild())
		{
			// L
			if (node.isLeftChild())
			{
				// LL
				rotate(grand, node, node.right, parent, parent.right, grand);
			}
			else
			{
				// LR
				rotate(grand, parent, node.left, node, node.right, grand);
			}
		}
		else
		{
			// R
			if (node.isLeftChild())
			{
				// RL
				rotate(grand, grand, node.left, node, node.right, parent);
			}
			else
			{
				// RR
				rotate(grand, grand, parent.left, parent, node.left, node);
			}
		}

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
	@Override protected void rotate(Node<E> r, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f)
	{
		super.rotate(r, b, c, d, e, f);
		// 更新高度
		updateHeight(b);
		updateHeight(f);
		updateHeight(d);


	}

	/**
	 * 恢复平衡
	 *
	 * @param grand 高度最低的那个不平衡节点
	 */
	private void rebalance2(Node<E> grand)
	{
		Node<E> parent = ((AVLNode<E>) grand).tallerChild();
		Node<E> node = ((AVLNode<E>) parent).tallerChild();
		if (parent.isLeftChild())
		{
			// L
			if (node.isLeftChild())
			{
				// LL
				rotateRight(grand);
			}
			else
			{
				// LR
				rotateLeft(parent);
				rotateRight(grand);
			}
		}
		else
		{
			// R
			if (node.isLeftChild())
			{
				// RL
				rotateRight(parent);
				rotateLeft(grand);
			}
			else
			{
				// RR
				rotateLeft(grand);
			}
		}

	}


	/**
	 * 更新节点高度
	 *
	 * @param node
	 */
	private void updateHeight(Node<E> node)
	{
		((AVLNode<E>) node).updateHeight();
	}

	/**
	 * 判断是否平衡
	 *
	 * @param node
	 * @return
	 */
	private boolean isBalanced(Node<E> node)
	{
		return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;

	}

	@Override protected Node<E> createNode(E element, Node<E> parent)
	{
		return new AVLNode<>(element, parent);
	}

	private static class AVLNode<E> extends Node<E>
	{
		// 高度
		int height = 1;

		public AVLNode(E element, Node<E> parent)
		{
			super(element, parent);
		}

		public int balanceFactor()
		{
			int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;

			return leftHeight - rightHeight;

		}

		public void updateHeight()
		{
			int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;

			height = 1 + Math.max(leftHeight, rightHeight);

		}

		public Node<E> tallerChild()
		{
			int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;

			if (leftHeight > rightHeight)
				return left;
			if (leftHeight < rightHeight)
				return right;

			return isLeftChild() ? left : right;

		}

		@Override public String toString()
		{
			String parentString = "null";
			if (parent != null)
			{
				parentString = parent.element.toString();
			}
			return element + "_p(" + parentString + ")_h(" + height + ")";
		}
	}

}
