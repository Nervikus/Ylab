package sequences;

//Дополнительно создал класс для теста в пакете test.
public class SequencesTest {
    public static void main(String[] args) {
        //сначала использовал scanner, но потом убрал, т.к. его не было в ТЗ
        int n = 10;

        Sequences sequences = new SequencesImpl();
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
    }
}
