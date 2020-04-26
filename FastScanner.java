
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Objects;

public class FastScanner {

    private final BufferedInputStream inputStream;
    private final int BUFFER_SIZE = 1<<10;
    private byte[] buffer;
    private int bufpointer, bytesRead;

    public FastScanner(InputStream in) {
        Objects.requireNonNull(in, "Data Input Stream cannot be null");
        inputStream = new BufferedInputStream(in);
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

    public void close() throws IOException{
        if (inputStream == null) return;
        inputStream.close();
    }

    public String nextWord() throws IOException{
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

    public String nextLine() throws IOException {
        /*TODO
        *   Speed Improvement is required.
        *   BufferedReader.readLine() is Faster than this method.
        * */
        int c;
        StringBuilder sb = new StringBuilder();
        while ((c = read()) != -1) {
            if (c == '\n' || c == '\r') break;
            sb.appendCodePoint(c);
        }
        return sb.toString();
    }

    public int nextIntFast() throws IOException{
        int sign = 1;
        byte c = read();
        while (c <= ' ') c = read();
        if (c == '-'){
            c = read();
            sign = -1;
        }

        int value = 0;
        do value = value * 10 + c - '0';
        while ((c=read()) > ' ');

        return value*sign;
    }

    public int nextIntStrict() throws IOException{
        int sign = 1;
        byte c = read();
        while (c <= ' ') c = read();
        if (c != '-' && (c < '0' || c > '9')) throw new InputMismatchException();
        if (c == '-'){
            c = read();
            sign = -1;
        }

        int value = 0;
        do {
            if (c < '0' || c > '9') throw new InputMismatchException();
            value = value * 10 + c - '0';
            if (value < 0) throw new InputMismatchException();
        }while ((c=read()) > ' ');

        return value*sign;
    }

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

    public double nextDoubleStrict() throws IOException{
        int sign = 1;
        byte c = read();
        while (c <= ' ') c = read();
        if (c != '-' && (c < '0' || c > '9')) throw new InputMismatchException();
        if (c == '-'){
            c = read();
            sign = -1;
        }

        double value = 0;
        do{
            if (c < '0' || c > '9') throw new InputMismatchException();
            value = value * 10 + c - '0';
        }
        while ((c=read()) > ' ' && c != '.');

        if (c == '.'){
            double d = 1;
            while ((c=read()) > ' '){
                if (c < '0' || c > '9') throw new InputMismatchException();
                value = value + (c - '0')/(d*=10);
            }
        }

        return value*sign;
    }

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

    public float nextFloatFast() throws IOException{
        return (float) nextDoubleFast();
    }

    public float nextFloatStrict() throws IOException{
        return (float) nextDoubleStrict();
    }



}
