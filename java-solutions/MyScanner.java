import java.io.*;
import java.nio.charset.Charset;

public class MyScanner implements AutoCloseable {

    private enum Criteria {
        WORD,
        INTEGER
    }

    private final Reader reader;
    private final StringBuilder token;
    private final char[] buffer;
    private int capacity;
    private int iterator;

    public MyScanner(InputStream source, Charset charset) throws IOException {
        this.reader = new InputStreamReader(source, charset);
        this.token = new StringBuilder();
        this.buffer = new char[128];
        this.capacity = this.reader.read(this.buffer);
        this.iterator = 0;
    }
    public MyScanner(String source, Charset charset) throws IOException {
        this(new ByteArrayInputStream(source.getBytes(charset)), charset);
    }
    public MyScanner(File source, Charset charset) throws SecurityException, IOException {
        this(new FileInputStream(source), charset);
    }

    public boolean hasThisLine() throws IOException {
        return this.capacity != -1;
    }
    public boolean hasNextInCurrentLine(Criteria criteria) throws IOException {
        if (this.capacity == -1) return false;
        while (thisCharIsInvalid(criteria)) {
            if (this.buffer[this.iterator] == '\n') {
                this.iterator++;
                if (this.iterator == this.capacity) createNewBuffer();
                return false;
            }
            this.iterator++;
            if (this.iterator == this.capacity) createNewBuffer();
            if (this.capacity == -1) return false;
        }
        return true;
    }
    public boolean hasNextIntInCurrentLine() throws IOException {
        return hasNextInCurrentLine(Criteria.INTEGER);
    }
    public boolean hasNextWordInCurrentLine() throws IOException {
        return hasNextInCurrentLine(Criteria.WORD);
    }

    public String readLine() throws IOException {
        this.token.setLength(0);
        while (true) {
            if (this.buffer[this.iterator] != '\n') {
                this.token.append(this.buffer[this.iterator]);
                this.iterator++;
                if (this.iterator == this.capacity) createNewBuffer();
                if (this.capacity == -1) break;
            } else {
                this.iterator++;
                if (this.iterator == this.capacity) createNewBuffer();
                break;
            }
        }
        return this.token.toString();
    }
    public String readWord() throws IOException {
        return read(Criteria.WORD);
    }
    public int readInt() throws IOException {
        return Integer.parseInt(read(Criteria.INTEGER));
    }

    private int createNewBuffer() throws IOException {
        this.capacity = this.reader.read(this.buffer);
        this.iterator = 0;
        return this.capacity;
    }
    private String read(Criteria criteria) throws IOException {
        this.token.setLength(0);
        while (!thisCharIsInvalid(criteria)) {
            this.token.append(this.buffer[this.iterator]);
            this.iterator++;
            if (this.iterator == this.capacity) {
                if (createNewBuffer() == -1) return this.token.toString();
            }
        }
        return this.token.toString();
    }
    private boolean thisCharIsInvalid(Criteria criteria) {
        if (criteria == Criteria.INTEGER) {
            return (!Character.isDigit(this.buffer[this.iterator]) && (this.buffer[this.iterator] != '-'));
        } else if (criteria == Criteria.WORD) {
            return (!Character.isLetter(this.buffer[this.iterator]) && this.buffer[this.iterator] != '\'' &&
                    (Character.getType(this.buffer[this.iterator]) != Character.DASH_PUNCTUATION));
        }
        return false;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}