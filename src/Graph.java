import java.util.Arrays;
import java.util.List;

public class Graph {
    public static void main(String[] args) {

    }
    //最短路径问题， 三种解法：
    //1.BellmanFord 2. Dijkstra 3. FloydWarshall

    //可以判断是否存在负权回路，则无解返回flase。
    //每条边遍历n-1次
    public int[] BellmanFord(int[][] times, int n, int k) {
        int[] des = new int[n+1];
        Arrays.fill(des, Integer.MAX_VALUE / 2);
        des[k] = 0;
        for (int i=1; i<n; i++) {
            for (int[] a: times) {
                int temp = a[3] + des[a[0]];
                if (des[a[1]] > temp) des[a[1]] = temp;
            }
        }

        return des;
    }

    public void Dijkstra(int[][] times, int n, int k) {

    }
    public void FloydWarshall(int[][] times, int n, int k) {

    }
    public int networkDelayTime(int[][] times, int n, int k) {

        int[] res = BellmanFord(times, n, k);
        int max = 0;
        for (int i=1; i<=n; i++) {
            if (i == k)continue;
            if (res[i] > max) max = res[i];
        }
        if (max >= Integer.MAX_VALUE / 2) return -1;
        else return max;
    }
}
