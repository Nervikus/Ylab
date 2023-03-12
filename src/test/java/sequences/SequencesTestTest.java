package sequences;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class SequencesTestTest {

    /*
    Прошу сильно не ругать - это мой первый тест)
    Но замечания по коду если есть, хочу узнать, чтобы в дальнейшем исправить
     */

    @Test
    public void main() {
        String actual = null;
        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(100);
            PrintStream capture = new PrintStream(outputStream);
            System.setOut(capture);
            Sequences sequences = new SequencesImpl();
            int n = 5;

            sequences.a(n);
            sequences.b(n);
            sequences.c(n);
            sequences.d(n);
            sequences.e(n);
            sequences.f(n);
            sequences.g(n);
            sequences.h(n);
            sequences.i(n);
            sequences.j(n);
            capture.flush();
            actual = outputStream.toString();

            System.setOut(originalOut);
            outputStream.close();
            capture.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String expected = """
                A. 2, 4, 6, 8, 10.\r
                B. 1, 3, 5, 7, 9.\r
                C. 1, 4, 9, 16, 25.\r
                D. 1, 8, 27, 64, 125.\r
                E. 1, -1, 1, -1, 1.\r
                F. 1, -2, 3, -4, 5.\r
                G. 1, -4, 9, -16, 25.\r
                H. 1, 0, 2, 0, 3.\r
                I. 1, 2, 6, 24, 120.\r
                J. 1, 1, 2, 3, 5.\r
                """;
        assertEquals(expected, actual);
    }
}