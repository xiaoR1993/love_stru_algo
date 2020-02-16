package com.zyr._13红黑树;

import com.zyr._08二叉搜索树.BST;
import com.zyr._08二叉搜索树.printer.BinaryTrees;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author 张业荣
 * @Description
 * @Date 2020/2/8
 */
public class Main
{
	private static List<Integer> list;
	private static BST<Integer> bsk;

	static
	{
		list = Arrays.asList(90, 6, 18, 13, 31, 50, 66, 79, 20, 52);
		bsk = new RBTree<>();
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
