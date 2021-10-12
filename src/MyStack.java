import java.net.Inet4Address;
import java.util.LinkedList;

class MyStack {
    private LinkedList<Integer> curQueue, annotherQueue;
    /** Initialize your data structure here. */
    public MyStack() {
        curQueue = new LinkedList<Integer>();
        annotherQueue = new LinkedList<Integer>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        curQueue.addLast(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        if (curQueue.size()<1) return 0;
        while (curQueue.size() != 1) {
            annotherQueue.addLast(curQueue.removeFirst());
        }
        LinkedList<Integer> temp = curQueue;
        curQueue = annotherQueue;
        annotherQueue = temp;
        return annotherQueue.removeFirst();

    }

    /** Get the top element. */
    public int top() {
        if (curQueue.size()<1) return 0;
        while (curQueue.size() != 1) {
            annotherQueue.addLast(curQueue.removeFirst());
        }
        LinkedList<Integer> temp = curQueue;
        curQueue = annotherQueue;
        annotherQueue = temp;
        int res = annotherQueue.removeFirst();
        curQueue.addLast(res);
        return res;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return curQueue.isEmpty();
    }
}
