import java.lang.reflect.Array;
import java.util.*;
//问题：
/**/
//题解：
//*************买卖股票问题
public class GreedyAlgorithm {
    public static void main(String[] args) {
        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm();
        //greedyAlgorithm.removeDuplicateLetters("bcabc");
        //greedyAlgorithm.wiggleMaxLength(new int[]{0,0});
        //greedyAlgorithm.removeKdigits("1432219", 3);
        //greedyAlgorithm.largestSumAfterKNegations(new int[] {-4,-3,-2}, 4);
        int[][] nums = new int[7][2];
        nums[0] = new int[]{9, 12};
        nums[1] = new int[]{1,10};
        nums[2] = new int[]{4,11};
        nums[3] = new int[]{8,12};
        nums[4] = new int[]{3,9};
        nums[5] = new int[]{6,9};
        nums[6] = new int[]{6,7};


        greedyAlgorithm.findMinArrowShots(nums);
    }
   /* 状态 a：root 必须放置摄像头的情况下，覆盖整棵树需要的摄像头数目。
    状态 b：覆盖整棵树需要的摄像头数目，无论root是否放置摄像头。
    状态 c：覆盖两棵子树需要的摄像头数目，无论节点root 本身是否被监控到。*/

    public int[] dfs(TreeNode root) {
        if (root == null) return new int[]{Integer.MAX_VALUE / 2, 0, 0};

        int[] left = dfs(root.left);
        int[] rigth = dfs(root.right);
        int[] res = new int[3];
        res[0] = left[2] + rigth[2] + 1;
        res[1] = Math.min(res[0], Math.min(left[0]+rigth[1], left[1]+rigth[0]));
        res[2] = Math.min(res[0], left[1] + rigth[1]);
        return res;
    }
    public int minCameraCover(TreeNode root) {
        return dfs(root)[1];
    }

