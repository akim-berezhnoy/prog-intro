import java.util.Scanner;

public class Problem_I {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long xl = Long.MAX_VALUE, xr = Long.MIN_VALUE, yl = Long.MAX_VALUE, yr = Long.MIN_VALUE;
        for (int i = 0; i < n ; i++) {
            long xi = sc.nextLong();
            long yi = sc.nextLong();
            long hi = sc.nextLong();
            xl = Math.min(xi-hi, xl);
            xr = Math.max(xi+hi, xr);
            yl = Math.min(xi-hi, yl);
            yr = Math.max(yi+hi, yr);
        }
        System.out.println((xl+xr)/2 + " " + (yl+yr)/2 + " " + (long)Math.ceil((double)Math.max(xr-xl, yr-yl)/2));
    }
}
