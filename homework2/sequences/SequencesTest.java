package sequences;

import java.util.Scanner;

/*
В этом методе сделал через сканер ввод чисел. Дополнительно создал класс для теста в пакете test.
Добавил метод для проверки чисел и решил ограничить ввод до 20, чтобы значения не выходили
за рамки вместительности long. Ещё есть мысль создать отдельный класс со статическими методами
для проверки входных данных, чтобы не дублировать метод везде - это нормальная практика?
 */

public class SequencesTest {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите число от 1 до 20:");
            int n = onlyPositiveNumber(scanner);

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

    private static int onlyPositiveNumber(Scanner scanner) {
        boolean flag = true;
        int number = 0;
        while (flag) {
            flag = false;
            while (!scanner.hasNextInt()) {
                System.out.println("Нужно ввести число. Попробуйте снова:");
                scanner.next();
            }
            number = scanner.nextInt();
            if (number <= 0 || number > 20) {
                System.out.println("Число должно быть от 1 до 20. Попробуйте снова:");
                flag = true;
            }
        }
        return number;
    }
}
