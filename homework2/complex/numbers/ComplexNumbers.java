package complex.numbers;

/*
Если что, в чате разрешали решить эт задачу без интерфейсов)
 */

public class ComplexNumbers {
    public static void main(String[] args) throws Exception {
        ComplexNumbers num1 = new ComplexNumbers(1, -1);
        ComplexNumbers num2 = new ComplexNumbers(3, 6);

        ComplexNumbers num3 = num1.add(num2);
        System.out.println(num3);

        ComplexNumbers num4 = num1.subtract(num2);
        System.out.println(num4);

        ComplexNumbers num5 = num1.multiply(num2);
        System.out.println(num5);

        double modulus = num1.getModulus();
        System.out.println(modulus);

        String str = num1.toString();
        System.out.println(str);

        //демонстрирация вывода конструктора с 1-им параметром
        ComplexNumbers num6 = new ComplexNumbers(5);
        String string = num6.toString();
        System.out.println(string);
    }

    private double realPart;
    private double imaginaryPart;

    public ComplexNumbers(double realPart) {
        this.realPart = realPart;
    }

    public ComplexNumbers(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public ComplexNumbers add(ComplexNumbers other) {
        double newReal = this.realPart + other.realPart;
        double newImaginary = this.imaginaryPart + other.imaginaryPart;
        return new ComplexNumbers(newReal, newImaginary);
    }

    public ComplexNumbers subtract(ComplexNumbers other) {
        double newReal = this.realPart - other.realPart;
        double newImaginary = this.imaginaryPart - other.imaginaryPart;
        return new ComplexNumbers(newReal, newImaginary);
    }

    public ComplexNumbers multiply(ComplexNumbers other) {
        double newReal = this.realPart * other.realPart - this.imaginaryPart * other.imaginaryPart;
        double newImaginary = this.realPart * other.imaginaryPart + this.imaginaryPart * other.realPart;
        return new ComplexNumbers(newReal, newImaginary);
    }

    public double getModulus() {
        return Math.sqrt(Math.pow(this.realPart, 2) + Math.pow(this.imaginaryPart, 2));
    }

    @Override
    public String toString() {
        if (this.imaginaryPart == 0) {
            return this.realPart + "";
        }
        return this.realPart + " + " + this.imaginaryPart + "i";
    }
}

