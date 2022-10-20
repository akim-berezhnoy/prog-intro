import java.util.Arrays;

class DynamicIntArray {
    private int[] array;
    private int length;

    public DynamicIntArray(int initialCapacity) {
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