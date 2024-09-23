import java.util.Scanner;

public class StringCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение: ");
        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calculate(String input) throws Exception {
        // Убираем лишние пробелы
        input = input.trim();

        // Проверка на правильность формата
        if (!input.matches("\"[^\"]{1,10}\"\\s*[\\+\\-\\*/]\\s*(\"[^\"]{1,10}\"|\\d{1,2})")) {
            throw new Exception("Неверный формат ввода");
        }

        // Определение операции и операндов
        String[] parts;
        char operator;

        if (input.contains("+")) {
            operator = '+';
            parts = input.split("\\+");
        } else if (input.contains("-")) {
            operator = '-';
            parts = input.split("-");
        } else if (input.contains("*")) {
            operator = '*';
            parts = input.split("\\*");
        } else if (input.contains("/")) {
            operator = '/';
            parts = input.split("/");
        } else {
            throw new Exception("Операция не поддерживается");
        }

        String str1 = parts[0].trim();
        String str2 = parts[1].trim();

        // Удаление кавычек
        str1 = str1.substring(1, str1.length() - 1);

        if (operator == '+' || operator == '-') {
            // Второй операнд тоже строка
            if (!str2.startsWith("\"") || !str2.endsWith("\"")) {
                throw new Exception("Неправильный второй операнд для операции " + operator);
            }
            str2 = str2.substring(1, str2.length() - 1);
        } else {
            // Второй операнд число
            int number = Integer.parseInt(str2);
            if (number < 1 || number > 10) {
                throw new Exception("Число должно быть от 1 до 10");
            }
            str2 = String.valueOf(number);
        }

        // Выполнение операции
        String result = "";
        switch (operator) {
            case '+':
                result = str1 + str2;
                break;
            case '-':
                result = str1.replace(str2, "");
                break;
            case '*':
                int multiplier = Integer.parseInt(str2);
                result = str1.repeat(multiplier);
                break;
            case '/':
                int divisor = Integer.parseInt(str2);
                int partLength = str1.length() / divisor;
                result = str1.substring(0, partLength);
                break;
            default:
                throw new Exception("Операция не поддерживается");
        }

        // Ограничение длины результата
        if (result.length() > 40) {
            result = result.substring(0, 40) + "...";
        }

        return result;
    }
}