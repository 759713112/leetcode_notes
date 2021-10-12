import jdk.jshell.JShell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sort {
    public static void main(String[] args) {
        //sortArray_tableInsert(new int[]{49,38,65,97,76,13,27,49});
        int[] nums = new int[]{3,0,-2,-1,1,2};

        //heapSort(nums, 0, nums.length);

    }
    //插入排序
    public static int[] sortArray_InsertSort(int[] nums) {
        for (int i=1; i<nums.length; i++) {
            int j=i-1;
            int temp = nums[i];
            for (; j>=0 && nums[j] > temp; j--)
                nums[j+1] = nums[j];
            nums[++j] = temp;
        }
        return nums;
    }
    //折半插入排序
    public static int[] sortArray_BInsertSort(int[] nums) {
        int temp, low, high, mid, i, j;
        for (i=1; i<nums.length; i++) {
            temp = nums[i];
            low = 0;
            high = i-1;
            while (low < high) {
                mid = (low + high) / 2;
                if (nums[mid] < temp) low = mid + 1;
                else high = mid - 1;
            }
            for (j=i-1; j>high; j--) nums[j+1] = nums[j];
            nums[++high] = temp;
        }
        return nums;
    }

    //2-路插入排序
    public static int[] sortArray_TowInsertSort(int[] nums) {
        if (nums.length == 0) return nums;
        int[] temp = new int[nums.length];
        temp[0] = nums[0];
        int first=0, last = 0;
        for (int i=1; i<nums.length; i++) {

            if (nums[i] > temp[0]) {
                int j=last;
                for (; j>0; j--){
                    if (temp[j] > nums[i]) temp[j+1] = temp[j];
                    else break;
                }
                temp[j+1] = nums[i];
                last++;
            }else {
                if (first == 0) {
                    temp[nums.length-1] = nums[i];
                    first = nums.length-1;
                }else {
                    int j=first;
                    for ( ; j<nums.length; j++) {
                        if (temp[j] < nums[i])temp[j-1] = temp[j];
                        else break;
                    }
                    temp[j-1] = nums[i];
                    first--;
                }
            }
        }

        for (int i = first, j=0; i!=last; i=(i+1)%nums.length, j++) {
            System.out.println(i);
            nums[j] = temp[i];
        }
        nums[nums.length-1] = temp[last];
        return nums;
    }
    //表插入排序
    public static int[] sortArray_tableInsert(int[] nums) {
        int length = nums.length;
        if (length<=1) return nums;
        int[] table = new int[length+1];
        table[0] = length;
        table[length] = 0;
        for (int i=1; i<length; i++) {
            int temp = table[length];
            if (nums[temp] > nums[i]) {
                table[length] = i;
                table[i] = temp;

            } else {
                int pre = temp;
                temp = table[temp];
                while (temp != length) {
                    if (nums[temp] < nums[i]) {
                        pre = temp;
                        temp = table[temp];
                    }else break;
                }
                table[pre] =  i;
                table[i] = temp;
            }
        }

        int p = table[length];
        for (int i=0; i<length; i++) {
            while (p < i) p = table[p];
            int q = table[p];
            if (p != i) {
                int temp = nums[i];
                nums[i] = nums[p];
                nums[p] = temp;

                table[p] = table[i];
                table[i] = p;
            }
            p = q;
        }
        return nums;
    }
    //希尔排序
    public static void  shellInsert(int[] nums, int dk) {
        for (int i=dk; i< nums.length; i++) {
            if (nums[i] < nums[i-dk]) {
                int temp = nums[i];
                int j=i-dk;
                for (; j>=0 && nums[j] > temp;j-=dk){
                    nums[j+dk] = nums[j];
                }
                nums[j+dk] = temp;
            }
        }
    }
    public static int[]  shellSort(int[] nums) {
        int[] delt = new int[]{5, 3, 1};
        for (int i=0; i<delt.length; i++) {
            shellInsert(nums, delt[i]);
        }
        System.out.println(Arrays.toString(nums));
        return nums;
    }

    //起泡排序
    public static int[] bubbleSort(int[] nums) {

        for (int i=0; i<nums.length-1; i++) {
            boolean flag = true;
            int temp = nums[0];
            int j=0;
            for (; j<nums.length-i-1; j++) {
                if (temp > nums[j+1]) {
                    nums[j] = nums[j+1];
                    flag = false;
                } else {
                    if (!flag) nums[j] = temp;
                    temp = nums[j+1];
                }
            }
            if (flag) break;
            nums[j] = temp;
            System.out.println(Arrays.toString(nums));
        }
        return nums;
    }

    //快速排序,进行了尾递归，中间取值优化， 还有多线程，相同元素聚集没实现，（书上的同时冒泡实现了反而速度降低）
    //基本思想：选取一值， 将比他大的挪到他后面，比他小的挪前面
    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;

//        nums[idx1] ^= nums[idx2];
//        nums[idx2] ^= nums[idx1];
//        nums[idx1] ^= nums[idx2];


    }
    public static int numberOfThree(int[] nums, int low, int high) {
        int mid = low + ((high - low) >> 1);
        if (nums[mid] > nums[high])
        {
            swap(nums, mid, high);
        }
        if (nums[low] > nums[high])
        {
            swap(nums, low, high);
        }
        if (nums[mid] > nums[low])
        {
            swap(nums, mid, low);
        }
        return nums[low];
    }
    public static int partition(int[] nums, int low, int high) {
        int temp = numberOfThree(nums, low, high);
        while (low < high) {
            //增加起泡操作
            while (low < high && nums[high] >= temp) high--;
            nums[low] = nums[high];
            while (low < high && nums[low] <= temp) low++;
            nums[high] = nums[low];
        }
        nums[low] = temp;
        return low;
    }
    public static void QSort(int[] nums, int low, int high) {
        //尾递归
        while (low < high) {
            //优化 数组较小时使用插入排序
            if (high - low + 1 < 10) {
                sortArray_InsertSort(nums, low, high);
                return;
            }
            int pivotloc = partition(nums, low, high);
            QSort(nums, low , pivotloc-1);
            low = pivotloc + 1;
        }
    }
    //服务快排的插入排序
    public static int[] sortArray_InsertSort(int[] nums, int low, int high) {
        for (int i=low+1; i<=high; i++) {
            int j=i-1;
            int temp = nums[i];
            for (; j>=0 && nums[j] > temp; j--)
                nums[j+1] = nums[j];
            nums[++j] = temp;
        }
        return nums;
    }
    public static int[] quickSort(int[] nums) {
        QSort(nums, 0, nums.length-1);
        return nums;
    }

    //选择排序
    public static int[] selectSort(int[] nums) {
        for(int i=0; i<nums.length-1; i++) {
            //、、、、、、、
        }
        return nums;
    }
    //树形选择排序，占用空间较大，优化后为堆排序
    //堆排序（完全二叉树，父节点均不大于（不小于）其左右两孩子节点）
    //建立大堆：从[2/n](向下）非叶子节点，不断筛选
    public static void heapAjust(int[] nums, int s, int temp, int high) {
        //除nums[s]外，其他都有序， 调整nums[s,,m]
        for (int j=s*2+1; j<high; nums[s] = nums[s=j],j=(j<<1)+1) {
            if (j<high-1 /*防止越界*/ && nums[j+1] > nums[j]) ++j;
            if (temp > nums[j]) {
                break;
            }
        }
        nums[s] = temp;
    }
    public static int[] heapSort(int[] nums, int low, int high) {
        for (int i=(low+high)>>>1; i>low;){
            heapAjust(nums, --i, nums[i], high);
        }
        while (--high > low) {
            int max = nums[low];
            heapAjust(nums, low, nums[high], high);
            nums[high] = max;
        }
        return nums;
    }
    public static void radixSort(int[] nums) {
        int max = nums[0];
        for (int i=1; i<nums.length; i++) max = Math.max(nums[i], max);
        int exp = 1;
        List<ArrayList<Integer>> lists = new ArrayList<>();
        while (max > 0) {
            lists.clear();
            for (int i=0; i<10; i++) {
                lists.add(new ArrayList<>());
            }
            for (int i=0; i<nums.length; i++) {
                lists.get((nums[i]/exp)%10).add(nums[i]);
            }
            int index = 0;
            for (int i=0; i<10; i++) {
                List<Integer> temp = lists.get(i);
                for (int j=0; j<temp.size(); j++) {
                    nums[index++] = temp.get(j);
                }
            }
            max /= 10;
            exp *= 10;
        }

    }

}
