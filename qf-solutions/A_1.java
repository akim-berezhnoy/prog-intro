import java.util.Scanner;

public class Problem_A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int n = sc.nextInt();
        System.out.println(2*Math.ceil((float)(n-b)/(b-a)) + 1);
    }
}
