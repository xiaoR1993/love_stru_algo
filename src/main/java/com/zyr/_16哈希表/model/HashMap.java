package com.zyr._16哈希表.model;

import com.zyr._08二叉搜索树.printer.BinaryTreeInfo;
import com.zyr._08二叉搜索树.printer.BinaryTrees;
import com.zyr._15映射.Map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * @author 张业荣
 * @Description 哈希表
 * @Date 2020/2/13
 */
@SuppressWarnings("all") public class HashMap<K, V> implements Map<K, V>
{
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	private int size;
	// 存储设置元素的桶 直接使用红黑树的根基点 因此是红黑树根节点的数组
	private Node<K, V>[] table;
	// 有1个初始化的数组大小
	private static final int DEFAULT_CAPACITY = 1 << 4;
	// load factor 装载因子
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	// 构造器
	public HashMap()
	{
		table = new Node[DEFAULT_CAPACITY];
	}

	/**
	 * 清除所有元素
	 */
	@Override public void clear()
	{
		size = 0;
		for (Node<K, V> node : table)
		{
			node = null;
		}

	}

	/**
	 * 元素的数量
	 *
	 * @return
	 */
	@Override public int size()
	{
		return size;
	}

	/**
	 * 是否为空
	 *
	 * @return
	 */
	@Override public boolean isEmpty()
	{
		return size == 0;
	}

	/**
	 * put数据
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	@Override public V put(K key, V value)
	{
		// 扩容判断
		resize();

		// 使用hash函数算出key所在的索引
		int index = index(key);

		Node<K, V> root = table[index];

		// 添加第一个节点
		if (root == null)
		{
			root = createNode(key, value, null);
			table[index] = root;
			size++;
			fixAfterPut(root);
			return null;
		}

		// 添加的不是第一个节点
		// 找到父节点
		Node<K, V> parent = root;
		Node<K, V> node = root;
		int cmp = 0;

		K k1 = key;
		int h1 = hash(k1);
		Node<K, V> result = null;
		boolean search = false;
		do
		{
			parent = node;
			// 需要通过一系列的判断来确定放在位置
			K k2 = node.key;
			int h2 = hash(k2);
			if (h1 > h2)
			{
				cmp = 1;
			}
			else if (h1 < h2)
			{
				cmp = -1;
			}
			//判断相等
			else if (Objects.equals(k1, k2))
			{
				cmp = 0;
			}
			else if (k1 != null && k2 != null && k1.getClass() == k2.getClass() && k1 instanceof Comparable
					&& (cmp = ((Comparable) k1).compareTo(k2)) != 0)
			{
			}
			// 扫描
			else if (search)
			{
				cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
			}
			else
			{
				if ((node.left != null && ((result = node(node.left, k1)) != null)) || (node.right != null && (
						(result = node(node.right, k1)) != null)))
				{
					node = result;
					cmp = 0;
				}
				else
				{
					search = true;
					cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
				}
			}

			if (cmp > 0)
			{
				node = node.right;
			}
			else if (cmp < 0)
			{
				node = node.left;
			}
			else
			{
				// 相等
				V oldValue = node.value;
				node.key = key;
				node.value = value;
				node.hash = h1;
				return oldValue;
			}
		}
		while (node != null);

		// 看看插入到父节点的哪个位置
		Node<K, V> newNode = createNode(key, value, parent);
		if (cmp > 0)
		{
			parent.right = newNode;
		}
		else
		{
			parent.left = newNode;
		}
		size++;
		// 新添加节点之后的处理
		fixAfterPut(newNode);
		return null;

	}

	/**
	 * 获取value
	 *
	 * @param key
	 * @return
	 */
	@Override public V get(K key)
	{
		int index = index(key);
		Node<K, V> root = table[index];
		Node<K, V> node = node(root, key);
		return node != null ? node.value : null;
	}

	/**
	 * 根据key删除数据
	 *
	 * @param key
	 * @return
	 */
	@Override public V remove(K key)
	{
		return remove(node(key));
	}

