package com.moliang.test;

import java.util.*;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/13 14:07
 * @Version 1.0
 */
public class Solution {

    public int[] getCoprimes(int[] nums, int[][] edges) {
        int[] tab = new int[nums.length];
        tab[0] = 0;
        int num = nums.length;
        Set<Integer> set = new HashSet<>();
        set.add(0);
        ArrayList[] lists = new ArrayList[num];
        for(int i = 0;i < lists.length;i++) {
            lists[i] = new ArrayList();
        }
        for(int[] t : edges) {
            lists[t[0]].add(t[1]);
            lists[t[1]].add(t[0]);
        }
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        while(!q.isEmpty() && set.size() < nums.length) {
            int t = q.poll();
            List<Integer> l = lists[t];
            for(int e : l) {
                if(!set.contains(e)) {
                    set.add(e);
                    q.add(e);
                    tab[e] = t;
                }
            }
        }
        int[] ans = new int[nums.length];
        ans[0] = -1;
        for(int i = 1;i < nums.length;i++) {
            int index = i;
            boolean flag = false;
            while(index != 0) {
                int parent = tab[index];
                if(get(nums[i], nums[parent]) == 1) {
                    ans[i] = parent;
                    flag = true;
                    break;
                }
                index = parent;
            }
            if(!flag) {
                ans[i] = -1;
            }
        }
        return ans;
    }

    private int get(int x, int y) {
        if(x < y) {
            return getCommonDivisor(y, x);
        }
        return getCommonDivisor(x, y);
    }

    private int getCommonDivisor(int x, int y) {
        int remain = x % y;
        if(remain == 0) return y;
        return getCommonDivisor(y, remain);
    }

    public List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for(String st : words) {
            map.put(st, map.getOrDefault(st, 0) + 1);
        }
        int len = words[0].length();
        List<Integer> ans = new ArrayList<>();
        for(int i = 0;i < len;i++) {
            Map<String, Integer> temp = new HashMap<>();
            int l = i, r = i + len, lc = i;
            for(int c = 0;c < words.length && r <= s.length();c++) {
                String t = s.substring(l, r);
                temp.put(t, temp.getOrDefault(t, 0) + 1);
                l = r; r += len;
            }
            int offset = i;
            while(true) {
                boolean flag = true;
                for(String t : map.keySet()) {
                    if(!temp.getOrDefault(t, 0).equals(map.getOrDefault(t, 0))) {
                        flag = false;
                        break;
                    }
                }
                if(flag) ans.add(offset);
                if(r > s.length()) break;
                String left = s.substring(lc, lc + len);
                temp.put(left, temp.getOrDefault(left, 0) - 1);
                String right = s.substring(l, r);
                temp.put(right, temp.getOrDefault(right, 0) + 1);
                l += len; r += len; lc += len; offset += len;
            }
        }
        return ans;
    }

    public int minimumSize(int[] nums, int maxOperations) {
        PriorityQueue<Integer> q = new PriorityQueue<>(((o1, o2) -> o2 - o1));
        for(int e : nums) {
            q.add(e);
        }
        while(!q.isEmpty()) {
            List<Integer> t = new ArrayList<>();
            int s = q.poll();
            t.add(s);
            while(!q.isEmpty() && q.peek() == s) t.add(q.poll());
            int num = (maxOperations / t.size()) + 1;
            Integer next = q.isEmpty() ? null : q.poll();
            if(next == null) {
                int ans = t.get(0) / num;
                if(t.get(0) % num != 0) return ans + 1;
                return ans;
            }
            if(num == 1) return s;
            int temp = s - next;
            maxOperations -= t.size();
            for(int i = 0;i < t.size();i++) {
                q.add(temp);
                q.add(next);
            }
        }
        return 0;
    }
}
