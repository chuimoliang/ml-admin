package com.moliang.test;

import cn.hutool.extra.template.engine.freemarker.FreemarkerTemplate;
import com.alibaba.druid.support.ibatis.SqlMapClientImplWrapper;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/13 14:07
 * @Version 1.0
 */
public class Solution {

    public int[] countPairs(int n, int[][] edges, int[] queries) {
        int[] eCnt = new int[n+1];
        HashMap<Integer,Integer> hm = new HashMap<>();
        int m = edges.length;
        for(int i=0;i<m;i++) {
            eCnt[edges[i][0]] ++;
            eCnt[edges[i][1]] ++;
            int code;
            if(edges[i][0]<edges[i][1]) {
                code = edges[i][0]*(n+1)+edges[i][1];
            } else {
                code = edges[i][1]*(n+1)+edges[i][0];
            }
            hm.put(code,hm.getOrDefault(code,0)+1);
        }
        HashMap<Integer,Integer> hm1 = new HashMap<>();
        for(int i=1;i<=n;i++) {
            hm1.put(eCnt[i],hm1.getOrDefault(eCnt[i],0)+1);
        }
        int[] cnt = new int[2*m+1];
        // System.out.println(Arrays.toString(eCnt));
        // System.out.println(hm.toString());
        ArrayList<Integer> ar = new ArrayList<>(hm1.keySet());
        for(int i=0;i<ar.size();i++) {
            int key =ar.get(i);
            for(int j=i;j<ar.size();j++) {
                if (i==j) {
                    cnt[2*key] += hm1.get(key)*(hm1.get(key)-1)/2;
                } else {
                    int key2 = ar.get(j);
                    cnt[key+key2] += hm1.get(key)*hm1.get(key2);
                }
            }
        }
        for(Integer code:hm.keySet()) {
            int x = code / (n+1);
            int y = code % (n+1);
            int sum = eCnt[x]+eCnt[y];
            cnt[sum] --;
            cnt[sum-hm.get(code)]++;
        }
        // System.out.println(Arrays.toString(cnt));
        int[] res = new int[queries.length];
        for(int i=0;i<queries.length;i++) {
            int sum=0;
            for(int j=queries[i]+1;j<=m;j++) {
                sum += cnt[j];
            }
            res[i] = sum;
        }
        return res;
    }
    public static void main(String[] args) {
        int[][] t = new int[][]{{1,2}, {2,4}, {1,3}, {2,3}, {2,1}, {1,4}};
        int[] q = new int[]{2, 3};
        Solution s = new Solution();
        s.countPairs(4, t, q);
    }

    /**
    static int i = 0;

    public static synchronized void add() {
        i++;
    }
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Semaphore semaphore = new Semaphore(2);
        int index = 0, count = 0;
        Solution s1 = new Solution();
        Solution s2 = new Solution();
        Solution s3 = new Solution();
        while(index < 100) {
            i = 0;
            Thread t1 = new Thread(() -> {
                for (int t = 0; t < 1000; t++) s1.add();
            });
            Thread t2 = new Thread(() -> {
                for (int t = 0; t < 1000; t++) s2.add();
            });
            Thread t3 = new Thread(() -> {
                for (int t = 0; t < 1000; t++) s3.add();
            });
            Thread t4 = new Thread(() -> {
                for (int t = 0; t < 1000; t++) s1.add();
            });
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            while (t1.isAlive() || t2.isAlive() || t3.isAlive() || t4.isAlive()) {
            }
            if(i < 4000) count++;
            index++;
        }
        System.out.println(count);
    }
     */
}
