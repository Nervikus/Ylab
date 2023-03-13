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
}
