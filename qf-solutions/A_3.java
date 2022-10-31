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
        int[] ps = new int[n];
        ps[0] = sc.nextInt();
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < n; i++) {
            int temp = sc.nextInt();
            ps[i] = ps[i - 1] + temp;
            max = Math.max(temp, max);
        }
        int q = sc.nextInt();
        int[] t_arr = new int[q];
        for (int i = 0; i < q; i++) {
            t_arr[i] = sc.nextInt();
        }
        System.out.println();
        for (int t : t_arr) {
            if (t < max) {
                System.out.println("Impossible");
                continue;
            }
            int ans = 1;
            int temp = upper_bound(ps, t);
            while (0 <= temp && temp < ps.length-1) {
                ans += 1;
                temp = upper_bound(ps, ps[temp] + t);
            }
            System.out.println(ans);
        }
    }
}
