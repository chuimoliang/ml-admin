package com.moliang.test;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/13 14:07
 * @Version 1.0
 */
public class Solution {

    class MedianFinder {

        PriorityQueue<Integer> max;
        PriorityQueue<Integer> min;

        public MedianFinder() {
            max = new PriorityQueue<>((o1, o2)-> o2 - o1);
            min = new PriorityQueue<>();
        }

        public void addNum(int num) {
            if (max.isEmpty() || max.peek() > num) {
                max.add(num);
            } else {
                min.add(num);
            }
            if (max.size() > min.size() + 1) {
                min.add(max.poll());
            }
            if (min.size() > max.size() + 1) {
                max.add(min.poll());
            }
        }

        public double findMedian() {
            if (max.size() == min.size()) {
                return (max.peek() + min.peek()) / 2.0;
            }
            return max.size() > min.size() ? max.peek() : min.peek();
        }
    }


    public static void main(String[] args) throws ClassNotFoundException {
        Class t = Class.forName(String.valueOf("com.moliang.test.Solution"));
        for (Method m : t.getMethods()) {
            System.out.println(m);
        }
        Class p = Class.forName(String.valueOf("com.moliang.test.Solution"));
        System.out.println(t == p);
    }
}
