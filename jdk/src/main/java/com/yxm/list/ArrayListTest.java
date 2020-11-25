package com.yxm.list;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        //1.添加元素
        list.add("张三");
        list.add("李四");
        list.add("王五");

        //2.替换元素
        list.set(1,"赵六");

        //3、指定位置添加元素
        list.add(1,"麻子");

        System.out.println(list.get(1));
        list.remove(1);
    }
}
