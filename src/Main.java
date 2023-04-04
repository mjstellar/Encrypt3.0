import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("To stop the program print «stop the program» \uD83D\uDE01");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String inputText = scanner.nextLine().toLowerCase();

            if (inputText.equals("stop the program")) {
                break;
            }

            int characterCount = inputText.replaceAll("[^\\w]", "").length();
            System.out.println(characterCount + " symbols");
            if (characterCount <= 0) {
                System.out.println("Sorry, but something went wrong. Perhaps this cannot be encrypted or decrypted in some way");
                break;
            }

            String encryptedText = encrypt(inputText);
            System.out.println("Encrypted text: " + encryptedText);

            boolean isEvenLength = inputText.replaceAll("[^a-z]", "").length() % 2 == 0;

            if (isEvenLength) {
                String decryptedText = Decrypt.decrypt(inputText);
                System.out.println();
                System.out.println("Decrypted text: " + decryptedText);
            } else {
                System.out.println("The string is not encrypted or may be incorrect");
            }
        }
    }

    public static String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'a' && c <= 'x') {
                result.append((char) (c + 1));
                result.append((char) (c + 2));
            } else if (c == 'y') {
                result.append('z');
                result.append('a');
            } else if (c == 'z') {
                result.append('a');
                result.append('b');
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }


    public static class Decrypt {

        public static String decrypt(String str) {
            // 1. Проверяем количество символов в строке, не учитывая пробелы и знаки препинания.
            int characterCount = str.replaceAll("[^\\w]", "").length();
            if (characterCount % 2 != 0) {
                return "This is NOT an encrypted string";
            } else {

                // 2. Создаем две переменные типа String.
                String firstPair = str.substring(0, 2);



                // 3. Проверяем, является ли второй символ второй пары следующей буквой английского алфавита после первого символа первой пары.
                char firstChar1 = firstPair.charAt(0);
                char secondChar1 = firstPair.charAt(1);
                char nextChar1 = (char) (firstChar1 + 1);
                if (secondChar1 != nextChar1) {
                    return "This is NOT an encrypted string";
                }

                if(characterCount > 3) {
                    String secondPair = str.substring(2, 4);
                    char firstChar2 = secondPair.charAt(0);
                    char secondChar2 = secondPair.charAt(1);
                    char nextChar2 = (char) (firstChar2 + 1);
                    if (secondChar2 != nextChar2) {
                        return "This is NOT an encrypted string";
                    }
                }

                if(characterCount == 26) {
                    System.out.println("Perhaps that is an latin alphabet... Anyway,");
                } else if (characterCount == 33) {
                    System.out.println("Похоже, это русский алфавит... Впрочем,");
                }


                // Сообщаем, если строка прошла проверку.
                System.out.println("this may be an encrypted string");
            }
            System.out.println("\n" + Arrays.toString(splitPairs(str)));

            String[] cipher = {"bc", "cd", "de", "ef", "fg", "gh", "hi", "ij", "jk", "kl",
                    "lm", "mn", "no", "op", "pq", "qr", "rs", "st", "tu",
                    "uv", "vw", "wx", "xy", "yz", "za", "ab"};

            char[] letters = new char[26];
            for (int i = 0; i < 26; i++) {
                letters[i] = (char) ('a' + i);
            }

            String[] patient = splitPairs(str);
            StringBuilder finalDecrypt = new StringBuilder();

            for (int i = 0; i < patient.length; i++) {
                for (int j = 0; j < 26; j++) {
                    if (patient[i].equals(cipher[j])){
                        finalDecrypt.append(letters[j]);
                        break;
                    }
                }
            }
            return finalDecrypt.toString();
        }
    }

    public static String[] splitPairs(String str) {
        // Определяем размер массива, равный половине длины строки
        int size = (int) Math.ceil((double) str.length() / 2);
        String[] pairs = new String[size];
        for (int i = 0; i < size; i++) {
            // Получаем символы на четных и нечетных индексах
            char firstChar = str.charAt(i * 2);
            char secondChar = (i * 2 + 1 < str.length()) ? str.charAt(i * 2 + 1) : '\0';
            // Складываем символы в пару
            pairs[i] = Character.toString(firstChar) + Character.toString(secondChar);
        }
        return pairs;
    }
}