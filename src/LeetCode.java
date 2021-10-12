import com.sun.source.tree.Tree;

import java.net.Inet4Address;
import java.util.*;

public class LeetCode {
    public static void main(String[] args) {

//        int[] a = {5,5,5,5};
//
//        String s = "issip";
//        System.out.println(Arrays.toString(getKMPnext(s)));
//        System.out.println(strStr("mississippi", "issip"));
        //System.out.println(divide(-2147483647, 1));
        //reverseBits(-1);
        System.out.println(Integer.MAX_VALUE);
        //List<List<Integer>> res = generate(5);
    }

    public static int[] towSum(int[] nums, int target) {
        int[] res = new int[2];

        for (int i=0; i<nums.length; i++) {
            for (int j=i+1; j<nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    res[0] = i;
                    res[1] = j;
                }
            }
        }
        return res;

    }

    public static int[] towSum2(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int i = 1;
        map.put(nums[0], 0);
        do {
            if (map.containsKey(target - nums[i])) {
                return new int[] {map.get(target-nums[i]), i};
            }
            map.put(nums[i], i);
            i++;
        } while(i<nums.length);

        throw new IllegalArgumentException("No two sum solution");
    }

    public static int reverseInteger(int x) {
        String xString = Integer.toString(x);
        String reverse = "";

        for (int i=1; i<xString.length(); i++) {
            reverse = xString.charAt(i) + reverse;

        }
        if (xString.charAt(0) == '-') {
            reverse = xString.charAt(0) + reverse;
        } else {
            reverse = reverse + xString.charAt(0);
        }
        int res = 0;
        try {
            res = Integer.parseInt(reverse);
        }
        catch (NumberFormatException ex) {

        }
        return res;
    }

    public static int reverseInteger2(int x) {
        int pop = 0;
        int res = 0;
        while (x != 0) {
            pop = x % 10;
            if (res > Integer.MAX_VALUE/10 ||
                    (res == Integer.MAX_VALUE/10 && pop>7) ) {
                return 0;
            }
            if (res < Integer.MIN_VALUE/10 || (
                    res == Integer.MIN_VALUE/10 && pop<-8)){
                return 0;
            }
            res = res * 10 + pop;
            x /= 10;
        }
        return res;
    }

    public static ListNode reverseLinkedList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    public static ListNode reverseLinkedList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode p = reverseLinkedList2(head.next);
        head.next.next = head;
        head.next = null;
        return p;

    }

    private static final Map<Character, Character> map = new HashMap<Character, Character>(){{
        put('{','}'); put('[',']'); put('(', ')');
    }};
    public static boolean validParentheses(String s) {

        LinkedList<Character> stack = new LinkedList<Character>(){{add('?');}};
        for (Character c: s.toCharArray()) {
            if (map.containsKey(c)) {
                stack.addLast(c);
            }else {
                if (map.get(stack.removeLast()) != c) return false;
            }
        }
        return stack.size() == 1;

    }

    public static boolean PalindromeNumber(int x) {
        String s = Integer.toString(x);
        String reverse_s = new StringBuilder(s).reverse().toString();
        return s.equals(reverse_s);
    }

    public static int removeDuplicatesFromSortedArray(int[] nums) {
        if (nums.length == 0) return 0;
        int j = 0;
        for (int i=1; i<nums.length; i++) {
            if (nums[i] != nums[j]) {
                nums[++j] = nums[i];
            }
        }
        return j+1;
    }

    public static int removeElement(int[] nums, int val) {
        int j = 0;
        for (int i=0; i<nums.length; i++) {
            if (nums[i] != val ) {
                nums[j++] = nums[i];
            }
        }
        return j;
    }

    public static final Map<Character, Integer> romanBase = new HashMap<Character, Integer>(){{
            put('I', 1);    put('V', 5);
            put('X', 10);   put('L', 50);
            put('C', 100);  put('D', 500);
            put('M', 1000);
    }};
    public static int romanToInteger(String s) {
        int res = 0;
        int i = 0;
        while (i < s.length()-1) {
            if (romanBase.get(s.charAt(i)) < romanBase.get(s.charAt(i+1))){
                res += romanBase.get(s.charAt(i+1)) - romanBase.get(s.charAt(i));
                i+=2;
            } else {
                res += romanBase.get(s.charAt(i));
                i++;
            }
        }
        if (i < s.length()) {
            res += romanBase.get(s.charAt(i));
        }
        return res;

    }
    public static int romanToInteger2(String s) {
        int res = 0;
        int preNum = romanBase.get(s.charAt(0));
        for (int i=1; i<s.length(); i++) {
            int curNum = romanBase.get(s.charAt(i));
            if (curNum > preNum) {
                res -= preNum;
            } else {
                res += preNum;
            }
            preNum = curNum;
        }
        res += preNum;
        return res;

    }

    public static String longestCommonPrefix(String[] strs) {
        String res = strs[0];
        for (int i=1; i<strs.length; i++) {
            int j=0;
            while ( j<res.length()&&j<strs[i].length() ) {
                if (res.charAt(j) != strs[i].charAt(j)) {
                    break;
                }
                j++;
            }
            res = res.substring(0,j);
            if (res.equals("")) {
                return res;
            }
        }
        return res;
    }

    public static int searchInsertPosition(int[] nums, int target) {
        int begin = 0;
        int end = nums.length;
        int mid = 0;
        while(begin < end) {
            mid = (begin + end ) /2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] >target) {
                end = mid;
            } else {
                begin = mid+1;
            }
        }
        return begin;
    }

    public static int maximumSubarray(int[] nums) {
        int maxSum = nums[0];
        int cur = nums[0];
        for (int i=1; i<nums.length; i++) {
            if (cur < 0) {
                cur = nums[i];
            } else {
                cur += nums[i];
            }
            if (cur > maxSum) {
                maxSum = cur;
            }

        }
        return maxSum;
    }

    public static int climbStairs(int n) {
        int[] methodNums = new int[n+1];
        methodNums[0] = 1;
        methodNums[1] = 1;
        for (int i=2; i<n+1; i++) {
            methodNums[i] = 1;
            for (int j=i-2; j>=0; j--) {
                methodNums[i] += methodNums[j];
            }
        }
        return methodNums[n];
    }

    public static ListNode mergeTwoSortedLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode head, cur, temp;
        if (l1.val < l2.val) {
            temp = l1;
            l1 = l1.next;
        } else {
            temp = l2;
            l2 = l2.next;
        }
        head = cur = temp;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                temp = l2;
                l2 = l2.next;
            } else {
                temp = l1;
                l1 = l1.next;
            }
            cur.next = temp;
            cur = cur.next;
        }
        cur.next = l1==null?l2:l1;
        return head;

    }

    public static int[] getKMPnext(String s){
        int[] next = new int[s.length()];
        next[0] = -1;
        int i = 0;
        int j = -1;
        while (i < s.length() - 1) {
            if (j == -1 || s.charAt(i) == s.charAt(j)) {
                next[++i] = ++j;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    public static int[] getKMPnext2(String s) {
        int[] next = new int[s.length()];
        next[0] = -1;
        int i = 0;
        int j = -1;
        while (i < s.length() - 1 ) {
            if ((j == -1 || s.charAt(i) == s.charAt(j))) {
                ++i;
                ++j;
                if (s.charAt(i) != s.charAt(j)) next[i] = j;
                else next[i] = next[j];
            } else {
                j = next[j];
            }
        }
        return next;
    }
    public static int strStr(String haystack, String needle) {
        if (needle.length() == 0) return 0;
        if (haystack.length() == 0) return -1;
        int i = 0;
        int j = 0;
        int[] next = getKMPnext(needle);
        char[] haystackChar = haystack.toCharArray();
        char[] needleChar = needle.toCharArray();
        while (i < haystack.length() && j < needle.length()) {
            if (j == -1 || haystackChar[i] == needleChar[j]) {
                i++;
                j++;
            }else {
                j = next[j];
            }

        }
        if (j == needle.length()) return i-j;
        else return -1;
    }

    public static int bestTimetoBuyandSellStock1(int[] prices) {
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i=1; i<prices.length; i++) {
            dp[i][0] = Math.max(dp[i-1][0], prices[i]+dp[i-1][1]);
            dp[i][1] = Math.max(dp[i-1][1], -prices[i]);
        }
        return dp[prices.length - 1][0];

    }
    public static int bestTimetoBuyandSellStock2(int[] prices) {
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i=1; i<prices.length; i++) {
            dp[i][0] = Math.max(dp[i-1][0], prices[i]+dp[i-1][1]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0]-prices[i]);
        }
        return dp[prices.length - 1][0];

    }
    public static int bestTimetoBuyandSellStock2_2(int[] prices) {
        int res = 0;
        int tmp = 0;
        for (int i=1; i<prices.length; i++) {
            tmp = prices[i] - prices[i-1];
            if (tmp > 0) res += tmp;
        }
        return res;
    }

    public static int bestTimetoBuyandSellStock3(int[] prices) {
        int[][][] dp = new int[prices.length][3][2];

        dp[0][1][1] = -prices[0];
        dp[0][2][1] = Integer.MIN_VALUE;
        for (int i=1; i<prices.length; i++) {
            dp[i][1][0] = Math.max(dp[i-1][1][0], dp[i-1][1][1]+prices[i]);
            dp[i][1][1] = Math.max(dp[i-1][1][1], -prices[i]);

            dp[i][2][0] = Math.max(dp[i-1][2][0], dp[i-1][2][1]+prices[i]);
            dp[i][2][1] = Math.max(dp[i-1][2][1], dp[i-1][1][0]-prices[i]);
        }

        return Math.max(dp[prices.length-1][1][0], dp[prices.length-1][2][0]);

    }
    public List<Integer> inorderTraversal(TreeNode root) {
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        List<Integer> res = new ArrayList<Integer>();
        while(!nodeList.isEmpty() || root != null) {
            if (root != null){
                nodeList.add(root);
                root = root.left;
            } else{
                TreeNode tmp = nodeList.remove(nodeList.size()-1);
                res.add(tmp.val);
                root = tmp.right;
            }
        }
        return res;
    }
    public boolean isSymmetric(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        if (root.left == null || root.right == null) {
            return false;
        }
        Stack<TreeNode> leftStack = new Stack<TreeNode>();
        Stack<TreeNode> rightStack = new Stack<TreeNode>();
        TreeNode leftNode = root.left;
        TreeNode rightNode = root.right;
        while(!leftStack.isEmpty() && !rightStack.isEmpty() ||
                leftNode != null && rightNode != null) {

            if (leftNode != null && rightNode != null){
                leftStack.add(leftNode);
                leftNode = leftNode.left;
                rightStack.add(rightNode);
                rightNode = rightNode.right;
            } else if(leftNode != null || rightNode != null) {
                return false;
            }
            else {
                leftNode = leftStack.pop();
                rightNode = rightStack.pop();
                if (rightNode.val != leftNode.val) return false;
                leftNode = leftNode.right;
                rightNode = rightNode.left;
            }
        }
        if (leftNode == null && rightNode == null && leftStack.isEmpty() && rightStack.isEmpty()) {
            return true;
        } else return false;

    }
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int depth = 0;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        queue.offer(null);
        while(!queue.isEmpty()){
            TreeNode tmp = queue.poll();
            while(tmp != null) {
                if (tmp.left != null) queue.offer(tmp.left);
                if (tmp.right != null) queue.offer(tmp.right);
                tmp = queue.poll();
            }
            depth++;
            if (!queue.isEmpty()) queue.offer(null);
        }
        return depth;
    }
    public void toBST_left(TreeNode root, int begin, int end, int[] nums) {
        if (begin == end) return;
        int mid = (begin + end) / 2;
        TreeNode left = new TreeNode();
        left.val = nums[mid];
        root.left = left;
        toBST_left(left, begin, mid, nums);
        toBST_right(left, mid+1, end, nums);
    }
    public void toBST_right(TreeNode root, int begin, int end, int[] nums) {
        if (begin == end) return;
        int mid = (begin + end) / 2;
        TreeNode right = new TreeNode();
        right.val = nums[mid];
        root.right = right;
        toBST_left(right, begin, mid, nums);
        toBST_right(right, mid+1, end, nums);
    }
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0 ) return null;
        TreeNode root = new TreeNode();
        int mid = nums.length / 2;
        root.val = nums[mid];
        toBST_left(root, 0, mid, nums);
        toBST_right(root, mid+1, nums.length, nums);
        return root;
    }
    public int depth(TreeNode root) {
        int leftDepth = 0;
        int rightDepth = 0;
        if (root.left != null) leftDepth = depth(root.left);
        if (root.right != null) rightDepth = depth(root.right);
        if (leftDepth == -1 || rightDepth == -1) return -1;
        if (Math.abs(leftDepth-rightDepth) > 1) return -1;
        return (leftDepth>rightDepth?leftDepth:rightDepth) + 1;
    }
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int leftDepth = 0;
        int rightDepth = 0;
        if (root.left != null) leftDepth = depth(root.left);
        if (root.right != null) rightDepth = depth(root.right);
        if (leftDepth == -1 || rightDepth == -1) return false;
        if (Math.abs(leftDepth-rightDepth) > 1) return false;
        return true;
    }
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        int leftMidDepth = minDepth(root.left);
        int rightMidDepth = minDepth(root.right);
        if (leftMidDepth == 0 || rightMidDepth == 0)
            return  (leftMidDepth>rightMidDepth?leftMidDepth:rightMidDepth) + 1;
        else return (leftMidDepth<rightMidDepth?leftMidDepth:rightMidDepth) + 1;
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null && targetSum == root.val) return true;
        return hasPathSum(root.left, targetSum-root.val) || hasPathSum(root.right, targetSum-root.val);
    }
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        List<Integer> pre = new ArrayList() {{add(1);}};
        int i = 2;
        while (i <= numRows) {
            List<Integer> cur = new ArrayList() {{add(1);}};
            for(int j=1; j<i-1; j++) {
                cur.add(pre.get(j-1) + pre.get(j));
            }
            cur.add(1);
            res.add(pre);
            pre = cur;
            i++;
        }
        res.add(pre);
        return res;
    }
    public static int divide(int a, int b) {
        if(a == 0x80000000 && b == -1){
            return 0x7FFFFFFF;
        }

        if (a == 0 ) return 0;
        int res = 0;
        boolean flag = false;
        if (a > 0 && b > 0) {
            a = -a;
            b = -b;
        }else {
            if (a > 0) {
                a = -a;
                if (b < 0) flag = true;
                else b = -b;
            }else{
                if (b > 0) {
                    flag = true;
                    b = -b;
                }
            }

        }

        while(a <= b) {
            a -= b;
            res++;
        }
        if (flag)
            return -res;

        return res;
    }

}

