package statsaccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int result = 0;
    private int sumResults = 0;
    private int minResult = Integer.MAX_VALUE;
    private int maxResult = Integer.MIN_VALUE;
    private int count = 0;
    private double avg = 0.0;

    @Override
    public void add(int value) {
        result = value;
        sumResults += value;
        count++;
        maxResult = getMax();
        minResult = getMin();
        if (count != 0) {
            avg = (double) sumResults / count;
        }
    }

    @Override
    public int getMin() {
        if (result < minResult) {
            minResult = result;
        }
        return minResult;
    }

    @Override
    public int getMax() {
        if (result > maxResult) {
            maxResult = result;
        }
        return maxResult;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        return avg;
    }

    public void printMaxMinCountAvg() {
        System.out.printf(
                """
                        MAX = %d
                        MIN = %d
                        count = %d
                        avg = %.1f
                        
                        """,
                getMax(), getMin(), getCount(), getAvg());
    }
}
