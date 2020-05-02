
import com.sun.istack.internal.NotNull;
import java.io.*;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Objects;

/**
 * A fast input reader to read primitive data types from any InputStream.
 * It provides similar functionality to that of Scanner class but is much faster than
 * Scanner or BufferedReader Class.<br>
 * The input stream is buffered using
 * {@linkplain BufferedInputStream BufferedInputStream} to provide faster reading.
 *
 * @author Kaustubh Dandekar
 **/
public class FastScanner {

    private final BufferedInputStream inputStream;
    private final int BUFFER_SIZE = 1<<10;
    private byte[] buffer;
    private int bufpointer, bytesRead;

    /* for caching the bytes representing a line in hasNextLine method */
//    private String lineCache;

    /**
     * Constructs a new <code>FastScanner</code> object that reads from
     * the provided input stream.
     *
     * @param in Input Stream to read from
     */
    public FastScanner(@NotNull InputStream in) {
        Objects.requireNonNull(in, "Data Input Stream cannot be null");
        inputStream = new BufferedInputStream(in);
        buffer = new byte[BUFFER_SIZE];
        bufpointer = bytesRead = 0;
    }

    /**
     * Constructs a new <code>FastScanner</code> object that reads from
     * the provided file <code>Path</code>. The Contents of file will be read as InputStream.
     *
     * @param path Path object of file to read from
     */
    public FastScanner(@NotNull Path path) throws FileNotFoundException {
        Objects.requireNonNull(path, "Data Input Stream cannot be null");
        inputStream = new BufferedInputStream(new FileInputStream(path.getFileName().toString()));
        buffer = new byte[BUFFER_SIZE];
        bufpointer = bytesRead = 0;
    }

    /**
     * Constructs a new <code>FastScanner</code> object that reads from
     * the provided String. The provided string will be used as an InputStream.
     *
     * @param string String to read from
     */
    public FastScanner(@NotNull String string) {
        Objects.requireNonNull(string, "Data Input Stream cannot be null");
        inputStream = new BufferedInputStream(new ByteArrayInputStream(string.getBytes()));
        buffer = new byte[BUFFER_SIZE];
        bufpointer = bytesRead = 0;
    }

    /**
     * Constructs a new <code>FastScanner</code> object that reads from
     * the provided <code>File</code> object.
     * The Contents of file will be read as InputStream.
     *
     * @param file File to read from
     */
    public FastScanner(@NotNull File file) throws FileNotFoundException {
        Objects.requireNonNull(file, "Data Input Stream cannot be null");
        inputStream = new BufferedInputStream(new FileInputStream(file));
        buffer = new byte[BUFFER_SIZE];
        bufpointer = bytesRead = 0;
    }

    private void fillBuffer() throws IOException {
        bytesRead = inputStream.read(buffer, bufpointer = 0, BUFFER_SIZE);
        if (bytesRead == -1) buffer[0] = -1;
    }

    private byte read() throws IOException{
        if (bufpointer == bytesRead) fillBuffer();
        return buffer[bufpointer++];
    }

//    Skip all the non-numeric bytes since we are expecting only valid data types in input
    private int[] skipNonNumerics() throws IOException{
        int sign = 1;
        byte c = read();
        while (c <= ' ') c = read();
        if (c == '-'){
            c = read();
            sign = -1;
        }
        return new int[]{c, sign};
    }

//    Skip Only the non-character bytes as we want to validate the data types in input
    private int[] skipNonCharacters() throws IOException{
        int sign = 1;
        byte c = read();
        while (c <= ' ') c = read();
        if (c != '-' && (c < '0' || c > '9')) throw new InputMismatchException();
        if (c == '-'){
            c = read();
            sign = -1;
        }
        return new int[]{c, sign};
    }

    /**
     *  Closes the <code>FastScanner</code> object.
     *
     * @throws IOException if the object is already closed or i/o error occurs
     * */
    public void close() throws IOException{
        if (inputStream == null) return;
        inputStream.close();
    }

    /**
     *  Checks if the input stream contains a string token
     *
     * @return <code>true</code> if the stream contains a token
     * @throws IOException if object is closed or i/o error occurs
     * */
    public boolean hasNext() throws IOException{
        if (bytesRead == -1) return false;
        int c;
        while(true){
            if ((c=read()) == -1) return false;
            if (c > ' '){
                bufpointer--;
                return true;
            }
        }
    }

    /**
     *  Returns the next word in the input stream
     *
     *  @return the next word in the input stream
     *  @throws IOException
     *          if the object is closed or i/o error occurs
     * */
    public String next() throws IOException{
        int c = read();
        while(c < ' ') c = read();

        StringBuilder sb = new StringBuilder();
        while (c != -1) {
            if (c <= ' ') break;
            sb.appendCodePoint(c);
            c = read();
        }
        return sb.toString();
    }

    /**
     *  Returns the next line in the input stream
     *
     *  @return the next line in the input stream
     *  @throws IOException
     *          if the object is closed or i/o error occurs
     * */
    public String nextLine() throws IOException {
        int c;
        StringBuilder sb = new StringBuilder();
        while ((c = read()) != -1) {
            if (c == '\n' || c == '\r') break;
            sb.appendCodePoint(c);
        }
        return sb.toString();
    }

    /**
     *  This Method expects the next input to be Integer only
     *  and does not validate for wrong input characters.
     *
     *  @return the integer value read from input
     *  @throws IOException
     *          if object is closed or i/o error occurs
     * */
    public int nextIntFast() throws IOException{
        int[] tmp = skipNonNumerics();
        int c = tmp[0];

        int value = 0;
        do value = value * 10 + c - '0';
        while ((c=read()) > ' ');

        return value*tmp[1];
    }