	/**
	 * 是否包含key
	 *
	 * @param key
	 * @return
	 */
	@Override public boolean containsKey(K key)
	{
		return node(key) != null;
	}

	/**
	 * 是否包含value
	 *
	 * @param value
	 * @return
	 */
	@Override public boolean containsValue(V value)
	{
		if (size == 0)
			return false;
		LinkedList<Node<K, V>> queue = new LinkedList<>();
		for (Node<K, V> root : table)
		{
			if (root == null)
				continue;

			queue.offer(root);
			while (!queue.isEmpty())
			{
				Node<K, V> node = queue.poll();
				if (Objects.equals(value, node.value))
					return true;

				if (node.left != null)
				{
					queue.offer(node.left);
				}

				if (node.right != null)
				{
					queue.offer(node.right);
				}
			}
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
		if (size == 0 || visitor == null)
			return;
		Queue<Node<K, V>> queue = new LinkedList<>();

		for (Node<K, V> root : table)
		{
			if (root == null)
				continue;
			queue.offer(root);

			while (!queue.isEmpty())
			{

				Node<K, V> node = queue.poll();

				if (visitor.visit(node.key, node.value))
					return;

				if (node.left != null)
				{
					queue.offer(node.left);
				}

				if (node.right != null)
				{
					queue.offer(node.right);
				}
			}

		}
	}

	public void print()
	{
		if (size == 0)
			return;
		for (int i = 0; i < table.length; i++)
		{
			final Node<K, V> root = table[i];
			System.out.println("【index = " + i + "】");
			BinaryTrees.println(new BinaryTreeInfo()
			{
				@Override public Object string(Object node)
				{
					return node;
				}

				@Override public Object root()
				{
					return root;
				}

				@Override public Object right(Object node)
				{
					return ((Node<K, V>) node).right;
				}

				@Override public Object left(Object node)
				{
					return ((Node<K, V>) node).left;
				}
			});
			System.out.println("---------------------------------------------------");
		}
	}

	// 内部类
	protected static class Node<K, V>
	{
		K key;
		V value;
		int hash;
		boolean color = RED;
		public Node<K, V> left;
		public Node<K, V> right;
		public Node<K, V> parent;

		public Node(K key, V value, Node<K, V> parent)
		{
			this.key = key;
			this.value = value;
			int hash = key == null ? 0 : key.hashCode();
			this.hash = hash ^ (hash >>> 16);
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
		 *
		 * @return
		 */
		public Node<K, V> sibling()
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

		@Override public String toString()
		{
			String str = "";
			if (color == RED)
			{
				str = "R_";
			}
			return str + key.toString() + "_" + value.toString();
		}

	}

	protected Node<K, V> createNode(K key, V value, Node<K, V> parent)
	{
		return new Node<>(key, value, parent);
	}

	// =============================== 私有方法 =============================== //

	private int hash(K key)
	{
		if (key == null)
			return 0;
		int h = key.hashCode();
		return h ^ (h >>> 16);

	}

	/**
	 * 求key的索引
	 *
	 * @param key
	 * @return
	 */
	private int index(K key)
	{
		return hash(key) & (table.length - 1);
	}

	private int index(Node<K, V> node)
	{
		return node.hash & (table.length - 1);

	}

	/**
	 * 扩容判断
	 */
	private void resize()
	{
		// 装载判断 size/table.length
		if (size / table.length <= DEFAULT_LOAD_FACTOR)
			return;

		// 原来的table
		Node[] oldTable = table;
		// 申请新的内存
		table = new Node[table.length << 1];

		// 移动过去
		// 层序遍历table
		Queue<Node<K, V>> queue = new LinkedList<>();
		for (Node<K, V> root : oldTable)
		{
			if (root == null)
				continue;

			queue.offer(root);

			while (!queue.isEmpty())
			{

				Node<K, V> node = queue.poll();

				if (node.left != null)
				{
					queue.offer(node.left);
				}

				if (node.right != null)
				{
					queue.offer(node.right);
				}

				// 移动node
				moveNode(node);
			}

		}

	}

	/**
	 * 移动Node
	 *
	 * @param newNode
	 */
	private void moveNode(Node<K, V> newNode)
	{
		// 重置
		newNode.parent = null;
		newNode.left = null;
		newNode.right = null;
		newNode.color = RED;

		int index = index(newNode);
		// 取出index位置的红黑树根节点
		Node<K, V> root = table[index];
		if (root == null)
		{
			root = newNode;
			table[index] = root;
			fixAfterPut(root);
			return;
		}

		// 添加新的节点到红黑树上面
		Node<K, V> parent = root;
		Node<K, V> node = root;
		int cmp = 0;
		K k1 = newNode.key;
		int h1 = newNode.hash;
		do
		{
			parent = node;
			K k2 = node.key;
			int h2 = node.hash;
			if (h1 > h2)
			{
				cmp = 1;
			}
			else if (h1 < h2)
			{
				cmp = -1;
			}
			else if (k1 != null && k2 != null && k1 instanceof Comparable && k1.getClass() == k2.getClass()
					&& (cmp = ((Comparable) k1).compareTo(k2)) != 0)
			{
			}
			else
			{
				cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
			}

			if (cmp > 0)
			{
				node = node.right;
			}
			else if (cmp < 0)
			{
				node = node.left;
			}
		}
		while (node != null);

		// 看看插入到父节点的哪个位置
		newNode.parent = parent;
		if (cmp > 0)
		{
			parent.right = newNode;
		}
		else
		{
			parent.left = newNode;
		}

		// 新添加节点之后的处理
		fixAfterPut(newNode);

	}

	/**
	 * 给子类重写的方法
	 *
	 * @param node
	 */
	private void fixAfterPut(Node<K, V> node)
	{
		Node<K, V> parent = node.parent;
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
		Node<K, V> uncle = parent.sibling();
		// 祖父节点
		Node<K, V> grand = parent.parent;
		// 提取 下面所有代码都需要将grand染成红色
		red(grand);
		// 叔父节点是红色【B树节点上溢】
		if (isRed(uncle))
		{
			black(parent);
			black(uncle);
			// 把祖父节点当做是新添加的节点
			fixAfterPut(grand);
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
	 * 判断节点黑色
	 *
	 * @param node
	 * @return
	 */
	private boolean isBlack(Node<K, V> node)
	{

		return colorOf(node) == BLACK;

	}

	/**
	 * 判断节点红色
	 *
	 * @param node
	 * @return
	 */
	private boolean isRed(Node<K, V> node)
	{

		return colorOf(node) == RED;

	}

	/**
	 * 获取节点颜色
	 *
	 * @param node
	 * @return
	 */
	private boolean colorOf(Node<K, V> node)
	{

		return node == null ? BLACK : ((Node<K, V>) node).color;

	}

	/**
	 * 节点染色
	 *
	 * @param node
	 * @param color
	 * @return
	 */
	private Node<K, V> color(Node<K, V> node, boolean color)
	{
		if (node != null)
			((Node<K, V>) node).color = color;
		return (Node<K, V>) node;
	}

	/**
	 * 节点染成黑色
	 *
	 * @param node
	 * @return
	 */
	private Node<K, V> black(Node<K, V> node)
	{
		return color(node, BLACK);
	}

	/**
	 * 节点染成红色
	 *
	 * @param node
	 * @return
	 */
	private Node<K, V> red(Node<K, V> node)
	{
		return color(node, RED);
	}

	/**
	 * 左旋转
	 *
	 * @param grand
	 */
	protected void rotateLeft(Node<K, V> grand)
	{
		Node<K, V> parent = grand.right;
		Node<K, V> child = parent.left;
		grand.right = child;
		parent.left = grand;
		afterRotate(grand, parent, child);
	}

	/**
	 * 右旋转
	 *
	 * @param grand
	 */
	protected void rotateRight(Node<K, V> grand)
	{
		Node<K, V> parent = grand.left;
		Node<K, V> child = parent.right;
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
	protected void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child)
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
			table[index(grand)] = parent;
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
	protected void rotate(Node<K, V> r, // 子树的根节点
			Node<K, V> b, Node<K, V> c, Node<K, V> d, Node<K, V> e, Node<K, V> f)
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
			table[index(r)] = d;
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

	private Node<K, V> node(K key)
	{
		int index = index(key);
		Node<K, V> root = table[index];
		return root == null ? null : node(root, key);
	}

	/**
	 * 扫描并根据key获取节点
	 *
	 * @param node
	 * @param k1
	 * @return
	 */
	private Node<K, V> node(Node<K, V> node, K k1)
	{
		int h1 = hash(k1);
		// 存储查找结果
		Node<K, V> result = null;
		int cmp = 0;
		while (node != null)
		{
			K k2 = node.key;
			int h2 = node.hash;
			if (h1 > h2)
			{
				node = node.right;
			}
			else if (h1 < h2)
			{
				node = node.left;
			}
			else if (Objects.equals(k1, k2))
			{
				return node;
			}
			else if (k1 != null && k2 != null && k1.getClass().equals(k2.getClass()) && k1 instanceof Comparable && (
					(cmp = ((Comparable) k1).compareTo(k2)) != 0))
			{
				node = cmp > 0 ? node.right : node.left;
			}
			else if (node.right != null && ((result = node(node.right, k1)) != null))
			{
				return result;
			}
			else
			{
				node = node.left;
			}
		}

		return node;
	}

	/**
	 * 删除节点
	 *
	 * @param node
	 */
	private V remove(Node<K, V> node)
	{
		if (node == null)
			return null;
		size--;

		V oldValue = node.value;

		Node<K,V> willNode = node;

		// 度为2的节点
		if (node.hasTwoChildren())
		{
			// 找到后继节点
			Node<K, V> s = successor(node);

			// 覆盖至
			node.key = s.key;
			node.value = s.value;
			node.hash = s.hash;

			// 删除后继节点
			node = s;
		}

		// 删除node节点(node的度必然是1或者0)
		Node<K, V> replacement = node.left != null ? node.left : node.right;

		if (replacement != null)
		{ // 度为1的节点
			// 更改parent
			replacement.parent = node.parent;
			// 更改parent的left、right的指向
			if (node.parent == null)
			{ // node是度为1的节点并且是根节点
				table[index(node)] = replacement;
			}
			else if (node == node.parent.left)
			{ // 是左子节点
				node.parent.left = replacement;
			}
			else
			{ // 是右子节点
				node.parent.right = replacement;
			}

			// 删除节点之后的处理
			fixAfterRemove(replacement);
		}
		else if (node.parent == null)
		{ // node是叶子节点并且是根节点
			table[index(node)] = null;

			// 删除节点之后的处理
			fixAfterRemove(node);
		}
		else
		{ // node是叶子节点,但不是根节点
			if (node == node.parent.left)
			{
				node.parent.left = null;
			}
			else
			{
				node.parent.right = null;
			}

			// 删除节点之后的处理
			fixAfterRemove(node);
		}

		afterRemove(willNode, node);

		return oldValue;

	}

	/**
	 * 给子类实现的方法
	 * @param willNode
	 * @param node
	 */
	protected void afterRemove(Node<K, V> willNode, Node<K, V> node)
	{
	}

	/**
	 * 查看节点的后继
	 *
	 * @param node
	 * @return
	 */
	public Node<K, V> successor(Node<K, V> node)
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

			Node<K, V> parent = node.parent;

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

	/**
	 * 删除node之后的调整
	 *
	 * @param node 被删除的节点
	 */
	private void fixAfterRemove(Node<K, V> node)
	{
		// 如果删除的节点是红色
		if (isRed(node))
		{
			black(node);
			return;
		}

		Node<K, V> parent = node.parent;
		// 删除的是根节点
		if (parent == null)
			return;

		// 删除的是黑色叶子节点【下溢】
		// 判断被删除的node是左还是右
		boolean left = parent.left == null || node.isLeftChild();
		Node<K, V> sibling = left ? parent.right : parent.left;
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
					fixAfterRemove(parent);
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
					fixAfterRemove(parent);
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

}
