/*
Я решил эту задачу двумя способами, как обычно бывает изображена таблица на тетрадках :)
 */

public class MultTable {
    public static void main(String[] args) throws Exception {
        ylabVersion();
        System.out.println();
        myVersion();
    }

    private static void ylabVersion() {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                int mult = i * j;
                System.out.printf("%d * %d = %d",j, i, mult);
                //для красоты вывода :)
                if (mult < 10) {
                    System.out.print("\t");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    private static void myVersion() {
        System.out.println("   | 2  3  4  5  6  7  8  9");
        System.out.println("- - - - - - - - - - - - - -");
        for (int i = 2; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                int tab = i * j;
                if (tab < 10)
                    System.out.print(tab + "  ");
                else
                    System.out.print(tab + " ");
                if (j == 1)
                    System.out.print("| ");
            }
            System.out.println();
        }
    }
}
