package com.tw.vrc.lists;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ArrayListUtilTest {
    @Test
    public void testSubList() {
        List list = new java.util.ArrayList();
        list.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        List A = list.subList(0,4);
        A.set(2,22);
        System.out.println(list);
        A.clear();
        A.add(88);
        System.out.println(list);
        System.out.println(list.size()+" "+A.size());
    }
}
