package org.wei.demo.monday1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TableSize {

    final static int MAXIMUM_CAPACITY = 1 << 30;


    public static void main(String[] args) {
        System.out.println(tableSizeFor(7));
    }

    static final int tableSizeFor(int cap) { // cap = 7
        int n = cap - 1; // n = 7-1 = 6 = 0 0000000 00000000 00000000 00000110
        n |= n >>> 1;    //     n >>> 1 = 0 0000000 00000000 00000000 00000011
                         //           n = 0 0000000 00000000 00000000 00000111
        n |= n >>> 2;    //     n >>> 2 = 0 0000000 00000000 00000000 00000001
                         //           n = 0 0000000 00000000 00000000 00000111
        n |= n >>> 4;    //     n >>> 4 = 0 0000000 00000000 00000000 00000000
                         //           n = 0 0000000 00000000 00000000 00000111
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

}
