package com.zyr._17二叉堆;

import com.zyr._08二叉搜索树.printer.BinaryTrees;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author 张业荣
 * @Description
 * @Date 2020/2/15
 */
public class Main
{
	static void test1()
	{
		BinaryHeap<Integer> heap = new BinaryHeap<>();
		heap.add(68);
		heap.add(72);
		heap.add(43);
		heap.add(50);
		heap.add(38);
		heap.add(10);
		heap.add(90);
		heap.add(65);
		BinaryTrees.println(heap);
		heap.remove();
		BinaryTrees.println(heap);

		System.out.println(heap.replace(70));
		BinaryTrees.println(heap);
	}

	static void test2()
	{
		Integer[] data = { 88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37 };
		BinaryHeap<Integer> heap = new BinaryHeap<>(data);
		BinaryTrees.println(heap);

		data[0] = 10;
		data[1] = 20;
		BinaryTrees.println(heap);
	}

	static void test3()
	{
		Integer[] data = { 88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37 };
		BinaryHeap<Integer> heap = new BinaryHeap<>(data, new Comparator<Integer>()
		{
			public int compare(Integer o1, Integer o2)
			{
				// 关键是这里 看返回的是什么 o1-o2默认是取大
				return o2 - o1;
			}
		});
		BinaryTrees.println(heap);
	}

	// 使用最小堆来求top3
	static void test4()
	{
		// 使用最小堆来求top3
		Integer[] arr = { 41, 75, 21, 81, 93, 84, 42, 88, 62, 89, 52, 78, 64, 83, 36, 98, 35, 55, 71, 30 };
		BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>()
		{
			@Override public int compare(Integer o1, Integer o2)
			{
				return o2 - o1;
			}
		});

		// 求topk
		int top = 3;
		for (int i = 0; i < arr.length; i++)
		{
			Integer value = arr[i];
			if (i < top)
			{
				heap.add(value);

			}
			else if (value > heap.get())
			{
				heap.replace(value);

			}
		}

		BinaryTrees.println(heap);
	}

	static void test5()
	{
		BinaryHeap<Integer> heap = new BinaryHeap<>();
		heap.add(68);
		heap.add(72);
		heap.add(43);
		heap.add(50);
		heap.add(38);
		heap.add(10);
		heap.add(90);
		heap.add(65);
		BinaryTrees.println(heap);
		//		Object[] arr = heap.elements;
		heap.sort();
		System.out.println(Arrays.toString(heap.elements));
		//		System.out.println(arr);

	}

	public static void main(String[] args)
	{
		test5();

	}

}