    /**
     *  This Method throws InputMismatchException if it
     *  encounters a character other than a integer value.
     *
     *  @return the integer value read from input
     *  @throws InputMismatchException
     *          if the next token does not match the Integer
     *          regular expression, or is out of range
     *  @throws IOException
     *          if object is closed or i/o error occurs
     * */
    public int nextIntStrict() throws IOException{
        int[] tmp = skipNonCharacters();
        int c = tmp[0];

        int value = 0;
        do {
            if (c < '0' || c > '9') throw new InputMismatchException();
            value = value * 10 + c - '0';
            if (value < 0) throw new InputMismatchException();
        }while ((c=read()) > ' ');

        return value*tmp[1];
    }

    /**
     *  This Method expects the next input to be integer
     *  or decimal only and does not validate for wrong input
     *  characters.
     *
     *  @return the numeric value scanned from the input as a <tt>double</tt>
     *  @throws IOException
     *          if object is closed or i/o error occurs
     * */
    public double nextDoubleFast() throws IOException{
        int sign = 1;
        byte c = read();
        while (c <= ' ') c = read();
        if (c == '-'){
            c = read();
            sign = -1;
        }

        double value = 0;
        do value = value * 10 + c - '0';
        while ((c=read()) > ' ' && c != '.');

        if (c == '.'){
            double d = 1;
            while ((c=read()) > ' ') value = value + (c - '0')/(d*=10);
        }

        return value*sign;
    }

    /**
     *  This Method throws InputMismatchException if it
     *  encounters a character other than a integer or
     *  decimal value.
     *
     *  @return the numeric value read from input as a <tt>double</tt>
     *  @throws InputMismatchException
     *          if the next token does not match the
     *          decimal number format
     *  @throws IOException
     *          if object is closed or i/o error occurs
     * */
    public double nextDoubleStrict() throws IOException{
        int sign = 1;
        byte c = read();
        while (c <= ' ') c = read();
        if (c != '-' && (c < '0' || c > '9')) throw new InputMismatchException();
        if (c == '-'){
            c = read();
            sign = -1;
        }

        long value = 0, precision = 0;
        do{
            if (precision != 0){ if (c == '.') throw new InputMismatchException();
                precision++;
            }
            if (c == '.'){ precision++; continue; }
            if (c < '0' || c > '9') throw new InputMismatchException();
            value = value * 10 + c - '0';
        }
        while ((c=read()) > ' ');

//        if (c == '.'){
//            double d = 1;
//            while ((c=read()) > ' '){
//                if (c < '0' || c > '9') throw new InputMismatchException();
//                value = value + (c - '0')/(d*=10);
//            }
//        }

        return value/Math.pow(10, precision)*sign;
    }

    /**
     *  <p>This method can be used to find the numeric
     *  values inside the input stream, for example parsing a
     *  numeric list in a string or input stream sent over a network.</p>
     *  <br>
     *  <p> To facilitate faster reading, it assumes that if '-' precedes
     *  a number then the number is negative. In some cases it will
     *  provide negative value. For example in the following mapping
     *  a-2 b-4 c-6 expected will be 2 but it will return -2. To
     *  avoid this separate the '-' character and number with a space.</p>
     *
     *  @return the next numeric value in the string
     *  @throws TypeNotPresentException
     *          if numeric value is not found in the input stream.
     *  @throws IOException
     *          if the object is closed or i/o error occurs

     * */
    public double nextNumberInText() throws IOException, TypeNotPresentException{
        int sign = 1;
        byte c = read();
        while (c < '0' || c > '9'){
            if (c == -1) throw new TypeNotPresentException("Number", new Throwable("End of Input Stream"));
            if(c == '-' && (c=read()) >= '0' && c <= '9'){
                sign = -1;
                break;
            }
            c = read();
        }

        double value = 0;
        do value = value * 10 + c - '0';
        while ((c=read()) >= '0' && c <= '9');

        if (c == '.'){
            double d = 1;
            while ((c=read()) >= '0' && c <= '9')
                value = value + (c - '0')/(d*=10);
        }

        return value*sign;
    }

    /**
     *  This works similarly to the <tt>nextDoubleFast()</tt> method
     *
     *  @return next float value read from input
     *  @throws IOException
     *          if the object is closed or i/o error occurs
     * */
    public float nextFloatFast() throws IOException{
        return (float) nextDoubleFast();
    }

    /**
     *  This method works similarly to the <tt>nextDoubleStrict()</tt> method
     *
     *  @return next float value read from input
     *  @throws InputMismatchException
     *          if the next token does not match the decimal number format
     *  @throws IOException
     *          if object is closed or i/o error occurs
     * */
    public float nextFloatStrict() throws IOException{
        return (float) nextDoubleStrict();
    }

    public long nextLongFast() throws IOException{
        int[] tmp = skipNonNumerics();
        int c = tmp[0];

        long value = 0;
        do value = value * 10 + c - '0';
        while ((c=read()) > ' ');

        return value*tmp[1];
    }

    public long nextLongStrict() throws IOException{
        int[] tmp = skipNonCharacters();
        int c = tmp[0];

        long value = 0;
        do {
            if (c < '0' || c > '9') throw new InputMismatchException();
            value = value * 10 + c - '0';
            if (value < 0) throw new InputMismatchException();
        }while ((c=read()) > ' ');

        return value*tmp[1];
    }

    public long nextShortFast() throws IOException{
        return (short) nextIntFast();
    }

    public short nextShortStrict() throws IOException{
        return (short) nextIntStrict();
    }
}
