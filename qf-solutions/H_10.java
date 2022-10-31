import java.util.Scanner;

public class Problem_H {

    static int upper_bound(int[] array, int elem) {
        int l = 0, r = array.length, mid;
        while (r - l > 1) {
            mid = (l + r) / 2;
            if (elem < array[mid]) {
                r = mid;
            } else if (elem > array[mid]) {
                l = mid;
            } else {
                return mid;
            }
        }
        if (array[l] > elem) return -1;
        if (l == array.length-1 && array[l] != elem) return -1;
        return l;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] ps = new int[n+1];
        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            int temp = sc.nextInt();
            ps[i] = ps[i-1]+temp;
            max = Math.max(temp, max);
        }
        int q = sc.nextInt();
        int[] T = new int[q];
        for (int i = 0; i < q; i++) T[i] = sc.nextInt();
        for (int t : T) {
            if (t < max) {
                System.out.println("Impossible");
                continue;
            }
            int ans = 0;
            int temp = 0;
            while (temp != -1) {
                ans++;
                int up = upper_bound(ps, ps[temp] + t);
                if (up == ps.length-1) break;
                temp = up;
            }
            if (ans == 0) System.out.println("Impossible"); else System.out.println(ans);
        }
    }
}