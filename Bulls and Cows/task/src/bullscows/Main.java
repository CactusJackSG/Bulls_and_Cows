package bullscows;

import java.util.*;

public class Main {

    private static final List<Character> fullList = new ArrayList<>(List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
            'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't','u', 'v', 'w', 'x', 'y', 'z'));
    
    public static int getBulls(String secretCode, String inputCode) {
        int bulls = 0;
        for (int i = 0; i < secretCode.length(); i++) {
            if (secretCode.charAt(i) == inputCode.charAt(i)) {
                bulls++;
            }
        }
        return bulls;
    }

    public static int getCows(String secretCode, String inputCode) {
        int cows = 0;
        for (int i = 0; i < secretCode.length(); i++) {
            for (int j = 0; j < inputCode.length(); j++) {
                if (i != j && secretCode.charAt(i) == inputCode.charAt(j)) {
                    cows++;
                }
            }
        }
        return cows;
    }

    public static int mainGame (String secretCode) {
        Scanner scanner = new Scanner(System.in);
        String inputCode = scanner.nextLine();
        int bulls = getBulls(secretCode, inputCode);
        int cows = getCows(secretCode, inputCode);
        if (bulls == 0 && cows == 0) {
            System.out.println("Grade: None");
        } else if (bulls == 0) {
            System.out.printf("Grade: %d cow(s).", cows);
        } else if (cows == 0) {
            System.out.printf("Grade: %d bull(s).", bulls);
        } else {
            System.out.printf("Grade: %d bull(s) and %d cow(s).%n", bulls, cows);
        }
        return bulls;
    }

    private static String generateCode(int n, int symbols) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        while (code.length() < n) {
            int randomIndex = random.nextInt(symbols);
            char nextChar = fullList.get(randomIndex);
            boolean unique = true;
            for (int i = 0; i < code.length(); i++) {
                if (code.charAt(i) == nextChar) {
                    unique = false;
                    break;
                }
            }
            if (unique) {
                code.append(nextChar);
            }
        }
        return code.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        String secretCode = "";
        System.out.println("Please, enter the secret code's length:");
        String input = "";
        boolean exit = false;
        try {
            input = scanner.next();
            n = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.%n",input);
            exit = true;
        }
        if (!exit) {
        /*while (!(n >= 1 && n <= 9)) {
            System.out.println("Please, enter the secret code's length:");
            n = scanner.nextInt();
            secretCode = generateCode(n);
            if (!(n >= 1 && n <= 9)) {
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.%n", n);
            }
        }*/
            if (n < 1)
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.%n", n);
            else {
                System.out.println("Input the number of possible symbols in the code:");
                int symbols = 0;
                try {
                    input = scanner.next();
                    symbols = Integer.parseInt(input);
                    if (symbols < n) {
                        throw new Exception(String.format("Error: it's not possible to generate a code with a length of %d with %d unique symbols.",n, symbols));
                    }
                    if (symbols > 36) {
                        throw new Exception("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                    }
                } catch (NumberFormatException e) {
                    System.out.printf("Error: \"%s\" isn't a valid number.%n",input);
                    exit = true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    exit = true;
                }
                if (!exit) {
                    secretCode = generateCode(n, symbols);
                    StringBuilder secretStars = new StringBuilder();
                    for (int i = 0; i < n; i++) {
                        secretStars.append("*");
                    }
                    String secretNumbers = "";
                    String secretLetters = "";
                    if (symbols <= 10) {
                        secretNumbers = String.format("(0-%d)", symbols - 1);
                        System.out.printf("The secret is prepared: %s %s.%n", secretStars, secretNumbers);
                    } else {
                        secretNumbers = "(0-9)";
                        secretLetters = String.format("(a-%c)", (96 + symbols - 10));
                        System.out.printf("The secret is prepared: %s %s, %s.%n", secretStars, secretNumbers, secretLetters);
                    }

                    System.out.println("Okay, let's start a game!");
                    int bulls = 0;
                    int turns = 1;
                    while (bulls != n) {
                        System.out.printf("Turn %d:%n", turns++);
                        bulls = mainGame(secretCode);
                        if (bulls == n) {
                            System.out.println("Congratulations! You guessed the secret code.");
                        }

                    }
                }}
        }


    }
}
