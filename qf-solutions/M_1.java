import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Problem_M {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        ArrayList<Long> ans = new ArrayList<>();
        for (int t_i = 0; t_i < t; t_i++) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }
            HashMap<Integer, Integer> C = new HashMap<>();
            long c = 0;
            C.put(arr[n - 1], 1);
            for (int j = n - 2; j > 0; j--) {
                for (int i = 0; i < j; i++) {
                    c += C.getOrDefault(2 * arr[j] - arr[i], 0);
                }
                C.put(arr[j], C.getOrDefault(arr[j], 0) + 1);
            }
            ans.add(c);
        }
        for (long c : ans) {
            System.out.println(c);
        }
    }
}
