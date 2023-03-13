package statsaccumulator;

/*
Тест через jUnit здесь не провожу, просто разместил метод main
 */
public class StatsAccumulatorTest {

    public static void main(String[] args) {
        StatsAccumulator accumulator = new StatsAccumulatorImpl();
        accumulator.add(0);
        accumulator.printMaxMinCountAvg();

        accumulator.add(4);
        accumulator.printMaxMinCountAvg();

        accumulator.add(3);
        accumulator.printMaxMinCountAvg();

        accumulator.add(5);
        accumulator.printMaxMinCountAvg();

        accumulator.add(2);
        accumulator.printMaxMinCountAvg();

        accumulator.add(-3);
        accumulator.printMaxMinCountAvg();

        accumulator.add(57);
        accumulator.add(63);
        accumulator.add(43);
        accumulator.printMaxMinCountAvg();
    }
}