package com.zyr._17二叉堆;

/**
 * @author 张业荣
 * @Description 堆的接口
 * @Date 2020/2/15
 */
public interface Heap<E>
{
	int size();	// 元素的数量
	boolean isEmpty();	// 是否为空
	void clear();	// 清空
	void add(E element);	 // 添加元素
	E get();	// 获得堆顶元素
	E remove(); // 删除堆顶元素
	E replace(E element); // 删除堆顶元素的同时插入一个新元素


}
