package com.zyr._05链表;

import com.zyr._05链表.circle.CircleLinkedListDemo;

/**
 * @author 张业荣
 * @Description 链表测试类
 * @Date 2020/1/5
 */
public class Main
{

	static void testList(List<Integer> list)
	{
		list.add(11);
		list.add(22);
		list.add(33);
		list.add(44);

		list.add(0, 55); // [55, 11, 22, 33, 44]
		list.add(2, 66); // [55, 11, 66, 22, 33, 44]
		list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]
		////
		list.remove(0); // [11, 66, 22, 33, 44, 77]
		list.remove(2); // [11, 66, 33, 44, 77]
		list.remove(list.size() - 1); // [11, 66, 33, 44]
		////
		Asserts.test(list.indexOf(44) == 3);
		Asserts.test(list.indexOf(22) == List.ELEMENT_NOT_FOUND);
		Asserts.test(list.contains(33));
		Asserts.test(list.get(0) == 11);
		Asserts.test(list.get(1) == 66);
		Asserts.test(list.get(list.size() - 1) == 44);

		System.out.println(list);
	}

	public static void main(String[] args)
	{
		//        testList(new CircleLinkedList<>());
		//测试约瑟夫问题
		CircleLinkedListDemo<Object> list = new CircleLinkedListDemo<>();
		for (int i = 1; i < 9; i++)
		{
			list.add(i);
		}

		//current指针到first
		list.reset();

		while (!list.isEmpty())
		{
			//这里为规定步数-1
			list.next();
			list.next();
			System.out.println(list.remove());
		}

	}

}
