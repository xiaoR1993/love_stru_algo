package com.zyr._08二叉搜索树;

import com.zyr._08二叉搜索树.printer.BinaryTrees;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author zyr
 * @Description
 * @Date 2020/1/21
 */
public class Main
{
	private static List<Integer> list;
	private static BST<Integer> bsk;

	static
	{
		list = Arrays.asList(19, 5, 8, 71, 34, 73, 30, 98, 83, 69, 63, 42, 44, 62);
		bsk = new BST<>();
		for (Integer i : list)
		{
			bsk.add(i);
		}

	}

	@Test public void test01()
	{
		// 打印二叉搜索树
		BinaryTrees.print(bsk, BinaryTrees.PrintStyle.INORDER);
	}

	@Test public void test02()
	{
		/*bsk.inorder(new BinarySearchTree.Visitor<Integer>()
		{
			@Override public boolean visit(Integer element)
			{
				System.out.print(element);
				System.out.print(" ");

				return element == 23;
			}
		});*/

		//		System.out.println(bsk.toString());

		//		System.out.println(bsk.height());
		//		System.out.println(bsk.height2());
		//		System.out.println(bsk.isComplete());

		//		BinarySearchTree.Node<Integer> node = bsk.predecessor(bsk.node(16));
		//
		//		if (node != null)
		//			System.out.println(node.element);

		//		System.out.println(bsk.inorderTraversal());

		/*for (Integer i : list)
		{
			System.out.println("============删除_" + i);
			bsk.remove(i);
			BinaryTrees.println(bsk);

		}*/

		String str1 = null;
		String str2 = "123";
		System.out.println(str1 instanceof String);

	}

}
