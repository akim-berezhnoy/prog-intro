import java.util.HashMap;
import java.util.Scanner;

public class Problem_H {

    static protected final HashMap<Integer, Integer> memory = new HashMap<>();

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
        for (int i = 0; i < q; i++) {
            int t = sc.nextInt();
            int ans = 0;
            if (!memory.containsKey(t)) {
                if (t < max) {
                    memory.put(t, 0);
                } else {
                    int temp = 0;
                    while (temp != -1) {
                        ans++;
                        int bound_index = upper_bound(ps, ps[temp] + t);
                        if (bound_index == ps.length - 1) break;
                        temp = bound_index;
                    }
                    memory.put(t,ans);
                }
            }
            ans = memory.get(t);
            if (ans == 0) System.out.println("Impossible");
            else System.out.println(ans);
        }
    }
}