import java.util.Arrays;
import java.util.List;

public class Recursion {
    public static void main(String[] args) {
        Recursion recursion = new Recursion();
        //hanoi(5, "A", "B", "C");
        //perm(5, 5, new int[6]);
        ListNode l1 = new ListNode(4);
        ListNode l2 = new ListNode(3, l1);
        ListNode l3 = new ListNode (2, l2);
        ListNode l4 = new ListNode(1, l3);
        //reorderList(l4);
        recursion.isMatch("aab", "c*a*b");
    }
    public boolean recursion_verifyPostorder(int[] postorder, int i, int j){
        if (j-i <=1) return true;
        int index = -1;
        for (int k =0; k<j; k++) {
            if (postorder[k] > postorder[j]) {
                if (index == -1) index = k;
            } else {
                if (index != -1) return false;
            }
        }
        return recursion_verifyPostorder(postorder, i, index-1) && recursion_verifyPostorder(postorder, index, j-1);
    }
    public boolean verifyPostorder(int[] postorder) {
        if (postorder.length == 1) return true;
        return recursion_verifyPostorder(postorder, 0, postorder.length-1);
    }
    public int sumNums(int n) {
        boolean flag = (n == 1) && ((n += sumNums(n-1))>0);
        return n;
    }
    //二进制角度，或者二分法
    public double myPow(double x, int n) {
        long b = n;
        double ans = 1;
        if (b < 0 ){
            b = -b;
            x = 1 / x;
        }
        while (b > 0) {
            if ((b & 1) != 0) ans *= x;
            x *= x;
            b >>= 1;
        }
        return ans;
    }
    //正则表达式匹配
    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        dp[0][0] = true;
        for (int i=0; i<=s.length(); i++){
            for (int j=1; j<=p.length(); j++) {
                if (p.charAt(j-1) == '*') {
                    if (match(s, p, i, j-1)) dp[i][j] = dp[i][j-2] || dp[i-1][j];
                    else dp[i][j] = dp[i][j-2];
                } else{
                    if (match(s, p, i, j)) dp[i][j] = dp[i-1][j-1];
                }
            }
        }
        return dp[s.length()][p.length()];
    }
    public boolean match(String s, String p, int i, int j) {
        if (i == 0) return false;
        if (p.charAt(j-1) == '.') return true;
        if (s.charAt(i-1) == p.charAt(j-1)) return true;
        return false;
    }
    public int multiply(int A, int B) {
        int min = Math.min(A, B);
        int max = Math.max(A, B);
        int ans = 0;
        for (int i=0; i<63; i++) {
            if ((min & 1) == 1) ans += max<<i;
            min >>= 1;
        }
        return ans;
//        if (B == 0) return 0;
//        if (B > 0) {
//            return A + multiply(A, B-1);
//        } else {
//            return multiply(A, B+1) - A;
//        }

    }
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next ==null ) return true;
        ListNode slow = head, fast = head;
        //找出中间
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            if (fast == null) break;
            slow = slow.next;

        }
        ListNode mid_first;
        mid_first = slow.next;
        slow.next = null;
        ListNode head1 = head, head2, temp1, temp2;
        head2 = reserve(mid_first);

        while (head2 != null) {
            if (head1.val == head2.val){
                head1 = head1.next;
                head2 = head2.next;
            } else return false;

        }
        return true;
    }

    public static void perm(int n, int m, int[] p){
        if (m == 0) System.out.println(Arrays.toString(p));
        else {
            for (int i=1; i<=n; i++) {
                if (p[i] == 0) {
                    p[i] = m;
                    perm(n, m-1, p);
                    p[i] = 0;
                }
            }
        }
    }
    public static  ListNode reserve(ListNode head) {
        if (head == null || head.next ==null) return head;
        ListNode hair = new ListNode(0);
        ListNode pre, nex, temp;
        nex = head.next;
        head.next = null;
        pre = nex;
        nex = nex.next;
        pre.next = head;
        while (nex != null) {
            temp = nex.next;
            nex.next = pre;
            pre = nex;
            nex = temp;
        }
        return pre;

    }
    public static void reorderList(ListNode head) {
        if (head == null || head.next ==null || head.next.next == null) return;
        ListNode slow = head, fast = head;
        //找出中间
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            if (fast == null) break;
            slow = slow.next;

        }
        ListNode mid_first;
        mid_first = slow.next;
        slow.next = null;
        ListNode head1 = head, head2, temp1, temp2;
        head2 = reserve(mid_first);

        while (head2 != null) {
            temp1 = head1.next;
            temp2 = head2.next;
            head1.next = head2;
            head2.next = temp1;
            head1 = temp1;
            head2 = temp2;
        }
    }
    public static void hanoi(int n, String a, String b, String c) {
        if (n == 1) System.out.println("move "+ n + " from " + a + " to " +c);
        else{
            hanoi(n-1, a, c, b);
            System.out.println("move "+ n + " from " + a + "to" +c);
            hanoi(n-1, b, a, c);
        }

    }
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) return head;
        ListNode pre_last, cur_last=null, cur, temp, next=null;
        ListNode root = new ListNode(0);
        pre_last = root;
        cur = head;
        while (cur != null) {
            int i=1;
            for (; i<k && cur != null; i++) {
                if (i==1) {
                    cur_last = cur;
                    if (cur.next == null) break;
                    next = cur.next;
                    cur.next = null;
                    temp = next.next;
                    next.next = cur;
                    cur = temp;
                } else{
                    temp = cur.next;
                    cur.next = next;
                    next = cur;
                    cur = temp;
                }
            }
            //下一部分只有一个节点
            if (i == 1) {
                pre_last.next = cur;
                cur = null;
            } else if (i==k) {
                pre_last.next = next;
                pre_last = cur_last;
            } else {
                for (int j=1; j<i; j++){
                    if (j==1){
                        cur = next.next;
                        next.next = null;
                        temp = cur.next;
                        cur.next = next;
                        next = cur;
                        cur = temp;
                    } else {
                        temp = cur.next;
                        cur.next = next;
                        next= cur;
                        cur = temp;
                    }
                }
                pre_last.next = next;
                cur = null;
            }
        }
        return root.next;
    }
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode temp = head.next;
        head.next = swapPairs(head.next.next);
        temp.next = head;
        return temp;
    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return null;
        if (l1 == null) l1 = new ListNode(0);
        if (l2 == null) l2 = new ListNode(0);
        l1.val += l2.val;
        if (l1.val < 10) {
            l1.next = addTwoNumbers(l1.next, l2.next);
        }else {
            l1.val %= 10;
            if (l1.next == null) {
                l1.next = addTwoNumbers(new ListNode(1), l2.next);
            } else {
                l1.next.val += 1;
                l1.next = addTwoNumbers(l1.next, l2.next);
            }
        }
        return l1;
    }
}
