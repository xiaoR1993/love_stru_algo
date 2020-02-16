package com.zyr._11AVL树;

import com.zyr._08二叉搜索树.BST;
import com.zyr._08二叉搜索树.printer.BinaryTrees;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author 张业荣
 * @Description
 * @Date 2020/2/5
 */
public class Main
{

	private static List<Integer> list;
	private static BST<Integer> bsk;

	static
	{
		list = Arrays.asList(26, 89, 78, 33, 58, 71, 82, 23, 14, 55, 38);
		bsk = new AVLTree<>();
		for (Integer i : list)
		{
			bsk.add(i);
		}

	}

	@Test public void test01()
	{
		// 打印二叉搜索树
		BinaryTrees.print(bsk);
	}

	@Test public void test02()
	{
		BinaryTrees.print(bsk);
		System.out.println();
		for (Integer i : list)
		{
			System.out.println("将要删除" + i);
			bsk.remove(i);
			// 打印二叉搜索树
			BinaryTrees.print(bsk);
			System.out.println();
			System.out.println("=========================");

		}

	}

}
