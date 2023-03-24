package passwordvalidator;

public class PasswordValidatorImpl implements PasswordValidator {
    @Override
    public boolean validate(String login, String password, String confirmPassword) {
        try {
            if (!login.matches("[A-Za-z0-9_]+")) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }
            if (login.length() >= 20) {
                throw new WrongLoginException("Логин слишком длинный");
            }
            if (!password.matches("[A-Za-z0-9_]+")) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }
            if (password.length() >= 20) {
                throw new WrongPasswordException("Пароль слишком длинный");
            }
            if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }
        } catch (WrongLoginException | WrongPasswordException e) {
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Ввод успешный!");
        return true;
    }
}
