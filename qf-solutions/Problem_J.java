import java.util.Scanner;

public class Problem_J {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int[][] way = new int[n][n];
        for (int i = 0; i < n; i++) {
            String line = sc.next();
            for (int j = 0; j < n; j++) {
                way[i][j] = Integer.parseInt(String.valueOf(line.charAt(j)));
            }
        }

        for (int node_from = 0; node_from < n; node_from++) {
            for (int node_to = node_from+1; node_to < n; node_to++) {
                if (way[node_from][node_to] != 0) {
                    for (int node = node_to+1; node < n; node++) {
                        way[node_from][node] = (10 + (way[node_from][node] - way[node_to][node])) % 10;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i >= j) System.out.print(0); else System.out.print(way[i][j]);
            }
            System.out.println();
        }
    }
}
