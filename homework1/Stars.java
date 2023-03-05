import java.util.Scanner;

public class Stars {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите количество строк:");
            int n = onlyPositiveNumber(scanner);
            System.out.println("Введите количество столбцов:");
            int m = onlyPositiveNumber(scanner);
            System.out.println("Введите любой символ:");
            String template = scanner.next();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    System.out.print(template);
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
    }

    /*
    я решил создать отдельный метод, где мы проверяем,
    что вводим только позитивные числа и ничего кроме чисел
    */
    private static int onlyPositiveNumber(Scanner scanner) {
        boolean flag = true;
        int number = 0;
        while (flag) {
            flag = false;
            while (!scanner.hasNextInt()) {
                System.out.println("Введите число:");
                scanner.next();
            }
            number = scanner.nextInt();
            if (number <= 0) {
                System.out.println("Введите положительное число > 0:");
                flag = true;
            }
        }
        return number;
    }
}
