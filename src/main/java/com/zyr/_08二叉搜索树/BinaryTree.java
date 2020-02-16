package com.zyr._08二叉搜索树;

import com.zyr._08二叉搜索树.printer.BinaryTreeInfo;

import java.util.*;

/**
 * @author 张业荣
 * @Description 二叉树
 * @Date 2020/1/30
 */
@SuppressWarnings({ "unused", "unchecked" }) public class BinaryTree<E> implements BinaryTreeInfo
{
	// 主要屬性
	protected int size;
	protected BST.Node<E> root;

	// 内部类
	protected static class Node<E>
	{
		public E element;
		public Node<E> left;
		public Node<E> right;
		public Node<E> parent;

		public Node(E element, Node<E> parent)
		{
			this.element = element;
			this.parent = parent;
		}

		public boolean isLeaf()
		{
			return left == null && right == null;
		}

		public boolean hasTwoChildren()
		{
			return left != null && right != null;
		}

		public boolean isLeftChild()
		{
			return parent != null && this == parent.left;
		}

		public boolean isRightChild()
		{
			return parent != null && this == parent.right;
		}

		/**
		 * 返回兄弟节点
		 * @return
		 */
		public Node<E> sibling()
		{
			if (isLeftChild())
			{
				return parent.right;
			}
			if (isRightChild())
			{
				return parent.left;
			}

			return null;

		}

	}

	public static abstract class Visitor<E>
	{
		// 控制停止装置
		boolean stop;

		/**
		 * @return 如果返回true，就代表停止遍历
		 */
		public abstract boolean visit(E element);

	}

	protected Node<E> createNode(E element, Node<E> parent)
	{
		return new Node<>(element, parent);
	}

