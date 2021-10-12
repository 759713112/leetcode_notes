import com.sun.source.tree.Tree;

import javax.swing.text.Position;
import java.awt.color.ICC_ProfileRGB;
import java.util.*;

public class DFS_BFS_Problem {
    public static void main(String[] args) {

    }
    class Position{
        int sr;
        int sc;
        int value;
        public Position(int _sr, int _sc, int _value) {
            sr = _sr;
            sc = _sc;
            value = _value;
        }
    }
    static Comparator<Position> cLNode = new Comparator<Position>() {
        public int compare(Position o1, Position o2) {
            return o2.value-o1.value;
        }
    };
    public int longestIncreasingPathDFS(int[][] matrix, int[][] forwardMax, int sr, int sc) {
        if (forwardMax[sr][sc] != 0) return forwardMax[sr][sc];
        int curMax = 0;
        PriorityQueue<Position> bigger = new PriorityQueue<>(cLNode);
        for (int i=0; i<4; i++) {
            int _sr = 0, _sc = 0;
            boolean flag = false;
            switch (i) {
                case 0: if (sr > 0) { _sr = sr - 1; _sc = sc; flag=true;  break;}
                case 1: if (sr < matrix.length-1) { _sr = sr + 1; _sc = sc; flag=true; break;}
                case 2: if (sc > 0) { _sr = sr; _sc = sc - 1; flag=true; break;}
                case 3: if (sc < matrix[0].length-1) { _sr = sr; _sc = sc + 1; flag=true; break;}
            }
            if (flag && matrix[_sr][_sc] > matrix[sr][sc]) {
                bigger.add(new Position(_sr, _sc, matrix[_sr][_sc]));
            }
        }
        for (Position p: bigger) {
            int temp = longestIncreasingPathDFS(matrix, forwardMax, p.sr, p.sc);
            if (temp > curMax) curMax = temp;
        }
        forwardMax[sr][sc] = curMax + 1;
        return curMax + 1;
    }
    //dfs搜索+dp逐步注释当前点能走的最长距离
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix.length == 0) return 0;
        int[][] forwardMax = new int[matrix.length][matrix[0].length];
        int max = 0;
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[0].length; j++) {
                if (forwardMax[i][j] == 0) {
                    int temp = longestIncreasingPathDFS(matrix, forwardMax, i, j);
                    if (temp > max) max = temp;
                }
            }
        }
        return max;
    }
    public void findItineraryDFS(String address, Map<String, PriorityQueue<String>> map, List<String> res) {
        while (map.containsKey(address) && map.get(address).size() > 0) {
            String temp = map.get(address).poll();
            findItineraryDFS(temp, map, res);
        }
        res.add(0, address);
    }
    public List<String> findItinerary(List<List<String>> tickets) {
        List<String> res = new LinkedList<>();
        if (tickets == null || tickets.size() == 0)return res;
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        for (List<String> ticket: tickets) {
            PriorityQueue<String> temp = map.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>());
            temp.add(ticket.get(1));
        }
        findItineraryDFS("JFK", map, res);
        return res;
    }
    public int sumNumbers(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        int cur = 0;
        int res = 0;
        TreeNode prev = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.add(root);
                cur = cur * 10 + root.val;
                root = root.left;
            }
            root = stack.pop();
            if (root.right == null || root.right == prev) {
                if (root.left == null && root.right == null) {
                    res += cur;
                }
                prev = root;
                cur /= 10;
                root = null;
            }else {
                stack.add(root);
                root = root.right;
            }
        }
        return res;
    }

    public boolean checkSubTree(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        if (t1.val == t2.val) {
            if (checkSubTree(t1.left, t2.left) && checkSubTree(t1.right, t2.right)) return true;
        } else {
            if (checkSubTree(t1.left, t2) || checkSubTree(t1.right, t2)) return true;

        }
        return false;
    }
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image[sr][sc] == newColor) return image;
        int preColor = image[sr][sc];
        image[sr][sc] = newColor;
        if (sr > 0 && image[sr-1][sc] == preColor) floodFill(image, sr-1, sc, newColor);
        if (sc > 0 && image[sr][sc-1] == preColor) floodFill(image, sr, sc-1, newColor);
        if (sr < image.length-1 && image[sr+1][sc] == preColor) floodFill(image, sr+1, sc, newColor);
        if (sc < image[0].length-1 && image[sr][sc+1] == preColor) floodFill(image, sr, sc+1, newColor);
        return image;
    }

    class Result {
        int depth;
        TreeNode node;
        Result(int _depth, TreeNode _node) {
            depth = _depth;
            node = _node;
        }
    }
    public Result subtreeWithAllDeepestDfs(TreeNode root) {
        if (root == null) return new Result(0, null);
        if (root.left == null && root.right == null) return new Result(1, root);
        Result leftResult = subtreeWithAllDeepestDfs(root.left);
        Result rightResult = subtreeWithAllDeepestDfs(root.right);;
        if (leftResult.depth == rightResult.depth) return new Result(leftResult.depth + 1, root);
        else  {
            leftResult.depth++;
            rightResult.depth++;
            return leftResult.depth > rightResult.depth ? leftResult : rightResult;
        }
    }
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        return subtreeWithAllDeepestDfs(root).node;
    }

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        Stack<TreeNode> stack = new Stack<>();
        boolean flag = false;
        while (root != null || !stack.isEmpty()) {
            if (root != null) {
                stack.add(root);
                root = root.left;
            } else {
                root = stack.pop();
                if (flag) return root;
                if (root == p) flag = true;
                root = root.right;
            }
        }
        return null;
    }
    private TreeNode ans;
    public DFS_BFS_Problem() {
        this.ans = null;
    }
    public boolean lowestCommonAncestorDfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return false;
        boolean leftExist = lowestCommonAncestorDfs(root.left, p, q);
        boolean rightExist = lowestCommonAncestorDfs(root.right, p, q);
        if (leftExist && rightExist || ( root == p || root == q) && (leftExist || rightExist))
            ans = root;

        if (root == p || leftExist || rightExist || root == q) return true;
        else return false;
    }
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        lowestCommonAncestorDfs(root, p, q);
        return this.ans;
    }
    public boolean isValidBST_DFS(TreeNode root, int[] max_min) {
        if (root == null) return true;


        if (root.left != null) {
            int[] left_max_min = new int[2];
            if (!isValidBST_DFS(root.left, left_max_min)) return false;
            if (left_max_min[0] >= root.val) return false;
            max_min[1] = left_max_min[1];
        } else max_min[1] = root.val;

        if (root.right != null) {
            int[] right_max_min = new int[2];
            if (!isValidBST_DFS(root.right, right_max_min)) return false;
            if (right_max_min[1] <= root.val) return false;
            max_min[0] = right_max_min[0];
        } else max_min[0] = root.val;

        return true;
    }
    public boolean isValidBST(TreeNode root) {
        return isValidBST_DFS(root, new int[2]);
    }
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) return Arrays.asList(0);
        int[] degrees = new int[n];
        List<List<Integer>> isConnect = new ArrayList<>();
        for (int i=0; i<n; i++) isConnect.add(new ArrayList<Integer>());
        for (int[] edge: edges) {
            degrees[edge[0]]++;
            degrees[edge[1]]++;
            isConnect.get(edge[0]).add(edge[1]);
            isConnect.get(edge[1]).add(edge[0]);
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i=0; i<n; i++) {
            if (degrees[i] == 1) queue.offer(i);
        }
        List<Integer> res = new ArrayList<>();;
        while (!queue.isEmpty()) {
            res = new ArrayList<>();
            int curSize = queue.size();
            for (int i=0; i<curSize; i++) {
                int temp = queue.poll();
                res.add(temp);
                for (Integer j: isConnect.get(temp)) {
                    degrees[j]--;
                    if (degrees[j] == 1) {
                        queue.offer(j);

                    }
                }

            }
        }
        return res;
    }
    public boolean findWhetherExistsPath(int n, int[][] graph, int start, int target) {
        boolean[] isVisited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        ArrayList<Integer>[] g = new ArrayList[n];
        for (int i=0; i<n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i=0; i<graph.length; i++) {
            g[graph[i][0]].add(graph[i][1]);
        }

        queue.offer(start);
        do {
            queue.offer(null);
            Integer temp = queue.poll();
            while (temp != null) {
                ArrayList<Integer> list = g[temp];
                for (Integer integer : list) {
                    if (!isVisited[integer]) {
                        if (integer == target) return true;
                        queue.offer(integer);
                        isVisited[integer] = true;
                    }
                }
                temp = queue.poll();
            }
        }while (!queue.isEmpty());
        return false;
    }
    public TreeNode sortedToBST(int[] nums, int start, int end) {
        if (start >= end) return null;
        int mid = (start + end) / 2;
        TreeNode res = new TreeNode(nums[mid]);
        res.left = sortedToBST(nums, start, mid-1);
        res.right = sortedToBST(nums, mid+1, end);
        return res;
    }
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedToBST(nums, 0, nums.length);
    }

    public Node connect2(Node root) {
        if (root == null) return root;
        Node mostLeft = root;
        Node t = new Node();
        while (mostLeft != null) {
            Node temp = mostLeft;
            Node prev = t;
            prev.next = null;
            while (temp != null) {
                if (temp.left != null && temp.right != null) {
                    prev.next = temp.left;
                    temp.left.next = temp.right;
                    prev = temp.right;
                } else if (temp.left != null) {
                    prev.next = temp.left;
                    prev = temp.left;
                } else if (temp.right != null) {
                    prev.next = temp.right;
                    prev = temp.right;
                }
                temp = temp.next;
            }
            mostLeft = t.next;
        }
        return root;
    }
    //利用next，建立完N层后，用next遍历N层来建立N+1层
    public Node connect(Node root) {
        if (root == null) return root;
        Node mostLeft = root;
        while (mostLeft.left != null) {
            Node temp = mostLeft;
            while (temp.next != null) {
                temp.left.next = temp.right;
                temp.right.next = temp.next.left;
                temp = temp.next;
            }
            mostLeft = mostLeft.left;
        }
        return root;
        //广度优先
//        if (root == null) return root;
//        Queue<Node> queue = new LinkedList<>();
//        queue.offer(root);
//        do {
//            queue.offer(null);
//            Node temp  = queue.poll();
//            while (temp != null) {
//                temp.next = queue.poll();
//                if (temp.left != null) {
//                    queue.offer(temp.left);
//                    queue.offer(temp.right);
//                }
//                temp = temp.next;
//            }
//
//        }while (!queue.isEmpty());
//        return root;
    }
    public TreeNode flattenDfs(TreeNode root) {
        if (root.left == null && root.right == null) return root;

        TreeNode temp1 = null, temp2 = null;
        if (root.left == null || root.right == null) {
            if (root.right == null) {
                root.right = root.left;
                root.left = null;
            }
            temp1 = flattenDfs(root.right);
            return temp1;
        }
        else {
            temp1 = flattenDfs(root.left);
            temp2 = flattenDfs(root.right);
            temp1.right = root.right;
            root.right = root.left;
            root.left = null;
            return temp2;
        }
    }
    public void flatten(TreeNode root) {
        if (root != null) flattenDfs(root);
    }
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new Stack<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode prev = null;
        Stack<Integer> list = new Stack<>();
        int cur = 0;
        while (root != null || stack.size() != 0) {
            while (root != null) {
                list.add(root.val);
                cur += root.val;
                if (cur == targetSum && root.left == null && root.right == null) {
                    res.add(new LinkedList<>(list));
                }
                stack.add(root);
                root = root.left;
            }
            root = stack.pop();
            if (root.right == null || root.right == prev) {
                cur -= root.val;
                list.pop();
                prev = root;
                root = null;
            } else {
                stack.add(root);
                root = root.right;
            }

        }
        return res;
    }
    public TreeNode lowestCommonAncestorSBT(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else {
                return root;
            }
        }
        return null;
    }
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return root;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }
}
