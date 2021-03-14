package org.wei.demo.monday1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EntryIterator {


    public static void main(String[] args) {
        testRemove();
    }

    private static void testRemove() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "11");
        map.put(2, "22");
        map.put(3, "33");


        // for (Map.Entry<Integer, String> entry : map.entrySet()) {
        //     if (entry.getKey() == 1) {
        //         map.remove(1);
        //     }
        // }

        final Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<Integer, String> next = iterator.next();
            if (next.getKey() == 1) {
                System.out.println("删除：value=" + next.getValue());
                iterator.remove();
            }
        }

        System.out.println("最终map:");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }
}
