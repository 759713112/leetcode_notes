import java.util.*;

public class SortProblem {
    public static void main(String[] args) {
        //sortArray_tableInsert(new int[]{49,38,65,97,76,13,27,49});
        //int[] nums = new int[]{3,0,-2,-1,1,2};
        //sortColors(new int[]{1,2,1});
        //heapSort(nums, 0, nums.length);
//        ListNode l1 = new ListNode(3);
//        ListNode l2 = new ListNode(1, l1);
//        ListNode l3 = new ListNode (2, l2);
//        ListNode l4 = new ListNode(4, l3);
//        sortList(l4);
        //maximumGap(new int[] {1,10000000});
        //groupAnagrams(new String[]{"ddddddddddg","dgggggggggg"});
        //pileBox(new int[][] {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}});
        //smallestDifference(new int[]{1, -2147483647}, new int[]{2147483647,0});
        //lengthOfLIS(new int[]{4,10,4,3,8,9});
        topKFrequent(new int[]{1,6,8,9,3}, 2);
    }
    public int[] getTriggerTime(int[][] increase, int[][] requirements) {
        int length = increase.length+1;
        int[][] source = new int[length][3];
        for (int i=1; i<length; i++) {
            source[i][0] = source[i-1][0] + increase[i-1][0];
            source[i][1] = source[i-1][1] + increase[i-1][1];
            source[i][2] = source[i-1][2] + increase[i-1][2];
        }
        int[] res = new int[requirements.length];

        for (int i=0; i<requirements.length; i++) {
            if (source[length-1][0] < requirements[i][0] ||
                    source[length-1][1] < requirements[i][1] ||
                    source[length-1][2] < requirements[i][2]){
                res[i] = -1;
                continue;
            }
            int low = 0;
            int high = length-1;
            while (low < high) {
                int mid = (low + high) / 2;
                if (source[mid][0] < requirements[i][0] ||
                        source[mid][1] < requirements[i][1] ||
                        source[mid][2] < requirements[i][2]) {
                    low = mid + 1;
                } else {
                    high = mid -1;
                }
            }
            if (source[low][0] < requirements[i][0] ||
                    source[low][1] < requirements[i][1] ||
                    source[low][2] < requirements[i][2])
                res[i] = low + 1;
            else res[i] = low;
        }
        return res;
    }
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i=0; i<nums.length; i++) {
            int cur = map.getOrDefault(nums[i], 0);
            map.put(nums[i], ++cur);
        }
        PriorityQueue<Integer[]> queue = new PriorityQueue<>(nums.length, (a, b)-> b[1] - a[1]);

        map.forEach((key, value)-> {
            queue.add(new Integer[]{key, value});
        });
        int[] res = new int[k];
        for (int i=0; i<k; i++) {
            res[i] = queue.poll()[0];
        }
        return res;
    }
    public static void swap(int[] nums, int idx1, int idx2) {
        nums[idx1] ^= nums[idx2];
        nums[idx2] ^= nums[idx1];
        nums[idx1] ^= nums[idx2];
    }
    public static void wiggleSort(int[] nums) {
        if (nums.length <= 2) return;
        boolean peek =false;
        if (nums[0] >= nums[1] && nums[1] > nums[2] || nums[0] <= nums[1] && nums[1] < nums[2]){
            swap(nums, 1, 2);
        }
        if (nums[2] >= nums[1] && nums[1] <= nums[0]) peek = true;
        for (int i=3; i<nums.length; i++) {
            if (peek) {
                if (nums[i] > nums[i-1]) swap(nums, i, i-1);

            }else if (nums[i] < nums[i-1]) {
                swap(nums, i, i-1);
            }
            peek = !peek;
        }
    }
    public List<List<Integer>> pairSums(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        if (nums.length < 2) return res;
        int first = 0;
        int last = nums.length-1;
        while (first != last) {
            if (nums[first] + nums[last] == target) {
                res.add(List.of(nums[first++], nums[last++]));
            } else if (nums[first] + nums[last] < target) {
                first++;
            } else {
                last--;
            }
        }
        return res;
    }
    //最长递增子序列 dp较慢
    //转换dp，为记录最长最后的最小值
    public static int lengthOfLIS(int[] nums) {
        if (nums.length <= 1) return nums.length;
        int res = 0;
        //dp算法
//        int[] dp = new int[nums.length];
//        dp[0] = 1;
//        for (int i=1; i< nums.length; i++) {
//            int max = 0;
//            for (int j=0; j<i; j++) {
//                if (nums[j] < nums[i]) {
//                    max = Math.max(max, dp[j]);
//                }
//            }
//            dp[i] = max + 1;
//            res = Math.max(dp[i], res);
//        }
        int[] tail = new int[nums.length];
        tail[0] = nums[0];
        for (int i=1; i<nums.length; i++) {
            int j=res;
            if (tail[j] < nums[i]) {
                res++;
                tail[res] = nums[i];
            } else {
                while(--j >= 0 && tail[j] > nums[i]);
                tail[j+1] = Math.min(nums[i], tail[j+1]);
            }
        }
        return res;
    }
    public static int bestSeqAtIndex(int[] height, int[] weight) {
        int[][] height_weight = new int[height.length][2];
        for (int i=0; i<height.length; i++) {
            height_weight[i] = new int[]{height[i], weight[i]};
        }
        Arrays.sort(height_weight, (a,b)-> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        for (int i=0; i<height.length; i++) {
            weight[i] = height_weight[i][1];
        }
        return lengthOfLIS(weight);
    }
    public int[] smallestK(int[] arr, int k) {
        if (k == 0) return new int[0];
        int[] res = new int[k];
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int i=0;
        for (; i<arr.length-k; i++) {
            queue.add(arr[i]);
        }
        while(--k >= 0) {
            queue.add(arr[i++]);
            res[k] = queue.poll();
        }
        return res;
    }
    public static int[] subSort(int[] array) {
        int m = -1;
        int n = -1;
        int[] res = new int[2];
        int length = array.length;
        if (length > 1) {
            //从左向右遍历，如果当前元素比它之前的最大的元素小，说明不是升序的，更新n为当前元素索引，继续遍历直到末尾
            //从右向左遍历，如果当前元素比它之后的最小的元素大，说明不是降序的，更新m为当前元素索引，继续遍历直到开始
            int max = array[0];
            int min = array[length - 1];
            for (int begin = 0; begin < length; begin++) {
                int end = length - 1 - begin;
                if (array[begin] < max) {
                    n = begin;
                } else {
                    max = array[begin];
                }
                if (array[end] > min) {
                    m = end;
                } else {
                    min = array[end];
                }
            }
        }
        res[0] = m;
        res[1] = n;
        return res;
        /*if (array.length <= 1) return new int[]{-1, -1};
        int[] first_last = new int[]{0, array.length-1};
        while (first_last[0] < array.length-1 && array[first_last[0]] <= array[first_last[0] + 1]) first_last[0]++;
        if (first_last[0] == array.length-1) return new int[]{-1, -1};
        while (first_last[1] > 0 && array[first_last[1]] >= array[first_last[1] -1]) first_last[1]--;
        int j = first_last[1];
        for (int i=first_last[0]+1; i<=j; i++) {
            if (first_last[0] == 0 && first_last[1] == array.length-1) return first_last;
            if (first_last[0] != 0  && array[i] < array[first_last[0]]) {
                do {
                    first_last[0]--;
                } while (first_last[0] >=0  && array[i] < array[first_last[0]]);
                first_last[0]++;
            }
            if (first_last[1] != array.length-1 && array[i] > array[first_last[1]]) {
                do {
                    first_last[1]++;
                } while (first_last[1] <=array.length  && array[i] > array[first_last[1]]);
                first_last[0]--;
            }
        }

        return first_last;*/
    }
    public static int smallestDifference(int[] a, int[] b) {
        long res = Integer.MAX_VALUE;
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0;
        int j = 0;
        while (i < a.length && j < b.length) {
            if (a[i] == b[j]) return 0;
            else if (a[i] < b[j]) res = Math.min((long)b[j] - a[i++], res);
            else res = Math.min((long)a[i] - b[j++], res);
        }

        return (int)res;
    }
    //俄罗斯套娃
    public static int pileBox(int[][] box) {
        if (box.length < 1) return 0;
        if (box.length == 1) return box[0][2];
        int res = 0;
        Arrays.sort(box, Comparator.comparingInt(a -> a[0]));
        int[] dp = new int[box.length];
        for (int i=0; i<box.length; i++) {
            int max = 0;
            for (int j=0; j<i; j++) {
                if (box[i][0] > box[j][0] && box[i][1] > box[j][1] && box[i][2] > box[j][2]) {
                    max = Math.max(dp[j], max);
                }
            }
            dp[i] = box[i][2] + max;
            res = Math.max(res, max);
        }
        return res;
    }
    public boolean CheckPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        int[] counts = new int[26];
        for (int i=0; i<s1.length(); i++) counts[s1.charAt(i)-'a']++;
        for (int i=0; i<s2.length(); i++) {
            if (--counts[s2.charAt(i)-'a'] < 0) return false;
        }
        return true;
    }
    //考虑传入string 包含的内容 英文字母？ascii码？ Unicode？
    //下面为英文字母
    public boolean isUnique(String astr) {
        int length = astr.length();
        if (length <= 1) return true;
        if (length > 52) return false;
        long bits = 0; //64位 大于'z' - 'A' +1 ;
        bits |= 1L<<(astr.charAt(0) - 'A');
        for (int i=1; i<length; i++) {
            int move = astr.charAt(0) - 'A';
            if ((bits & 1L<<move) != 0) return false;
            else  bits |= (1L<<move);
        }
        return true;
    }
    public static int maximumGap(int[] nums) {
        if (nums.length <= 1) return 0;
        if (nums.length == 2) return Math.abs(nums[0]-nums[1]);
        int max=nums[0], min=nums[0];
        for (int i=1; i<nums.length; i++) {
            if (nums[i] < min) min = nums[i];
            else if (nums[i] > max)  max = nums[i];
        }
        int interval = (max - min)  / (nums.length - 1);
        if ((max - min)  % (nums.length - 1) != 0) interval++;
        int[][] box_min_max = new int[2][nums.length-1];
        Arrays.fill(box_min_max[0], Integer.MAX_VALUE);
        Arrays.fill(box_min_max[1], -1);
        int index;
        for (int i=0; i<nums.length; i++) {
            if (nums[i] == min || nums[i] == max) continue;
            index = (nums[i] - min) / interval;
            box_min_max[1][index] = Math.max(nums[i], box_min_max[1][index]);
            box_min_max[0][index] = Math.min(nums[i], box_min_max[0][index]);
        }
        int max_gap = 0;
        int pre_max = min;
        for (int i=0; i<nums.length-1; i++) {
            if (box_min_max[1][i] == -1) continue;
            max_gap = Math.max(max_gap, box_min_max[0][i]-pre_max);
            pre_max = box_min_max[1][i];
        }
        max_gap = Math.max(max_gap, max-pre_max);
        return max_gap;
    }
    public static String largestNumber(int[] nums) {
        Integer[] array = new Integer[nums.length];
        for (int i=0; i<nums.length; i++) {
            array[i] = nums[i];
        }
        Arrays.sort(array, (a, b) -> {  //
            long sa = 10, sb = 10;
            while (sa <= b) sa *= 10;
            while (sb <= a) sb *= 10;
            return (int)(sb*b+a-sa*a-b);
        });
        if (array[0] == 0) return "0";
        StringBuffer temp = new StringBuffer();
        for (Integer a: array){
            temp.append(a);
        }
        return temp.toString();
    }
    public static ListNode temp = new ListNode();
    public static void merge(ListNode[] head) {
        ListNode head1 = head[0];
        ListNode head2 = head[1];
        ListNode  cur;
        cur = temp;
        while (head1 != null || head2 != null) {
            if (head1 != null && (head2 == null || head1.val < head2.val)) {
                cur.next = head1;
                cur = head1;
                head1 = head1.next;
            } else {
                cur.next = head2;
                cur = head2;
                head2 = head2.next;
            }
        }
        head[0] = temp.next;
        head[1] = cur;
    }
    public static ListNode sortList(ListNode head) {
        if(head==null || head.next==null) return head;
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        ListNode root = new ListNode(0, head);
        ListNode cur, pre;
        ListNode[] heads = new ListNode[2];
        for (int mLength=1; mLength<length; mLength<<=1) {
                pre = root;
                cur = root.next;
                while (cur != null) {
                    heads[0] = cur;
                    for (int i=0; i<mLength-1 && cur!=null; i++) {
                        cur = cur.next;
                    }
                    if (cur != null) {
                        if (cur.next != null) {
                            heads[1] = cur.next;
                            cur.next = null;
                            cur = heads[1];
                            for (int i=0; i<mLength-1 && cur!=null; i++){
                                cur = cur.next;
                            }
                            if (cur != null) {
                                ListNode temp = cur;
                                cur = cur.next;
                                temp.next = null;
                            }
                            merge(heads);
                        }
                        else cur = null;
                    }
                    pre.next = heads[0];
                    pre = heads[1];
                }

        }

        return root.next;
    }
    public static ListNode insertionSortList(ListNode head) {
        if(head==null||head.next==null) return head;
        ListNode root = new ListNode(Integer.MIN_VALUE, head);
        ListNode pre, cur, next=head.next;
        head.next = null;
        while (next != null) {
            pre=root;
            cur = root.next;
            while (cur != null && cur.val < next.val) {
                pre = cur;
                cur = cur.next;
            }
            pre.next = next;
            next = next.next;
            pre.next.next = cur;

        }
        return root.next;
    }
    public static boolean checkEqualZero(int i, int j, int k) {
        if (i+j+k==0) return true;
        else return false;
    }
    //双指针，一个从前往后，一个从后往前
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (nums.length < 3) return res;
        Arrays.sort(nums);
        for (int first=0; first<nums.length-2; first++){
            if (nums[first] > 0) break;
            if (first==0 || nums[first] != nums[first-1]) {
                int third=nums.length-1;
                for (int second=first+1; second<third; second++) {
                    if (second==first+1 || nums[second] != nums[second-1]) {
                        while (nums[first]+nums[second]+nums[third] > 0 && second<third-1) third = third-1;
                        if( checkEqualZero(nums[first], nums[second], nums[third]) ) {
                            res.add(Arrays.asList(nums[first], nums[second], nums[third]));
                            third--;
                        }
                    }

                }
            }
        }
        return res;
    }

    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int min = nums[0]+nums[1]+nums[2] -target;
        for (int first=0; first<nums.length-2; first++){
            if (nums[first] >0 && nums[first] > target) break;
            int third=nums.length-1;
            for (int second=first+1; second<third; second++) {
                do {
                    int temp = nums[first]+nums[second]+nums[third] - target;
                    if (temp == 0) return target;
                    if (Math.abs(temp) < Math.abs(min)) min = temp;
                    if (temp < 0) break;
                    third--;
                }while (second<third);
            }

        }
        return min+target;
    }
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (nums.length < 4) return res;
        Arrays.sort(nums);
        for (int first=0; first<nums.length-3; first++) {
            int temp1 = nums[first];
            if (temp1 > 0 && temp1 > target) break;
            if (first==0 || nums[first] != nums[first-1]) {
                for (int second=first+1; second<nums.length-2; second++){
                    int temp2 = temp1 + nums[second];
                    if (temp2 > 0 && temp2 > target) break;
                    if (second==first+1 || nums[second] != nums[second-1]) {
                        int forth = nums.length-1;
                        for (int third=second+1; third<forth; third++) {
                            if (third==second+1 || nums[third] != nums[third-1]){
                                int temp4;
                                do {
                                    temp4 = temp2 + nums[third] + nums[forth] - target;
                                    if (temp4 <= 0) break;
                                    forth--;
                                }while(third<forth);
                                if (temp4 == 0) {
                                    res.add(Arrays.asList(nums[first], nums[second], nums[third], nums[forth]));
                                }
                            }
                        }
                    }

                }
            }
        }
        return res;
    }
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> res = new HashMap<>();
        for (String s: strs) {
            char[] count = new char[26];
            //类似基数排序
            int length = s.length();
            for (int i = 0; i < length; i++) {
                count[s.charAt(i) - 'a']++;
            }
            StringBuffer sb = new StringBuffer();
            for(int i=0; i<26; i++){
                if (count[i] != 0){
                    sb.append((char)(i+'a'));
                    sb.append(count[i]);
                }
            }
            String key = sb.toString();
            List<String> list = res.getOrDefault(key, new ArrayList<String>());
            list.add(s);
            res.put(key, list);
        }
        return new ArrayList<List<String>>(res.values());
    }
    public int[][] merge(int[][] intervals) {
        if (intervals.length < 2) return intervals;
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> res = new ArrayList<>();
        int[] cur = intervals[0];
        for (int i=1; i<intervals.length; i++) {
            if (intervals[i][0] < cur[1]) {
                cur[1] =  intervals[i][1] > cur[1] ?intervals[i][1] : cur[1];
            }else {
                res.add(cur);
                cur = intervals[i];
            }
        }
        return res.toArray(new int[res.size()][2]);
    }
    public static int partition(int[] nums, int low, int high) {
        int temp = nums[low];
        while (low < high) {
            while (low < high && nums[high] >= temp) high--;
            nums[low] = nums[high];
            while (low < high && nums[low] <= temp) low++;
            nums[high] = nums[low];
        }
        nums[low] = temp;
        return low;
    }
    public static void sortColors(int[] nums) {
        if (nums.length <=1 ) return;
        int pZero = 0;
        int pTow = nums.length;
        int i=0;
        while (i < pTow) {
            if (nums[i] == 0) {
                nums[i++] = nums[pZero];
                nums[pZero++] = 0;
            }else if (nums[i] == 1) {
                i++;
            }else {
                nums[i] = nums[--pTow];
                nums[pTow] = 2;
            }
        }

    }
}