	// =============================== 常规方法  =============================== //

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
		root = null;
		size = 0;
	}

	// =============================== 二叉树遍历  =============================== //

	/**
	 * 前序遍历二叉树
	 *
	 * @param visitor
	 */
	public void preorder(Visitor<E> visitor)
	{
		if (visitor == null)
			return;

		preorder(root, visitor);

	}

	/**
	 * 使用迭代的手段来进行前序遍历
	 * 总结:前序遍历使用迭代方法 结合栈 全部节点入栈处理
	 *
	 * @param visitor
	 */
	public void preorderIter(Visitor<E> visitor)
	{
		//  使用栈
		Stack<Node> stack = new Stack<>();
		// 将根节点入栈
		stack.push(root);

		// 栈不为空则继续循环
		while (!stack.empty())
		{
			Node<E> node = stack.pop();
			// 元素为空
			if (node == null)
				continue;

			// 访问元素
			visitor.visit(node.element);

			// 右节点入栈
			if (node.right != null)
			{
				stack.push(node.right);
			}

			// 左节点入栈
			if (node.left != null)
			{
				stack.push(node.left);
			}

		}

	}

	/**
	 * 迭代版前序遍历
	 *
	 * @return
	 */
	public List<E> preorderTraversal()
	{
		// 矛盾点在于 左边和右边 如果有多层的情况的时候 左边是先进先出 而右边是需要等待 先进后出
		Queue<Node> leftQue = new LinkedList<>();
		Stack<Node> rightStack = new Stack<>();
		Queue<E> outque = new LinkedList<>();
		// 结果集转换
		List<E> list = new ArrayList<>();

		if (root == null)
			return list;

		leftQue.offer(root);

		while (!leftQue.isEmpty())
		{
			Node<E> node = leftQue.poll();

			if (node == null)
				continue;

			outque.offer(node.element);

			if (node.left != null)
				leftQue.offer(node.left);
			if (node.right != null)
				rightStack.push(node.right);

			if (leftQue.isEmpty() && !rightStack.isEmpty())
			{
				leftQue.offer(rightStack.pop());
			}
		}

		while (!outque.isEmpty())
		{
			list.add(outque.poll());
		}

		return list;
	}

	/**
	 * 使用迭代的手段来进行前序遍历
	 * 总结 访问本node 然后指针指向下个左 右节点入栈
	 *
	 * @param visitor
	 */
	public void preorderIter2(Visitor<E> visitor)
	{
		//  使用栈
		Stack<Node> stack = new Stack<>();

		Node<E> node = root;

		while (true)
		{
			if (node != null)
			{
				visitor.visit(node.element);

				stack.push(node.right);

				node = node.left;
			}
			else
			{
				if (stack.empty())
				{
					break;
				}
				else
				{
					node = stack.pop();
				}
			}

		}

	}

	/**
	 * 中序遍历
	 *
	 * @param visitor
	 */
	public void inorder(Visitor<E> visitor)
	{
		if (visitor == null)
			return;
		inorder(root, visitor);
	}

	/**
	 * 使用迭代的方式中序遍历二叉树
	 * 使用指针指向左 入栈本节点 节点访问完指针指向右
	 *
	 * @param visitor
	 */
	public void inorderIter(Visitor<E> visitor)
	{
		Stack<Node> stack = new Stack<>();

		Node<E> node = this.root;

		while (true)
		{

			// node不为null
			if (node != null)
			{
				// 先访问左 - 没办法 左边要一直到底
				stack.push(node);
				// 所以要存的是node  然后左边进行然后栈探出node
				node = node.left;
				//  右边子树的话 跟这个是一样的
			}

			if (node == null)
			{
				if (stack.empty())
					return;
				node = stack.pop();

				// 对node进行访问
				visitor.visit(node.element);

				node = node.right;
			}
		}

	}

	/**
	 * 使用迭代的方式完成中序遍历
	 *
	 * @return
	 */
	public List<E> inorderTraversal()
	{
		Stack<Node> mStack = new Stack<>();
		Queue<E> outque = new LinkedList<>();
		// 结果集转换
		List<E> list = new ArrayList<>();

		if (root == null)
			return list;

		Node<E> node = root;

		while (node != null || !mStack.isEmpty())
		{

			// 往左走
			while (node != null)
			{
				mStack.push(node);
				node = node.left;
			}

			// 最终node会为null 然后从栈中弹出
			node = mStack.pop();

			outque.add(node.element);

			node = node.right;
		}

		while (!outque.isEmpty())
		{
			list.add(outque.poll());
		}

		return list;

	}

	/**
	 * 后序遍历 使用递归
	 *
	 * @param visitor
	 */
	public void postorder(Visitor<E> visitor)
	{
		if (visitor == null)
			return;

		postorder(root, visitor);

	}

	/**
	 * 后序遍历 迭代形式
	 * 总结 不太懂
	 *
	 * @param visitor
	 */
	public void postorderIter(Visitor<E> visitor)
	{
		Stack<Node> stack = new Stack<>();

		Node<E> node = this.root;

		Node<E> lastVisit = node;

		stack.push(node);

		while (!stack.empty())
		{
			node = stack.peek();

			if (node == null)
			{
				stack.pop();
				continue;
			}
			if (node.isLeaf() || node.left == lastVisit || node.right == lastVisit)
			{
				Node<E> pop = stack.pop();
				visitor.visit(pop.element);
				lastVisit = pop;
			}
			else
			{
				stack.push(node.right);
				stack.push(node.left);
			}

		}

	}

	/**
	 * 层序遍历 非常重要 需要达到默写的地步
	 *
	 * @param visitor
	 */
	public void levelOrder(Visitor<E> visitor)
	{
		if (root == null || visitor == null)
			return;

		/**
		 * 思路:
		 * 		1.使用队列先进先出规律
		 * 		2.根节点入队
		 * 		3.队列不为空则继续循环
		 * 		4.节点访问 并左节点入队,右节点入队 条件是不为空
		 */
		// 使用链表版本的队列
		Queue<Node> queue = new LinkedList<>();

		queue.add(root);

		while (!queue.isEmpty())
		{

			Node<E> node = queue.poll();

			// 需要添加是否继续访问条件限制
			if (visitor.stop)
			{
				break;
			}
			// 获取数据

			// 访问 结果为false则退出
			if (visitor.visit(node.element))
				break;

			// 左节点不为空则入队
			if (node.left != null)
			{
				queue.offer(node.left);
			}

			// 右节点不为空则入队
			if (node.right != null)
			{
				queue.offer(node.right);
			}

		}

	}

	/**
	 * 计算二叉树的高度 使用迭代方法
	 *
	 * @return
	 */
	public int height()
	{
		/**
		 * 思路:
		 * 		1.迭代就是采用层序遍历
		 * 		2.需要记录到高度的话 就是从根节点开始
		 * 		3.然后需要记录到每层的元素个数
		 * 		4.当个数为0时 层树+1
		 */
		// 默写层序遍历
		if (root == null)
			return 0;
		int height = 0;
		int levelNum = 1;
		Queue<Node> queue = new LinkedList<>();

		queue.offer(root);

		while (!queue.isEmpty())
		{

			Node<E> node = queue.poll();

			// 操作
			levelNum--;
			if (levelNum == 0)
			{
				height++;
			}

			if (node.left != null)
			{
				queue.offer(node.left);
			}

			if (node.right != null)
			{
				queue.offer(node.right);
			}

			// 精髓在这里 就是队列的数量 就是下1层的节点数量
			if (levelNum == 0)
			{
				levelNum = queue.size();
			}

		}

		return height;

	}

	/**
	 * 判断是否为完全二叉树
	 *
	 * @return
	 */
	public boolean isComplete()
	{
		/**
		 * 思路:
		 * 		1.使用层序遍历
		 */
		if (root == null)
			return false;

		Queue<Node> queue = new LinkedList<>();

		queue.offer(root);

		boolean leaf = false;

		while (!queue.isEmpty())
		{

			Node<E> node = queue.poll();

			if (leaf && !node.isLeaf())
			{
				return false;
			}

			// 操作
			if (node.left != null)
			{
				queue.offer(node.left);
			}
			else if (node.right != null)
			{
				return false;
			}

			if (node.right != null)
			{
				queue.offer(node.right);
			}
			else
			{
				// 后面都是叶子节点
				leaf = true;
			}

		}

		return true;
	}

	/**
	 * 计算二叉树的高度 使用递归方法
	 *
	 * @return
	 */
	public int height2()
	{
		/**
		 * 思路
		 * 		1.其实就是前序递归方式
		 *
		 */
		return height2(root);

	}

	/**
	 * 找到前驱
	 *
	 * @param node
	 * @return
	 */
	public Node<E> predecessor(Node<E> node)
	{
		/**
		 * 思路:
		 * 		1.前驱节点是2种情况
		 * 			(1)在左子树的最右节点中
		 * 				node.left.right.right...
		 * 			(2)在parent 并且 node是parent的右节点 那么Parent就是前驱
		 * 		结合二叉树图会好看出来
		 */
		if (node == null)
			return null;

		// 在左子树的最右节点中
		//		node.left.right.right...
		if (node.left != null)
		{
			node = node.left;
			while (node.right != null)
			{
				node = node.right;
			}
			return node;
		}

		// 在parent 并且 node是parent的右节点 那么Parent就是前驱
		if (node.parent != null)
		{
			Node<E> parent = node.parent;
			while (parent != null)
			{
				if (parent.right == node)
				{
					return parent;
				}
				node = parent;
				parent = parent.parent;
			}

		}
		return null;

	}

	/**
	 * 查看节点的后继
	 *
	 * @param node
	 * @return
	 */
	public Node<E> successor(Node<E> node)
	{
		if (node == null)
			return null;

		// 在右节点的左子树中 node.right.left.left...
		if (node.right != null)
		{
			node = node.right;
			while (node.left != null)
			{
				node = node.left;
			}
			return node;
		}

		// 在parent中 且node.parent.left = node;
		if (node.parent != null)
		{

			Node<E> parent = node.parent;

			while (parent != null)
			{
				if (parent.left == node)
				{
					return parent;
				}
				node = parent;
				parent = parent.parent;
			}
		}

		return null;
	}

	// =============================== 打印方法  =============================== //

	/**
	 * who is the root node
	 */
	@Override public Object root()
	{
		return root;
	}

	/**
	 * how to get the left child of the node
	 *
	 * @param node
	 */
	@Override public Object left(Object node)
	{
		return ((BST.Node) node).left;
	}

	/**
	 * how to get the right child of the node
	 *
	 * @param node
	 */
	@Override public Object right(Object node)
	{
		return ((BST.Node) node).right;
	}

	/**
	 * how to print the node
	 *
	 * @param node
	 */
	@Override public Object string(Object node)
	{
//		return ((BST.Node) node).element;
		return node;
	}

	// =============================== 打印方法  =============================== //

	/**
	 * 前序遍历 加标识
	 *
	 * @param sb
	 * @param node
	 * @param prefix
	 */
	private void toString(StringBuilder sb, BST.Node<E> node, String prefix)
	{
		if (node == null)
			return;
		sb.append(prefix).append(node.element).append("\n");
		toString(sb, node.left, prefix + "{L}-");
		toString(sb, node.right, prefix + "{R}-");
	}

	@Override public String toString()
	{

		StringBuilder sb = new StringBuilder();
		toString(sb, root, "");
		return sb.toString();
	}

	// =============================== 私有方法  =============================== //

	/**
	 * 递归实现前序遍历
	 * 总结:前序遍历使用递归 比较简单 三段式
	 *
	 * @param node
	 * @param visitor
	 */
	private void preorder(Node<E> node, Visitor<E> visitor)
	{
		if (node == null || visitor.stop)
			return;

		visitor.stop = visitor.visit(node.element);
		preorder(node.left, visitor);
		preorder(node.right, visitor);
	}

	/**
	 * 递归实现中序遍历
	 *
	 * @param node
	 * @param visitor
	 */
	private void inorder(Node<E> node, Visitor<E> visitor)
	{
		if (node == null || visitor.stop)
			return;

		inorder(node.left, visitor);
		if (visitor.stop)
			return;
		visitor.stop = visitor.visit(node.element);
		inorder(node.right, visitor);

	}

	private void postorder(Node<E> node, Visitor<E> visitor)
	{
		if (node == null || visitor.stop)
			return;

		postorder(node.left, visitor);
		postorder(node.right, visitor);

		if (visitor.stop)
			return;

		visitor.stop = visitor.visit(node.element);

	}

	private int height2(Node<E> root)
	{
		// 空树
		if (root == null)
			return 0;

		// 关键点在于这个 其实是从最简单的开始 以及明白二叉树递归的遍历原理
		return 1 + Math.max(height2(root.left), height2(root.right));

	}

}