    //找出第一个不是递增的字符，在从这个字符从后往前遍历字符ch，如果ch-1后仍然满足，则将ch-1，将后面剩余的字符都置为9
    public int monotoneIncreasingDigits(int n) {
        if (n <= 9) return n;
        char[] s = Integer.toString(n).toCharArray();
        for (int i=1; i<s.length; i++) {
            if (s[i] < s[i-1]) {
                int j = i - 1;
                while ( j >= 1){
                    if (s[j] - 1 >= s[j-1]) break;
                    j--;
                }
                s[j]--;
                while(++j < s.length) {
                    s[j] = '9';
                }
                return Integer.parseInt(String.valueOf(s));
            }
        }
        return n;
    }
    //问题：
    /*字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，
    同一字母最多出现在一个片段中。返回一个表示每个字符串片段的长度的列表。*/
    //题解：记录每个字母最后一次出现的下标，end记录当前遍历到字母最后一次下标的最大值，遍历到i==end结束
    //end - start + 1 即为长度
    public List<Integer> partitionLabels(String s) {
        List<Integer> res = new ArrayList<>();
        int[] last = new int[56];
        char[] charArray = s.toCharArray();
        for (int i=0; i<charArray.length; i++) {
                last[charArray[i] - 'a'] = i;
        }
        int start = 0, end = 0;
        for (int i=0; i<charArray.length; i++) {
            end = Math.max(end, last[charArray[i] - 'a']);
            if (i == end) {
                res.add(end - start + 1);
                start = end + 1;
            }
        }
//        int[] count = new int[56];
//        char[] charArray = s.toCharArray();
//        for (char ch: charArray) {
//            if (ch >= 'a') {
//                count[ch - 'a']++;
//            }else {
//                count[ch - 'A']++;
//            }
//        }
//        Set<Character> set = new HashSet<>();
//        int cur = 0;
//        int curZero = 0;
//        for (char ch: charArray) {
//            cur++;
//            set.add(ch);
//            int temp;
//            if (ch >= 'a') temp = ch - 'a';
//            else temp = ch - 'A';
//            if (--count[temp] == 0) {
//                curZero++;
//                if (curZero == set.size()) {
//                    res.add(cur);
//                    cur = 0;
//                    curZero = 0;
//                    set.clear();
//                }
//            }
//
//
//        }
        return res;

    }
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length <= 1) return 0;
        Arrays.sort(intervals, (a, b)->{
            if (a[1] == b[1]) return 0;
            else if (a[1] > b[1]) return 1;
            else return -1;
        });
        int res = 0;
        int pre = intervals[0][1];
        for (int i=1; i<intervals.length; i++) {
            if (intervals[i][0] < pre) {
                res++;
            }else {
                pre = intervals[i][1];
            }
        }
        return res;
    }
    public int findMinArrowShots(int[][] points) {
        if (points.length <= 1) return points.length;
        Arrays.sort(points, (a, b)->{
            if (a[0] != b[0]) {
                if (a[0] < b[0]) return -1;
                else return 1;
            }
            else return a[1] - b[1];
        });
        int res = 1;
        for (int i=1; i<points.length; i++) {
            if (points[i][0] <= points[i-1][1]) {
                if (points[i][1] > points[i-1][1]) {
                    points[i][1] = points[i-1][1];
                }
            } else {
                res++;
            }
        }
        return res;
    }
    public int longestPalindrome(String s) {
        char[] array = s.toCharArray();
        int[] count = new int[52];
        for (char ch: array) {
            if (ch >= 'a') count[ch-'a']++;
            else count[ch-'A'+26]++;
        }
        int res = 0;
        for (int i: count) {
            res += i / 2;
        }
        res *= 2;
        if (res == s.length()) return res;
        else return res+1;
    }
    public int largestSumAfterKNegations(int[] nums, int k) {
        Arrays.sort(nums);
        int i=0;
        while (i < nums.length && nums[i] < 0 && k > 0) {
            nums[i] = -nums[i];
            k--;
            i++;
        }
        if (k == 0 || (k & 1) == 0 ) return Arrays.stream(nums).sum();

        if (i!=nums.length && (i == 0 || nums[i-1] >= nums[i])) {
            nums[i] = -nums[i];
        } else {
            nums[i-1] = -nums[i-1];
        }
        return Arrays.stream(nums).sum();
    }
    //
    public int maxSubArray(int[] nums) {
        int cur = nums[0];
        int max = cur;
        for (int i=1; i<nums.length; i++) {
            if (cur < 0) cur = nums[i];
            else {
                cur += nums[i];
                if (cur > max) max = cur;
            }
        }
        return max;
    }
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int j = 0;
        int res = 0;
        for (int i=0; i<s.length && j<g.length; i++) {
            if (s[i] >= g[j]) {
                res++;
                j++;
            }
        }
        return res;
    }
    //从左到右 从右到左
    public int candy(int[] ratings) {
        if (ratings.length < 2) return ratings.length;
        int[] candyArray = new int[ratings.length];
        Arrays.fill(candyArray, 1);
        for (int i=1; i<candyArray.length; i++) {
            if (ratings[i] > ratings[i-1]) candyArray[i] = candyArray[i-1] +1;
        }
        for (int i=candyArray.length-2; i>=0; i--) {
            if (ratings[i] > ratings[i+1]) {
                candyArray[i] = Math.max(candyArray[i], candyArray[i+1]+1);
            }
        }
        int res = Arrays.stream(candyArray).sum();
        return res;
    }
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (a, b)->{
            if (a[0] != b[0]) return b[0] - a[0];
            else return a[1] - b[1];
        });
        List<int[]> res = new ArrayList<>();
        for (int i=0; i<people.length; i++) {
            res.add(people[i][1], people[i]);
        }
        return res.toArray(people);
    }
    public String removeKdigits(String num, int k) {
        if (k >= num.length()) return "0";
        StringBuffer res = new StringBuffer(num);
        int i = 0;
        while (k > 0) {
            while (i < res.length()-1 && res.charAt(i) <= res.charAt(i+1)) i++;
            if (i == res.length()-1) {
                res.delete(i-k+1, res.length());
                break;
            }
            res.deleteCharAt(i);
            if ( i != 0) i--;
            k--;
        }
        while (res.length() > 0 && res.charAt(0) == '0') res.deleteCharAt(0);
        if (res.length() == 0) return "0";
        return res.toString();
    }
    public int wiggleMaxLength(int[] nums) {
        if (nums.length <= 1) return nums.length;
        int[] dp = new int[nums.length];
        int index = 1;
        boolean flag = false;  //下一个要比前面大为false
        int i=1;
        while(i < nums.length && nums[i] == nums[0]) i++;
        if (i < nums.length ) {
            if (nums[i] > nums[0]) flag = true;
        }
        else return 1;
        dp[0] = nums[0]; dp[1] = nums[i++];
        for (; i<nums.length; i++) {
            if (flag) {
                if (nums[i] < dp[index]) {
                    ++index;
                    flag = false;
                }
                dp[index] = nums[i];
            } else {
                if (nums[i] > dp[index]) {
                    ++index;
                    flag = true;
                }
                dp[index] = nums[i];
            }
        }
        return index+1;
    }
    //或者dp方法寻找最长递增子序列
    public boolean increasingTriplet(int[] nums) {
        if (nums.length <= 2) return false;
        int one = Integer.MAX_VALUE, two = Integer.MAX_VALUE;
        for (int i=0; i<nums.length; i++) {
            if (nums[i] > two) return true;
            else if (nums[i] > one) {
                two = nums[i];
            } else {
                one = nums[i];
            }
        }
        return false;
    }
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int[] nums = new int[cost.length];
        for (int i=0; i<nums.length; i++) {
            nums[i] = gas[i] - cost[i];
        }
        int minGas = Integer.MAX_VALUE;
        int index = -1;
        int space = 0;
        for (int i=0; i<gas.length; i++) {
            space += nums[i];
            if (space < minGas) {
                minGas = space;
                index = i;
            }
        }
        return space < 0 ? -1 : (index + 1 ) % gas.length;
//        int i = 0;
//        while (i < gas.length) {
//            int cur = 0;
//            int cnt = 0;
//            while (cnt < gas.length) {
//                int j = (i+cnt) % gas.length;
//                cur += nums[j];
//                if (cur < 0) break;
//                cnt++;
//            }
//            if (cnt == gas.length) return i;
//            i += cnt + 1;
//        }
//        return -1;

    }
    public int maxArea(int[] height) {
        int head = 0, tail = height.length-1;
        int max = 0;
        while (head < tail) {
            max = Math.max(max, (tail - head) * Math.min(height[head], height[tail]));
            if (height[head] < height[tail]) {
                ++head;

            }else {
                --tail;
            }
        }
        return max;
    }
    public String removeDuplicateLetters(String s) {
        char[] counts = new char[26];
        boolean[] exist = new boolean[26];
        for (int i=0; i<s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
        }
        StringBuffer res = new StringBuffer();
        for (int i=0; i<s.length(); i++) {
            char ch = s.charAt(i);
            if (!exist[ch - 'a']) {
                exist[ch - 'a'] = true;
                while (!res.isEmpty()) {
                    char tail = res.charAt(res.length()-1);
                    if (tail > ch && counts[tail - 'a'] != 0) {
                        res.deleteCharAt(res.length()-1);
                        exist[tail - 'a'] = false;
                    }
                    else break;
                }
                res.append(ch);
                exist[ch - 'a'] = true;
            }
            counts[ch -'a']--;
        }
        return res.toString();
    }

}
