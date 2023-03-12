package ratelimitedprinter;

/*
Мне кажется, эту задачу можно было сделать в одном классе, без интерфейса,
поправьте меня, если я ошибаюсь.
 */
public class RateLimitedPrinterImpl implements RateLimitedPrinter{
    private long lastTime;
    private int interval;

    public RateLimitedPrinterImpl(int interval) {
        this.interval = interval;
        lastTime = 0;
    }

    @Override
    public void print(String message) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastTime) > interval ) {
            System.out.println(message);
            lastTime = currentTime;
        }
    }

    public static void main(String[] args) throws Exception {
        RateLimitedPrinter rateLimitedPrinter = new RateLimitedPrinterImpl(1000);
        for (int i = 0; i < 1000000000; i++) {
            rateLimitedPrinter.print(String.valueOf(i));
        }
    }
}
