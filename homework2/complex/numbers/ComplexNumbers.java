package complex.numbers;

/*
Если что, в чате разрешали решить эт задачу без интерфейсов)
 */

public class ComplexNumbers {
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

