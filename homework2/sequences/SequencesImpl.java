package sequences;

/*
Сделал вывод через StringBuilder. Красивее и проверять вам удобнее будет)
 */

public class SequencesImpl implements Sequences {
    @Override
    public void a(int n) {
        StringBuilder builder = new StringBuilder("A. ");
        for (int i = 1; i <= n; i++) {
            builder.append(i * 2);
            if (i != n) {
                builder.append(", ");
            } else {
                builder.append(".");
            }
        }
        System.out.println(builder);
    }

    @Override
    public void b(int n) {
        StringBuilder builder = new StringBuilder("B. ");
        for (int i = 1; i <= n; i++) {
            builder.append(2 * i - 1);
            if (i != n) {
                builder.append(", ");
            } else {
                builder.append(".");
            }
        }
        System.out.println(builder);
    }

    @Override
    public void c(int n) {
        StringBuilder builder = new StringBuilder("C. ");
        for (int i = 1; i <= n; i++) {
            builder.append(i * i);
            if (i != n) {
                builder.append(", ");
            } else {
                builder.append(".");
            }
        }
        System.out.println(builder);
    }

    @Override
    public void d(int n) {
        StringBuilder builder = new StringBuilder("D. ");
        for (int i = 1; i <= n; i++) {
            builder.append(i * i * i);
            if (i != n) {
                builder.append(", ");
            } else {
                builder.append(".");
            }
        }
        System.out.println(builder);
    }

    @Override
    public void e(int n) {
        StringBuilder builder = new StringBuilder("E. ");
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                builder.append(1);
            } else {
                builder.append(-1);
            }

            if (i != n) {
                builder.append(", ");
            } else {
                builder.append(".");
            }
        }
        System.out.println(builder);
    }

    @Override
    public void f(int n) {
        StringBuilder builder = new StringBuilder("F. ");
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                builder.append(i);
            } else {
                builder.append(-i);
            }

            if (i != n) {
                builder.append(", ");
            } else {
                builder.append(".");
            }
        }
        System.out.println(builder);
    }

    @Override
    public void g(int n) {
        StringBuilder builder = new StringBuilder("G. ");
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                builder.append(i * i);
            } else {
                builder.append(-i * i);
            }

            if (i != n) {
                builder.append(", ");
            } else {
                builder.append(".");
            }
        }
        System.out.println(builder);
    }

    @Override
    public void h(int n) {
        StringBuilder builder = new StringBuilder("H. ");
        int result = 1;
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                builder.append(result);
            } else {
                result++;
                builder.append(0);
            }

            if (i != n) {
                builder.append(", ");
            } else {
                builder.append(".");
            }
        }
        System.out.println(builder);
    }

    @Override
    public void i(int n) {
        StringBuilder builder = new StringBuilder("I. ");
        //в этом методе поменял int на long, чтобы больше значений вместить
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
            builder.append(result);

            if (i != n) {
                builder.append(", ");
            } else {
                builder.append(".");
            }
        }
        System.out.println(builder);
    }

    @Override
    public void j(int n) {
        StringBuilder builder = new StringBuilder("J. ");
        int result = 1;
        int temp = 1;
        for (int i = 1; i <= n; i++) {
            builder.append(result);
            int sum = result + temp;
            result = temp;
            temp = sum;

            if (i != n) {
                builder.append(", ");
            } else {
                builder.append(".");
            }
        }
        System.out.println(builder);
    }
}
