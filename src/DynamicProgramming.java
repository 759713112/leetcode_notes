import com.sun.source.tree.Tree;

import java.net.Inet4Address;
import java.util.*;
public class DynamicProgramming {
    public static void main(String[] args) {
        //System.out.println(longestPalindrome("aabaa"));
        //System.out.println(Arrays.deepToString(isPalindrome("aabaa")));
        getMoneyAmount(19);
        //maxProfit(new int[]{1,2,3,0,2});
        //maxProfit(4, new int[]{1,2,4,2,5,7,2,4,9,0} );
        //rob(new int[]{2,1,1,2});
    }
    public static int rob(int[] nums) {
        if (nums.length ==  1) return nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[1], nums[0]);
        for (int i=2; i<nums.length; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2]+nums[i]);
        }
        System.out.println(Arrays.toString(dp));
        return Math.max(dp[nums.length-1], dp[nums.length-2]);

    }

    public static int maxProfit(int k, int[] prices) {
        if (prices.length <=1 || k==0)return 0;
        int[] buy = new int[k];
        int[] sell = new int[k];
        for (int i=0; i<k; i++) {
            buy[i] = -prices[0];
            sell[0] = 0;
        }
        for (int i=1; i<prices.length; i++) {
            buy[0] = Math.max(-prices[i], buy[0]);
            sell[0] = Math.max(sell[0], buy[0]+prices[i]);
            for (int j=1; j<k; j++) {
                buy[j] = Math.max(sell[j-1]-prices[i], buy[j]);
                sell[j] = Math.max(buy[j]+prices[i], sell[j]);
            }
//            System.out.println(prices[i]);
//            System.out.println(Arrays.toString(buy));
//            System.out.println(Arrays.toString(sell));
//            System.out.println();
        }
        return sell[k-1];
    }
    public static int maxProfit(int[] prices) {
        if (prices.length <= 1) return 0;
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[1][0] = Math.max(0, dp[0][1]+prices[1]);
        dp[1][1] = Math.max(dp[0][1], -prices[1]);
        for (int i=2; i<prices.length; i++){
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1]+prices[i]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-2][0]-prices[i]);
        }
        for (int i=0; i<prices.length; i++)
        System.out.println(Arrays.toString(dp[i]));
        return dp[prices.length-1][0];
    }
    public int maxProfit(int[] prices, int fee) {
        if (prices.length <= 1) return 0;
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i=1; i<prices.length; i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1]+prices[i]-fee);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0]-prices[i]);
        }
        return dp[prices.length-1][0];
    }
    public static int getMoneyAmount(int n) {
        int[][] dp = new int[n+1][n+1];
        for (int i=1; i<n-1; i++) {
            dp[i][i+1] = i;
            dp[i][i+2] = i+1;
        }
        dp[n-1][n] = n-1;

        for (int i=n-3; i>=0; i--) {
            for (int j=i+1; j<=n; j++){
                for (int k=i; k<=j-1; k++) {
                    int cur;
                    if (k==i) {
                        cur = k + dp[k+1][j];
                    } else cur = k + Math.max(dp[i][k-1], dp[k+1][j]);
                    if (dp[i][j] != 0 ) {
                        if (dp[i][j] >= cur) {
                            dp[i][j] = cur;
                            if ( i == 1 && j ==n) System.out.println(k);
                        }

                    }
                    else dp[i][j] = cur;
                }

            }
        }
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=n; j++)
            {
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println();
        }
        return dp[1][n];
    }
