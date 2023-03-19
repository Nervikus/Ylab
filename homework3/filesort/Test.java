package filesort;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        System.out.println(new Date());
        File dataFile = new Generator().generate("data.txt", 1_000_000);
        System.out.println(new Validator(dataFile).isSorted()); // false
        System.out.println();

        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); // true

        long end = System.currentTimeMillis();
        System.out.println(new Date());
        System.out.println(end - start);
    }
}