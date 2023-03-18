package passwordvalidator;

public interface PasswordValidator {
    boolean validate(String login, String password, String confirmPassword);
}