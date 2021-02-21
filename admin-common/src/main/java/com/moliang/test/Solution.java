package com.moliang.test;

import java.util.*;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/13 14:07
 * @Version 1.0
 */
public class Solution {

    public int longestSubarray(int[] nums, int limit) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        PriorityQueue<Integer> max = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int l = 0, r = 0, ans = 0;
        while(r < nums.length) {
            min.add(nums[r]);
            max.add(nums[r]);
            if(max.peek() - min.peek() <= limit) {
                ans = Math.max(ans, min.size());
                r++;
                continue;
            }
            while(max.peek() - min.peek() > limit) {
                min.remove(nums[l]);
                max.remove(nums[l]);
                l++;
            }
            r++;
        }
        return ans;
    }

    public int longestPalindrome(String word1, String word3) {
        StringBuilder b = new StringBuilder();
        for(int i = word3.length() - 1;i >= 0;i--) {
            b.append(word3.charAt(i));
        }
        String word2 = new String(b);
        int[][] tab = new int[word1.length()][word2.length()];
        for(int[] t : tab) Arrays.fill(t, -1);
        int ans = 0;

        for(int i = 0;i < word1.length();i++) {
            for(int j = 0;j < word2.length();j++) {
                if(word1.charAt(i) != word2.charAt(j)) {
                    tab[i][j] = -1;
                }
                else {
                    if(i - 1 >= 0 && j - 1 >= 0)
                    tab[i][j] = 1 + tab[i - 1][j - 1];
                    else tab[i][j] = 1;
                    ans = Math.max(ans, tab[i][j]);
                }
            }
        }
        if(ans == 0) return 0;
        if(ans < word1.length() || ans < word2.length()) return 2 * ans + 1;
        return 2 * ans;
    }

    Map<String, Integer> map = new HashMap<>();
    public int maximumScore(int[] nums, int[] multipliers) {
        int l = 0, r = nums.length - 1, index = 0, sum = 0;
        return maximumScore(nums, multipliers, l, r, index, sum);
    }

    public int maximumScore(int[] nums, int[] multipliers, int l, int r, int index, int sum) {
        if(index == multipliers.length) return sum;
        String s = l +"|" + r +"|"+ index;
        if(map.containsKey(s)) return map.get(s) + sum;
        int ans =  Math.max(maximumScore(nums, multipliers, l + 1, r, index + 1, multipliers[index] * nums[l]),
                maximumScore(nums, multipliers, l, r - 1, index + 1, multipliers[index] * nums[r]));
        map.put(s, ans);
        return ans + sum;
    }

    /**
    public int maximumScore(int[] nums, int[] multipliers) {
        int l = 0, r = nums.length - 1, index = 0, sum = 0;
        while(index < multipliers.length) {
            if(index < multipliers.length - 1 && multipliers[index] < multipliers[index + 1]) {
                if(nums[l] < nums[r]) {
                    sum += nums[l] * multipliers[index];
                    l++;
                } else {
                    sum += nums[r] * multipliers[index];
                    r--;
                }
            } else {
                if(nums[l] < nums[r]) {
                    sum += nums[r] * multipliers[index];
                    r--;
                } else {
                    sum += nums[l] * multipliers[index];
                    l++;
                }
            }
            index++;
        }
        return sum;
    }
     **/



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
