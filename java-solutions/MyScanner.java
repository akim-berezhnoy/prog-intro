import java.io.*;
import java.nio.charset.Charset;

public class MyScanner implements AutoCloseable {
    private enum Criteria { WORD, INTEGER }
    private final Reader reader;
    private final char[] buffer;
    private int capacity;
    private int iterator;

    public MyScanner(InputStream source, Charset charset) throws IOException {
        reader = new InputStreamReader(source, charset);
        buffer = new char[64];
        capacity = reader.read(buffer);
        iterator = 0;
    }
    public MyScanner(File source, Charset charset) throws SecurityException, IOException {
        this(new FileInputStream(source), charset);
    }

    public boolean hasThisLine() throws IOException { return capacity != -1; }
    public boolean hasNextInCurrentLine(Criteria criteria) throws IOException {
        if (capacity == -1) return false;
        while (thisCharIsInvalid(criteria)) {
            if (buffer[iterator] == '\n') {
                iterator++;
                if (iterator == capacity) readNewBuffer();
                return false;
            }
            iterator++;
            if (iterator == capacity) readNewBuffer();
            if (capacity == -1) return false;
        }
        return true;
    }
    public boolean hasNextIntInCurrentLine() throws IOException { return hasNextInCurrentLine(Criteria.INTEGER); }
    public boolean hasNextWordInCurrentLine() throws IOException { return hasNextInCurrentLine(Criteria.WORD); }

    public int readInt() throws IOException { return Integer.parseInt(read(Criteria.INTEGER)); }
    public String readWord() throws IOException { return read(Criteria.WORD); }

    private int readNewBuffer() throws IOException {
        capacity = reader.read(buffer);
        iterator = 0;
        return capacity;
    }
    private String read(Criteria criteria) throws IOException {
        StringBuilder token = new StringBuilder();
        while (!thisCharIsInvalid(criteria)) {
            token.append(buffer[iterator++]);
            if ((iterator == capacity) && (readNewBuffer() == -1)) { return token.toString(); }
        }
        return token.toString();
    }
    private boolean thisCharIsInvalid(Criteria criteria) {
        if (criteria == Criteria.INTEGER) {
            return (!Character.isDigit(buffer[iterator]) && (buffer[iterator] != '-'));
        } else if (criteria == Criteria.WORD) {
            return (!Character.isLetter(buffer[iterator]) && buffer[iterator] != '\'' &&
                    (Character.getType(buffer[iterator]) != Character.DASH_PUNCTUATION));
        }
        return false;
    }

    @Override
    public void close() throws IOException { reader.close(); }
}