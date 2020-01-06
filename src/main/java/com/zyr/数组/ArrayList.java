package com.zyr.数组;

import com.zyr.链表.AbstractList;

import java.util.Objects;

/**
 * @author 张业荣
 * @Title 数组
 * @Description 数组实现
 * @Date 2020/1/3
 */
public class ArrayList<E> extends AbstractList<E> {

    /**
     * 所有的元素
     */
    private E[] elements;

    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList(int capaticy) {
        //初始化size
        capaticy = (capaticy < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capaticy;
        //初始化数组
        elements = (E[]) new Object[capaticy];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 清除所有元素
     */
    public void clear() {
        //全部设置为null
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;

    }

    /**
     * 获取index位置的元素
     *
     * @param index
     * @return
     */
    public E get(int index) {
        rangeCheck(index);
        return elements[index];

    }

    /**
     * 设置index位置的元素
     *
     * @param index
     * @param element
     * @return 原来的元素ֵ
     */
    public E set(int index, E element) {
        rangeCheck(index);

        E old = get(index);
        elements[index] = element;

        return old;

    }

    /**
     * 在index位置插入一个元素
     *
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacity(size + 1);
        //从index到size-1的数据 都往后摞
        //后到前 防止覆盖
        for (int i = size - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = element;
        size++;
//        rangeCheckForAdd(index);
//
//        ensureCapacity(size + 1);
//
//        for (int i = size; i > index; i--) {
//            elements[i] = elements[i - 1];
//        }
//        elements[index] = element;
//        size++;

    }

    /**
     * 删除index位置的元素
     *
     * @param index
     * @return
     */
    public E remove(int index) {
        rangeCheck(index);
        E old = get(index);
        //数据从后往前摞
        for (int i = index + 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }
        elements[size - 1] = null;
        size--;
        return old;
    }

    /**
     * 查看元素的索引
     *
     * @param element
     * @return
     */
    public int indexOf(E element) {
        //对象须要1注意空指针
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        }
        //element不为null
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) return i;
        }

        return ELEMENT_NOT_FOUND;
    }

    /**
     * 保证要有capacity的容量
     *
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        //扩容的条件判断
        if (elements.length <= capacity) {
            //容量
            int newCpacity = capacity + (capacity >> 1);
            //新数组
            E[] newElements = (E[]) new Objects[newCpacity];
            //替换旧数组数据到新数据
            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }


    @Override
    public String toString() {
        // size=3, [99, 88, 77]
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }

            string.append(elements[i]);

//			if (i != size - 1) {
//				string.append(", ");
//			}
        }
        string.append("]");
        return string.toString();
    }

}
