package com.zyr._13红黑树;

import java.util.Comparator;

/**
 * @author 张业荣
 * @Description 红黑树
 * @Date 2020/2/7
 */
public class RBTree<E> extends BBST<E>
{
	private static final boolean RED = false;
	private static final boolean BLACK = true;

	public RBTree()
	{
	}

	public RBTree(Comparator<E> comparator)
	{
		super(comparator);
	}

	@Override protected Node<E> createNode(E element, Node<E> parent)
	{
		return new RBNode<>(element, parent);
	}

	/**
	 * 给子类重写的方法
	 *
	 * @param node
	 */
	@Override protected void afterAdd(Node<E> node)
	{
		Node<E> parent = node.parent;
		// 添加的是根节点 或者 上溢到达了根节点
		if (parent == null)
		{
			black(node);
			return;
		}

		// 如果父节点是黑色，直接返回
		if (isBlack(parent))
			return;

		// 叔父节点
		Node<E> uncle = parent.sibling();
		// 祖父节点
		Node<E> grand = parent.parent;
		// 提取 下面所有代码都需要将grand染成红色
		red(grand);
		// 叔父节点是红色【B树节点上溢】
		if (isRed(uncle))
		{
			black(parent);
			black(uncle);
			// 把祖父节点当做是新添加的节点
			afterAdd(grand);
			return;
		}

		// 叔父节点不是红色
		if (parent.isLeftChild())
		{
			// L
			if (node.isLeftChild())
			{
				// LL
				black(parent);
			}
			else
			{
				// LR
				black(node);
				rotateLeft(parent);
			}
			rotateRight(grand);
		}
		else
		{
			// R
			if (node.isLeftChild())
			{
				// RL
				black(node);
				rotateRight(parent);
			}
			else
			{
				// RR
				black(parent);
			}
			rotateLeft(grand);

		}

	}

	/**
	 * 删除node之后的调整
	 *
	 * @param node 被删除的节点
	 */
	@Override protected void afterRemove(Node<E> node)
	{
		// 如果删除的节点是红色
		if (isRed(node))
		{
			black(node);
			return;
		}

		Node<E> parent = node.parent;
		// 删除的是根节点
		if (parent == null)
			return;

		// 删除的是黑色叶子节点【下溢】
		// 判断被删除的node是左还是右
		boolean left = parent.left == null || node.isLeftChild();
		Node<E> sibling = left ? parent.right : parent.left;
		// 被删除的节点在左边，兄弟节点在右边
		if (left)
		{
			if (isRed(sibling))
			{
				// 兄弟节点是红色
				black(sibling);
				red(parent);
				rotateLeft(parent);
				// 更换兄弟
				sibling = parent.right;
			}

			// 兄弟节点必然是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right))
			{
				// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				// parent变黑
				black(parent);
				// 兄弟节点变红
				red(sibling);
				if (parentBlack)
				{
					afterRemove(parent);
				}
			}
			else
			{
				// 兄弟节点至少有1个红色子节点，向兄弟节点借元素
				// 兄弟节点的左边是黑色，兄弟要先旋转
				if (isBlack(sibling.right))
				{
					rotateRight(sibling);
					sibling = parent.right;
				}

				color(sibling, colorOf(parent));
				black(sibling.right);
				black(parent);
				rotateLeft(parent);

			}

		}
		else
		{
			// 被删除的节点在右边，兄弟节点在左边
			if (isRed(sibling))
			{
				// 兄弟节点是红色
				black(sibling);
				red(parent);
				rotateRight(parent);
				// 更换兄弟
				sibling = parent.left;
			}

			// 兄弟节点必然是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right))
			{
				// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				// parent变黑
				black(parent);
				// 兄弟节点变红
				red(sibling);
				if (parentBlack)
				{
					afterRemove(parent);
				}
			}
			else
			{
				// 兄弟节点至少有1个红色子节点，向兄弟节点借元素
				// 兄弟节点的左边是黑色，兄弟要先旋转
				if (isBlack(sibling.left))
				{
					rotateLeft(sibling);
					sibling = parent.left;
				}

				color(sibling, colorOf(parent));
				black(sibling.left);
				black(parent);
				rotateRight(parent);

			}

		}

	}

