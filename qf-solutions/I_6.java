import java.util.Scanner;

public class Problem_I {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int xl = Integer.MAX_VALUE, xr = Integer.MIN_VALUE, yl = Integer.MAX_VALUE, yr = Integer.MIN_VALUE;
        for (int i = 0; i < n ; i++) {
            int xi = sc.nextInt();
            int yi = sc.nextInt();
            int hi = sc.nextInt();
            xl = Math.min(xi-hi, xl);
            xr = Math.max(xi+hi, xr);
            yl = Math.min(yi-hi, yl);
            yr = Math.max(yi+hi, yr);
        }
        System.out.println((xl+xr)/2 + " " + (yl+yr)/2 + " " + (int)Math.ceil((double)Math.max(xr-xl, yr-yl)/2));
    }
}
