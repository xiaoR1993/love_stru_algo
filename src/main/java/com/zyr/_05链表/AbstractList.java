package com.zyr._05链表;

/**
 * @author 张业荣
 * @Description 抽象类List
 * @Date 2020/1/5
 */
public abstract class AbstractList<E> implements List<E> {

    /**
     * 元素的数量
     */
    protected int size;

    //接口上的属性都是 public static final 因为他不可能有实例 在子类里面 可以直接访问 但是 对象不行
//    public static final Object obj = new Object();

    /**
     * 元素的数量
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否包含某个元素
     *
     * @param element
     * @return
     */
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 添加元素到尾部
     *
     * @param element
     */
    public void add(E element) {
        add(size, element);
    }

    /**
     * 新增检查范围
     *
     * @param index
     */
    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }

    /**
     * 抛出索引越界异常
     *
     * @param index
     */
    protected void outOfBounds(int index) {
        throw new IndexOutOfBoundsException(index + "索引越界了");
    }

    /**
     * 检查范围
     *
     * @param index
     */
    protected void rangeCheck(int index) {
        if (index < 0 || index > size - 1) {
            outOfBounds(index);
        }

    }

}
