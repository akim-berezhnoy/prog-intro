import java.io.*;
import java.nio.charset.Charset;

public class MyScanner {

    private enum Criteria {
        LETTER,
        INTEGER,
        LINE
    }

    private final Reader reader;
    private final StringBuilder token;
    private final char[] buffer;
    private int capacity;
    private int iterator;

    public MyScanner(InputStream source, Charset charset) throws IOException {
        this.reader = new InputStreamReader(source, charset);
        this.token = new StringBuilder();
        this.buffer = new char[64];
        this.capacity = this.reader.read(this.buffer);
        this.iterator = 0;
    }
    public MyScanner(String source, Charset charset) throws IOException {
        this(new ByteArrayInputStream(source.getBytes(charset)), charset);
    }
    public MyScanner(File source, Charset charset) throws SecurityException, IOException {
        this(new FileInputStream(source), charset);
    }

    public String nextLine() throws IOException {
        return next(Criteria.LINE);
    }
    public String nextInt() throws IOException {
        return next(Criteria.INTEGER);
    }
    public String nextWord() throws IOException {
        return next(Criteria.LETTER);
    }

    private String next(Criteria criteria) throws IOException {
        this.token.setLength(0);
        while (true) {
            if (this.iterator == this.capacity) {
                this.capacity = this.reader.read(this.buffer);
                this.iterator = 0;
                if (this.capacity == -1) return this.token.toString();
            }
            if (this.capacity == -1) return null;
            if (thisCharIsInvalid(criteria)) {
                // Заметим, что при выводе следующей строки, в случае встречи с двумя подряд идущими сепараторами,
                // необходимо вывести пустую строчку, а в случае с числом, словом и т.д. мы ожидаем, что программа
                // пойдет дальше, пока не найдем подходящюю последовательность символов или не встретим конец потока.
                if (criteria != Criteria.LINE) {
                    while (thisCharIsInvalid(criteria) && (this.capacity != -1)) {
                        this.iterator++;
                        if (this.iterator == this.capacity) {
                            this.capacity = this.reader.read(this.buffer);
                            this.iterator = 0;
                        }
                    }
                } else {
                    this.iterator++;
                }
                if (criteria == Criteria.LINE) return this.token.toString();
                if (!this.token.isEmpty()) return this.token.toString();
            }
            this.token.append(this.buffer[this.iterator]);
            iterator++;
        }
    }

    private boolean thisCharIsInvalid(Criteria criteria) {
        if (criteria == Criteria.INTEGER) {
            return (!Character.isDigit(this.buffer[this.iterator]) && (this.buffer[this.iterator] != '-'));
        } else if (criteria == Criteria.LETTER) {
            return (!Character.isLetter(this.buffer[this.iterator]) && this.buffer[this.iterator] != '\'' &&
                    (Character.getType(this.buffer[this.iterator]) != Character.DASH_PUNCTUATION));
        } else if (criteria == Criteria.LINE) {
            return (this.buffer[this.iterator] == '\n');
        }
        return false;
    }

    public void close() throws IOException {
        this.reader.close();
    }
}