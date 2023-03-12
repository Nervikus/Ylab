package snils.validator;

public class SnilsValidatorImpl implements SnilsValidator {
    @Override
    public boolean validate(String snils) {
        String cleanSnils = snils.replaceAll("[^\\d]", "");
        if (cleanSnils.length() == 0) {
            System.out.print("В СНИЛСЕ отсутствуют цифры  ");
            return false;
        } else if (cleanSnils.length() != 11) {
            System.out.print("Количество цифр должно быть 11  ");
            return false;
        }

        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = Character.getNumericValue(cleanSnils.charAt(i));
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (9 - i);
        }

        int checksum;
        if (sum < 100) {
            checksum = sum;
        } else if (sum == 100) {
            checksum = 0;
        } else {
            checksum = sum % 101;
            if (checksum == 100) {
                checksum = 0;
            }
        } if (checksum == (digits[9] * 10 + digits[10])) {
            System.out.print("СНИЛС верный  ");
            return true;
        } else {
            System.out.print("СНИЛС неверный  ");
            return false;
        }
    }
}



