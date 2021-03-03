package com.moliang.test;

import com.alibaba.druid.support.ibatis.SqlMapClientImplWrapper;

import java.util.*;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/13 14:07
 * @Version 1.0
 */
public class Solution {


    int[][] nums;
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, ((o1, o2) -> o1[0] - o2[0]));
        this.nums = envelopes;
        int[] tab = new int[envelopes.length];
        Arrays.fill(tab, -1);
        int ans = 0;
        for(int i = 0;i < envelopes.length;i++) {
            ans = Math.max(ans, find(i, tab));
        }
        return ans;
    }

    private int find(int index, int[] tab) {
        if(tab[index] != -1) return tab[index];
        int l = index + 1;
        while(l < nums.length && nums[l][0] == nums[index][0]) l++;
        int ans = 1;
        for(;l < nums.length;l++) {
            if(nums[l][1] > nums[index][1]) {
                ans = Math.max(ans, 1 + find(l, tab));
            }
        }
        tab[index] = ans;
        return ans;
    }

}
