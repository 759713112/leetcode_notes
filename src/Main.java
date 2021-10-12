//HDOJ
import java.util.*;
public class Main {
    public static void main(String[] args) {
        //sortedInsert();
        //payoff2021();
       //setA_setB();
        numberSequence();
    }
    public static void numberSequence() {
        int A, B, n, i;
        int[] res = new int[1001];
        res[1] = 1;
        res[2] = 1;
        Scanner scan  = new Scanner(System.in);
        while (true) {
            A = scan.nextInt();
            B = scan.nextInt();
            n = scan.nextInt();
            if (A==0 && B==0 && n==0) break;
            for (i=3; i<=1000 && i<=n; i++) {
                res[i] = (A * res[i-1] + B * res[i-2]) % 7;
                if (res[i] == 1 && res[i-1] == 1) {
                    break;
                }
            }
            if (n < i) System.out.println(res[n]);
            else {
                int T = i-2;
                int temp = n % T;
                if (temp == 0) System.out.println(res[T]);
                else System.out.println(res[temp]);
            }
            //System.out.println(Arrays.toString(res));


        }
    }
    public static void sortedInsert() {
        int n;
        int m;
        Scanner scan  = new Scanner(System.in);
        n = scan.nextInt();
        m = scan.nextInt();
        while (!(m==0 && n ==0)) {
            int[] array = new int[n+1];
            int i = 0;
            if (n == 0) array[0] = m;
            else{
                for (; i<n; i++) {
                    array[i] = scan.nextInt();
                }
                for (i=n-1; i>=0; i--) {
                    if (array[i] > m) array[i+1] = array[i];
                    else break;
                }
                array[i+1] = m;
            }

            for(i=0; i<array.length-1; i++) {
                System.out.print(array[i]+" ");
            }
            System.out.println(array[i]);
            n = scan.nextInt();
            m = scan.nextInt();
        }
    }
    public static void payoff2021() {
        int n;
        Scanner scan  = new Scanner(System.in);

        while (scan.hasNext()) {
            n = scan.nextInt();
            if (n == 0) break;
            int rmbNums = 0;
            for(int i=0; i<n; i++) {
                int onePay = scan.nextInt();
                rmbNums += onePay / 100;
                onePay %= 100;
                rmbNums += onePay / 50;
                onePay %= 50;
                rmbNums += onePay / 10;
                onePay %= 10;
                rmbNums += onePay / 5;
                onePay %= 5;
                rmbNums += onePay / 2;
                onePay %= 2;
                rmbNums += onePay;
            }
            System.out.println(rmbNums);
        }
    }
    public static void setA_setB() {
        int n;
        int m;
        Scanner scan  = new Scanner(System.in);
        while (true) {
            n = scan.nextInt();
            m = scan.nextInt();
            if (m == 0 && n == 0) break;
            Set<Integer> a = new HashSet<>();
            Set<Integer> b = new HashSet<>();
            for (int i=0; i<n; i++) {
                a.add(scan.nextInt());
            }
            for (int i=0; i<m; i++) {
                b.add(scan.nextInt());
            }
            a.removeAll(b);
            if (a.isEmpty()) System.out.println("NULL");
            else{
                for(int i: a){
                    System.out.print(i+" ");
                }
                System.out.println();
            }

        }

    }


}

