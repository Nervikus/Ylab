import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) throws Exception {
        int number = new Random().nextInt(99) + 1;
        int maxAttempts = 10;
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                while (!scanner.hasNextInt()) {
                    maxAttempts--;
                    System.out.printf("Это не число! Осталось %d попыток%n", maxAttempts);
                    scanner.next();
                }
                int value = scanner.nextInt();
                if (value > number) {
                    maxAttempts--;
                    System.out.printf("Мое число меньше! Осталось %d попыток%n", maxAttempts);
                } else if (value < number) {
                    maxAttempts--;
                    System.out.printf("Мое число больше! Осталось %d попыток%n", maxAttempts);
                } else {
                    System.out.printf("Ты угадал с %d попытки!%n", 11 - maxAttempts);
                    break;
                }
                if (maxAttempts < 1) {
                    System.out.println("Ты не угадал");
                    break;
                }
            }
        }
    }
}
