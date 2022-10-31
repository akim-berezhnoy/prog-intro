import java.util.Scanner;

public class Problem_B {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = -25_000+n; i < 25000; i++) {
            System.out.println(710*i);
        }
    }
}
