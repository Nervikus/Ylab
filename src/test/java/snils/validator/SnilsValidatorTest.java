package snils.validator;

import org.junit.Test;

public class SnilsValidatorTest {

    @Test
    //можно ли тестировать таким образом (не метод класса, а метод main)?
    public void main() {
        System.out.println(new SnilsValidatorImpl().validate("01468870570"));
        System.out.println(new SnilsValidatorImpl().validate("90114404441"));
        System.out.println(new SnilsValidatorImpl().validate("43432"));
        System.out.println(new SnilsValidatorImpl().validate(""));
        System.out.println(new SnilsValidatorImpl().validate("fdr"));
        System.out.println(new SnilsValidatorImpl().validate("fdr24"));
        System.out.println(new SnilsValidatorImpl().validate("90d114fg404h441"));
        System.out.println(new SnilsValidatorImpl().validate("901-144-044 41"));
    }
}