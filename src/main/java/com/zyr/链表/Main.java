package com.zyr.链表;

/**
 * @author 张业荣
 * @Description 链表测试类
 * @Date 2020/1/5
 */
public class Main {

    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(1,30);
        list.remove(1);
        System.out.println(list.indexOf(10));
        System.out.println(list.get(1));
        System.out.println(list);



    }

}
