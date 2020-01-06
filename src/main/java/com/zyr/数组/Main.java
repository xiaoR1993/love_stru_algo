package com.zyr.数组;

import javax.sound.midi.Soundbank;

/**
 * @author 张业荣
 * @Title 测试类
 * @Description 测试类
 * @Date 2020/1/3
 */
public class Main {

    public static void main(String[] args) {
        //测试Integer
        ArrayList<Integer> list = new ArrayList<>();
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
