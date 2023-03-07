import java.util.Scanner;

public class Pell {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            long pell = formula(scanner);
            System.out.println(pell);
        }
    }

    private static long formula(Scanner scanner) {
        int n = 0;
        boolean flag = true;
        System.out.println("Введите число от 0 до 30:");

        //проверка на корректный ввод числа
        while (flag) {
            flag = false;
            while (!scanner.hasNextInt()) {
                System.out.println("Введите ЧИСЛО от 0 до 30:");
                scanner.next();
            }
            n = scanner.nextInt();
            if (n < 0 || n > 30) {
                System.out.println("Попробуйте снова:");
                flag = true;
            }
        }

        if (n == 0) return 0;
        if (n == 1) return 1;

        long a = 0;
        long b = 1;

        for (int i = 1; i < n; i++) {
            long temp = a;
            a = b;
            b = 2 * a + temp;
        }
        return b;
    }
}