	/**
	 * 删除node之后的调整
	 *
	 * @param node 被删除的节点
	 */
	//	@Override
	// 拥有replacement的删除
//	protected void afterRemove(Node<E> node, Node<E> replacement)
//	{
//		// 如果删除的节点是红色
//		if (isRed(node))
//			return;
//
//		// 用以取代node的子节点是红色
//		if (isRed(replacement))
//		{
//			black(replacement);
//			return;
//		}
//
//		Node<E> parent = node.parent;
//		// 删除的是根节点
//		if (parent == null)
//			return;
//
//		// 删除的是黑色叶子节点【下溢】
//		// 判断被删除的node是左还是右
//		boolean left = parent.left == null || node.isLeftChild();
//		Node<E> sibling = left ? parent.right : parent.left;
//		// 被删除的节点在左边，兄弟节点在右边
//		if (left)
//		{
//			if (isRed(sibling))
//			{
//				// 兄弟节点是红色
//				black(sibling);
//				red(parent);
//				rotateLeft(parent);
//				// 更换兄弟
//				sibling = parent.right;
//			}
//
//			// 兄弟节点必然是黑色
//			if (isBlack(sibling.left) && isBlack(sibling.right))
//			{
//				// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
//				boolean parentBlack = isBlack(parent);
//				// parent变黑
//				black(parent);
//				// 兄弟节点变红
//				red(sibling);
//				if (parentBlack)
//				{
//					afterRemove(parent, null);
//				}
//			}
//			else
//			{
//				// 兄弟节点至少有1个红色子节点，向兄弟节点借元素
//				// 兄弟节点的左边是黑色，兄弟要先旋转
//				if (isBlack(sibling.right))
//				{
//					rotateRight(sibling);
//					sibling = parent.right;
//				}
//
//				color(sibling, colorOf(parent));
//				black(sibling.right);
//				black(parent);
//				rotateLeft(parent);
//
//			}
//
//		}
//		else
//		{
//			// 被删除的节点在右边，兄弟节点在左边
//			if (isRed(sibling))
//			{
//				// 兄弟节点是红色
//				black(sibling);
//				red(parent);
//				rotateRight(parent);
//				// 更换兄弟
//				sibling = parent.left;
//			}
//
//			// 兄弟节点必然是黑色
//			if (isBlack(sibling.left) && isBlack(sibling.right))
//			{
//				// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
//				boolean parentBlack = isBlack(parent);
//				// parent变黑
//				black(parent);
//				// 兄弟节点变红
//				red(sibling);
//				if (parentBlack)
//				{
//					afterRemove(parent, null);
//				}
//			}
//			else
//			{
//				// 兄弟节点至少有1个红色子节点，向兄弟节点借元素
//				// 兄弟节点的左边是黑色，兄弟要先旋转
//				if (isBlack(sibling.left))
//				{
//					rotateLeft(sibling);
//					sibling = parent.left;
//				}
//
//				color(sibling, colorOf(parent));
//				black(sibling.left);
//				black(parent);
//				rotateRight(parent);
//
//			}
//
//		}
//
//	}

	/**
	 * 判断节点黑色
	 *
	 * @param node
	 * @return
	 */
	private boolean isBlack(Node<E> node)
	{

		return colorOf(node) == BLACK;

	}

	/**
	 * 判断节点红色
	 *
	 * @param node
	 * @return
	 */
	private boolean isRed(Node<E> node)
	{

		return colorOf(node) == RED;

	}

	/**
	 * 获取节点颜色
	 *
	 * @param node
	 * @return
	 */
	private boolean colorOf(Node<E> node)
	{

		return node == null ? BLACK : ((RBNode<E>) node).color;

	}

	/**
	 * 节点染色
	 *
	 * @param node
	 * @param color
	 * @return
	 */
	private RBNode<E> color(Node<E> node, boolean color)
	{
		if (node != null)
			((RBNode<E>) node).color = color;
		return (RBNode<E>) node;
	}

	/**
	 * 节点染成黑色
	 *
	 * @param node
	 * @return
	 */
	private RBNode<E> black(Node<E> node)
	{
		return color(node, BLACK);
	}

	/**
	 * 节点染成红色
	 *
	 * @param node
	 * @return
	 */
	private RBNode<E> red(Node<E> node)
	{
		return color(node, RED);
	}

	private static class RBNode<E> extends Node<E>
	{
		boolean color = RED;

		public RBNode(E element, Node<E> parent)
		{
			super(element, parent);
		}

		@Override public String toString()
		{
			String str = "";
			if (color == RED)
			{
				str = "R_";
			}
			return str + element.toString();
		}

	}

}
