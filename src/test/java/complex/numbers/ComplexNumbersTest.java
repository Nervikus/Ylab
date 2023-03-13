package complex.numbers;

import org.junit.Test;

import static org.junit.Assert.*;

/*
Метод main внизу
 */
public class ComplexNumbersTest {
    ComplexNumbers num1 = new ComplexNumbers(1, -1);
    ComplexNumbers num2 = new ComplexNumbers(3, 6);

    @Test
    public void add() {
        ComplexNumbers numbers = num1.add(num2);
        String actual = numbers.toString();
        String expected = "4.0 + 5.0i";
        assertEquals(expected, actual);
    }

    @Test
    public void subtract() {
        ComplexNumbers numbers = num1.subtract(num2);
        String actual = numbers.toString();
        String expected = "-2.0 + -7.0i";
        assertEquals(expected, actual);
    }

    @Test
    public void multiply() {
        ComplexNumbers numbers = num1.multiply(num2);
        String actual = numbers.toString();
        String expected = "9.0 + 3.0i";
        assertEquals(expected, actual);
    }

    @Test
    public void getModulus() {
        double actual = num1.getModulus();
        double expected = 1.4142135623730951;
        assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void testToString() {
        String actual = num1.toString();
        String expected = "1.0 + -1.0i";
        assertEquals(expected, actual);
    }
    @Test
    public void main() {
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
}