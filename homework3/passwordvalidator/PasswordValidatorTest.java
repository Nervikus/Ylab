package passwordvalidator;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        PasswordValidator passwordValidator = new PasswordValidatorImpl();

        String login1 = "loGin1";
        String password1 = "qWerty_123";
        String confirmPassword1 = "qWerty_123";
        boolean validate1 = passwordValidator.validate(login1, password1, confirmPassword1);
        System.out.println(validate1);
        System.out.println();

        String login2 = "login_2";
        String password2 = "1234567890qwertyuiop";
        String confirmPassword2 = "1234567890qwertyuiop";
        boolean validate2 = passwordValidator.validate(login2, password2, confirmPassword2);
        System.out.println(validate2);
        System.out.println();

        String login3 = "12-34пLo";
        String password3 = "kdkd2";
        String confirmPassword3 = "kdkd2";
        boolean validate3 = passwordValidator.validate(login3, password3, confirmPassword3);
        System.out.println(validate3);
        System.out.println();

        String login4 = "DarkSouls_777";
        String password4 = "QwErTy123ъ4";
        String confirmPassword4 = "QwErTy123ъ4";
        boolean validate4 = passwordValidator.validate(login4, password4, confirmPassword4);
        System.out.println(validate4);
        System.out.println();

        String login5 = "Dark_Souls";
        String password5 = "QwErTy1234";
        String confirmPassword5 = "QwErTy123456";
        boolean validate5 = passwordValidator.validate(login5, password5, confirmPassword5);
        System.out.println(validate5);
        System.out.println();

        String login6 = "DarkSouls_777looooooooooooo";
        String password6 = "QwErTy1234";
        String confirmPassword6 = "QwErTy1234";
        boolean validate6 = passwordValidator.validate(login6, password6, confirmPassword6);
        System.out.println(validate6);
        System.out.println();

        String login7 = "_DarkSouls_777";
        String password7 = "_QwErTy_1234";
        String confirmPassword7 = "_QwErTy_1234";
        boolean validate7 = passwordValidator.validate(login7, password7, confirmPassword7);
        System.out.println(validate7);
        System.out.println();
    }
}