//////*************************************
    public boolean PredictTheWinner(int[] nums) {
        int[][] arr = new int[nums.length][nums.length];
        for (int i=0; i<nums.length; i++) arr[i][i] = nums[i];
        for (int i=nums.length-2; i>=0; i--) {
            for (int j=i+1; j<nums.length; j++) {
                arr[i][j] = Math.max(nums[i] - arr[i + 1][j], nums[j] - arr[i][j - 1]);
            }
        }
        return arr[0][nums.length - 1] >= 0;

    }
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet(wordDict);
        boolean[] array = new boolean[s.length()];
        if (wordDictSet.contains(s.substring(0,1))) array[0] = true;
        for (int i=1; i<s.length(); i++) {
            for (int j=i; j>0; j--){
                if (array[j-1] && wordDictSet.contains(s.substring(j,i+1))){
                    array[i] = true;
                }
            }
            if (wordDictSet.contains(s.substring(0,i+1)))array[i] = true;
        }
        return array[s.length()-1];
    }

    public static boolean[][] isPalindrome(String s) {
        boolean[][] res = new boolean[s.length()][s.length()];
        for (int j = 0; j < s.length(); j++) {
            for (int i = 0; i < s.length(); i++) {
                if (i >= j) res[i][j] = true;
                else if (s.charAt(i) == s.charAt(j) && res[i + 1][j - 1]) {
                    res[i][j] = true;
                }
            }
        }
        return res;
    }
    public static void dfsPalindrome(String s, List<List<String>> res, List<String> cur,
       int start, int end, boolean[][] isPalindrome) {
        if (start == end) {
            res.add(cur);
            return;
        }
        for (int i=start; i<end; i++) {
            if (isPalindrome[start][i]) {
                List<String> new_cur = new ArrayList<>(cur);
                new_cur.add(s.substring(start, i+1));
                dfsPalindrome(s, res, new_cur, i+1, end, isPalindrome);
            }
        }

    }
    public List<List<String>> partition(String s) {
        boolean[][] isPalindrome = isPalindrome(s);
        List<List<String>> res = new ArrayList<>();
        List<String> cur = new ArrayList<>();
        dfsPalindrome(s, res, cur,0, s.length(), isPalindrome);
        return res;
    }

    public boolean isSubsequence(String s, String t) {
        char[] tArray = t.toCharArray();
        char[] sArray = s.toCharArray();
        int i = 0, j = 0;
        for (; i < tArray.length && j < sArray.length; i++) {
            if (tArray[i] == sArray[j]) {
                j++;
            }
        }
        if (j == sArray.length) return true;
        else return false;
    }

    public int[] countBits(int n) {
        int[] res = new int[n + 1];
        res[0] = 0;
        if (n == 0) return res;
        res[1] = 1;
        if (n == 1) return res;
        res[2] = 1;
        if (n == 2) return res;
        int base = 2;
        for (int i = 3; i < n + 1; i++) {
            if (i < base * 2) {
                res[i] = 1 + res[i - base];
            } else {
                res[i] = 1;
                base *= 2;
            }
        }
        return res;
    }

    public int minCostClimbingStairs(int[] cost) {
        int[] curMiniCost = new int[cost.length];
        curMiniCost[0] = cost[0];
        curMiniCost[1] = cost[1];
        for (int i = 2; i < cost.length; i++) {
            curMiniCost[i] = (curMiniCost[i - 2] < curMiniCost[i - 1] ? curMiniCost[i - 2] : curMiniCost[i - 1]) + cost[i];
        }
        return curMiniCost[cost.length - 2] < curMiniCost[cost.length - 1] ? curMiniCost[cost.length - 2] : curMiniCost[cost.length - 1];
    }

    private static long mod = 1000000007;

    public int waysToStep(int n) {
        long[] res = new long[n + 1];
        res[1] = 1;
        if (n == 1) return (int) res[1];
        res[2] = 2;
        if (n == 2) return (int) res[2];
        res[3] = 4;
        if (n == 3) return (int) res[3];

        for (int i = 4; i <= n; i++) {
            res[i] = (res[i - 1] + res[i - 2] + res[i - 3]) % mod;
        }
        return (int) res[n];
    }

    public static int reverseBits(int num) {
        int cur = 0;
        int pre = 0;
        int max = 0;
        boolean flag = false;
        for (int i = 0; i < 32; i++) {
            if ((num & 1) == 1) {
                cur++;
                flag = false;
            } else {
                if ((cur + pre) > max) max = cur + pre;
                if (flag) pre = 0;
                else {
                    flag = true;
                    pre = cur;
                    cur = 0;
                }
            }
            num >>= 1;
        }
        if ((cur + pre) > max) max = cur + pre;
        return max == 32 ? 32 : max + 1;
    }

    public static int fib(int n) {
        int mod = 1000000007;
        if (n == 0) return 0;
        if (n == 1) return 1;
        int f1 = 0;
        int f2 = 1;
        boolean flag = false;
        for (int i = 2; i <= n; i++) {
            if (!flag) {
                f1 = (f1 + f2) % mod;
                flag = true;
            } else {
                f2 = (f1 + f2) % mod;
                flag = false;
            }
        }
        if (flag) return f1;
        else return f2;

    }

    public static String longestPalindrome(String s) {
        String reverse = new StringBuffer(s).reverse().toString();
        int[][] arr = new int[s.length()][s.length()];
        int maxlen = 0;
        int endIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(i) == reverse.charAt(j)) {
                    if (i == 0 || j == 0) {
                        arr[i][j] = 1;
                    } else {
                        arr[i][j] = arr[i - 1][j - 1] + 1;
                    }
                }
                if (maxlen < arr[i][j]) {
                    if (i == s.length() - j - 1 + arr[i][j] - 1) {
                        maxlen = arr[i][j];
                        endIndex = i;
                    }

                }

            }
        }
        return s.substring(endIndex - maxlen + 1, endIndex + 1);
    }

    public static int jump(int[] nums) {
        if (nums.length == 1) return 0;
        int[] dynamicJump = new int[nums.length];
        dynamicJump[nums.length - 1] = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j <= nums[i] && i + j <= nums.length - 1; j++) {
                if (dynamicJump[i + j] == 0 || dynamicJump[i + j] > dynamicJump[i] + 1) {
                    dynamicJump[i + j] = dynamicJump[i] + 1;
                }
            }
        }
        return dynamicJump[nums.length - 1];
    }

    public static int jump2(int[] nums) {
        int end = 0;
        int steps = 0;
        int maxPosition = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            maxPosition = Math.max(i + nums[i], maxPosition);
            if (i == end) {
                steps++;
                end = maxPosition;
            }
        }
        return steps;
    }

    public static boolean canJump(int[] nums) {
        if (nums.length == 1) return true;
        int end = 0;
        int steps = 0;
        int maxPosition = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            maxPosition = Math.max(i + nums[i], maxPosition);
            if (i == end) {
                if (maxPosition <= end) return false;
                steps++;
                end = maxPosition;
            }
        }
        return true;
    }

    public List<String> generateParenthesis(int n) {
        //初始化list数组模型
        LinkedList<String> data[] = new LinkedList[n];
        LinkedList<String> oi = new LinkedList<String>();
        oi.add("()");
        data[0] = oi;
        //计算模型【n】坐标处有效组合
        for (int m = 1; m < n; m++) {
            LinkedList<String> indata = new LinkedList<String>();
            for (int j = 0; j < data[m - 1].size(); j++) {
                String stringData = data[m - 1].get(j);
                for (int h = 0; h < stringData.length(); h++) {
                    String stringData2 = data[m - 1].get(j);
                    if (!indata.contains((new StringBuilder(stringData2)).insert(h, "()").toString())) {
                        indata.add((new StringBuilder(stringData2)).insert(h, "()").toString());
                    }
                }
            }
            data[m] = indata;
        }
        return data[n - 1];
    }

    public List<String> generateParenthesis2(int n) {
        List<String> res = new LinkedList<>();
        if (n == 0) return res;
        dfs_generateParenthesis("", n, n, res);
        return res;
    }

    public static void dfs_generateParenthesis(String cur, int left, int right, List<String> res) {
        if (left == 0 && right == 0) {
            res.add(cur);
            return;
        }
        if (left > right) {
            return;
        }
        if (left > 0) {
            dfs_generateParenthesis(cur + "(", left - 1, right, res);
        }
        if (right > 0) {
            dfs_generateParenthesis(cur + ")", left, right - 1, res);
        }
    }

    public int uniquePaths(int m, int n) {
        int[][] dynamicArray = new int[m][n];
        for (int i = 0; i < n; i++) dynamicArray[0][i] = 1;
        for (int i = 0; i < m; i++) dynamicArray[i][0] = 1;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dynamicArray[i][j] = dynamicArray[i - 1][j] + dynamicArray[i][j - 1];
            }
        }
        return dynamicArray[m - 1][n - 1];

    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dynamicArray = new int[m][n];
        for (int i = 0; i < n; i++) {
            if (obstacleGrid[0][i] == 1) {
                break;
            }
            dynamicArray[0][i] = 1;
        }
        for (int i = 0; i < m; i++) {
            if (obstacleGrid[i][0] == 1) break;
            dynamicArray[i][0] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) continue;
                dynamicArray[i][j] = dynamicArray[i - 1][j] + dynamicArray[i][j - 1];
            }
        }
        return dynamicArray[m - 1][n - 1];
    }

    public int numDecodings(String s) {
        if (s.charAt(0) == '0') return 0;
        if (s.length() == 1) return 1;
        int[] nums = new int[s.length()];
        nums[0] = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != '0') nums[i] += nums[i - 1];
            if (s.charAt(i - 1) == '1' || s.charAt(i - 1) == '2' &&
                    s.charAt(i) >= '0' && s.charAt(i) <= '6') {
                if (i == 1) {
                    nums[i]++;
                    continue;
                }
                nums[i] += nums[i - 2];
            }
        }
        return nums[s.length() - 1];
    }

    public static TreeNode treeCopy(TreeNode root) {
        TreeNode res = new TreeNode(root.val);
        if (root.left != null) {
            res.left = treeCopy(root.left);
        }
        if (root.right != null) {
            res.right = treeCopy(root.right);
        }
        return res;
    }

    public static List<TreeNode> generateTrees(int n) {
        List<TreeNode> pre = new ArrayList<TreeNode>();

        pre.add(new TreeNode(1));

        for (int i = 2; i <= n; i++) {
            List<TreeNode> cur = new ArrayList<TreeNode>();
            for (int m = 0; m < pre.size(); m++) {
                TreeNode node = pre.get(m);
                //加到头
                TreeNode root = new TreeNode(i);
                root.left = node;
                cur.add(root);

                //加到右自树
                for (int j = 0; j < n; j++) {

                    TreeNode copy = treeCopy(node);
                    TreeNode right = copy;
                    for (int k = 0; k < j; k++) {
                        if (right == null) break;
                        right = right.right;
                    }
                    if (right == null) break;
                    TreeNode insert = new TreeNode(i);
                    insert.left = right.right;
                    right.right = insert;
                    cur.add(copy);
                }
            }
            pre = cur;
        }
        return pre;

    }

    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() == 0 && s2.length() == 0 && s3.length() == 0) return true;
        if (s1.length() + s2.length() != s3.length()) return false;
        boolean flag1 = false, flag2 = false;
        if (s1.length() > 0 && s1.charAt(0) == s3.charAt(0)) {
            flag1 = isInterleave(s1.substring(1), s2, s3.substring(1));
        }
        if (s2.length() > 0 && s2.charAt(0) == s3.charAt(0)) {
            flag2 = isInterleave(s1, s2.substring(1), s3.substring(1));
        }
        return flag1 || flag2;
    }

    public boolean isInterleave2(String s1, String s2, String s3) {
        if (s1.length() == 0 && s2.length() == 0 && s3.length() == 0) return true;
        if (s1.length() + s2.length() != s3.length()) return false;
        boolean[] dynamic = new boolean[s2.length() + 1];
        dynamic[0] = true;
        for (int i = 1; i < s1.length() + 1; i++) {
            if (s2.charAt(i - 1) != s3.charAt(i - 1)) break;
            dynamic[i] = true;
        }

        for (int i = 1; i < s1.length() + 1; i++) {
            for (int j = 0; j < s2.length() + 1; j++) {
                if (!(dynamic[j] && s1.charAt(i - 1) == s3.charAt(i + j))) {
                    dynamic[j] = false;
                } else continue;
                if (j != 0) {
                    if (dynamic[j - 1] && s2.charAt(j - 1) == s3.charAt(i + j)) {
                        dynamic[j] = true;
                    }
                }

            }
        }
        return dynamic[s2.length()];
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle.size() == 1) return triangle.get(0).get(0);
        int[] pre = new int[1];

        for (int i = 0; i < triangle.size() - 1; i++) {
            int[] cur = new int[i + 2];
            int j = 0;
            cur[j] = pre[j++] + triangle.get(i).get(0);
            for (; j < i + 1; j++) {
                cur[j] = Math.min(pre[j] + triangle.get(i).get(j), pre[j - 1] + triangle.get(i).get(j - 1));
            }
            cur[j] = pre[j - 1] + triangle.get(i).get(j - 1);
            pre = cur;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < triangle.size(); i++) {
            int curCost = pre[i] + triangle.get(triangle.size() - 1).get(i);
            if (curCost < min) min = curCost;
        }
        return min;
    }
}

