import java.util.Scanner;

public class Problem_H {

    static int upper_bound(int[] array, int elem) {
        int l = 0, r = array.length, mid = 0;
        while (r - l > 1) {
            mid = (l + r) / 2;
            if (elem < array[mid]) {
                r = mid;
            } else if (elem > array[mid]) {
                l = mid;
            } else if (elem == array[mid]){
                return mid;
            }
        }
        if (elem > array[array.length-1]) return -1;
        if (array[l] <= elem) return l; else return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] ps = new int[n];
        int s = 0;
        for (int i = 0; i < n; i++) ps[i] = (s += sc.nextInt());
        int q = sc.nextInt();
        int[] T = new int[q];
        for (int i = 0; i < q; i++) T[i] = sc.nextInt();
        for (int t : T) {
            int ans = 1;
            int temp = upper_bound(ps, t);
            while (temp != -1) {
                ans++;
                if (upper_bound(ps, ps[temp] + t) == ps.length-1) break;
                temp = upper_bound(ps, ps[temp] + t);
            }
            if (ans == 0) System.out.println("Impossible"); else System.out.println(ans);
        }
    }
}
