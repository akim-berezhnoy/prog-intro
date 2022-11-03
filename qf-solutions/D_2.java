import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Problem_D {

    private static BigInteger toBig(long longNumber) { return BigInteger.valueOf(longNumber); }
    private final static long MOD = 998_244_353L;
    private final static BigInteger BIG_MOD = BigInteger.valueOf(MOD);
    private static long[] r;
    private static long[] d;

    private static long pow(long baseLong, long exponentLong) {
        BigInteger base = BigInteger.valueOf(baseLong);
        BigInteger exponent = BigInteger.valueOf(exponentLong);
        BigInteger result = BigInteger.ONE;
        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            if (exponent.testBit(0)) result = (result.multiply(base)).mod(BIG_MOD);
            exponent = exponent.shiftRight(1);
            base = (base.multiply(base)).mod(BIG_MOD);
        }
        return Long.parseLong(String.valueOf(result.mod(BIG_MOD)));
    }

    private static IntArray divisors(int n) {
        IntArray divs = new IntArray(1);
        for (int d = 1; d <= Math.sqrt(n); d++) {
            if (n % d == 0) {
                divs.add(d);
                if (n/d != d) divs.add(n/d);
            }
        }
        return divs;
    }

    private static long[] R(int n, int k) {
        r = new long[n+1];
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) r[i] = ((i/2) * ((pow(k, i/2) + pow(k, (i+2)/2)) % MOD)) % MOD;
            else r[i] = i*pow(k, (i+1)/2) % MOD;
        }
        return r;
    }

    private static long[] D(int n, int k) {
        d = new long[n+1];
        for (int i = 1; i <= n; i++) {
            d[i] = r[i];
            IntArray divs = divisors(i);
            for (int j = 0; j < divs.length; j++) {
                int div = divs.get(j);
                if (div != i) {
                    d[i] = ((d[i] - (d[div] * (i/div)) % MOD ) % MOD) % MOD;
                }
            }
        }
        return d;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        long[] r = R(n, k);
        long[] d = D(n, k);
        long result = 0;
        for (int i = 1; i <= n; i++) {
            IntArray divs = divisors(n);
            for (int j = 0; j < divs.length; j++) {
                int div = divs.get(j);
                result = (result + d[div]) % MOD;
            }
        }
        System.out.println(result);
    }

    static class IntArray {
        private int[] array;
        private int length;

        public IntArray(int initialCapacity) {
            array = new int[initialCapacity];
            length = 0;
        }

        public int getLength() { return length; }

        public int get(int index) { return array[index]; }

        public void add(int value) {
            array[length++] = value;
            if (length == array.length) { array = Arrays.copyOf(array, array.length*2+1); }
        }

    }
}
