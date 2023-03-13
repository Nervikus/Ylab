package ratelimitedprinter;

/*
Тест через jUnit здесь не провожу, просто разместил метод main в папку с тестами
 */

public class RateLimitedPrinterTest {
    public static void main(String[] args) {
        System.out.println("Тест №1. Интервал = 1000 мс, счётчик = 1_000_000_000:");
        RateLimitedPrinter rateLimitedPrinter1 = new RateLimitedPrinterImpl(1000);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter1.print(String.valueOf(i));
        }
        System.out.println("Тест №2. Интервал = 2000 мс, счётчик = 1_000_000_000:");
        RateLimitedPrinter rateLimitedPrinter2 = new RateLimitedPrinterImpl(2000);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter2.print(String.valueOf(i));
        }
        System.out.println("Тест №3. Интервал = 1000 мс, счётчик = 100_000_000:");
        RateLimitedPrinter rateLimitedPrinter3 = new RateLimitedPrinterImpl(1000);
        for (int i = 0; i < 100_000_000; i++) {
            rateLimitedPrinter3.print(String.valueOf(i));
        }
        System.out.println("Тест №4. Интервал = 1000 мс, счётчик = 10_000_000:");
        RateLimitedPrinter rateLimitedPrinter4 = new RateLimitedPrinterImpl(1000);
        for (int i = 0; i < 10_000_000; i++) {
            rateLimitedPrinter4.print(String.valueOf(i));
        }
    }

}