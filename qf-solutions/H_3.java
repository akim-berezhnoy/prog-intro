import java.util.Scanner;

public class Problem_H {

    static int upper_bound(int[] array, int elem) {
        int l = 1, r = array.length, mid;
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
        return l;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] ps = new int[n+1];
        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            int temp = sc.nextInt();
            ps[i] = ps[i-1] + temp;
            max = Math.max(temp, max);
        }
        int q = sc.nextInt();
        int[] tarr = new int[q];
        for (int i = 0; i < q; i++) {
            tarr[i] = sc.nextInt();
        }
        System.out.println();
        for (int t : tarr) {
            if (t < max) {
                System.out.println("Impossible");
                continue;
            }
            int ans = 1;
            int temp = upper_bound(ps, t);
            while (1 <= temp && temp <= ps.length) {
                ans += 1;
                if (temp == ps.length-1) break;
                temp = upper_bound(ps, ps[temp] + t);
                if (temp == ps.length-1) ans--;
            }
            System.out.println(ans);
        }
    }
}
