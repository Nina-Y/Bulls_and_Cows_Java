package bullscows;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        String length = sc.nextLine();
        try {
            Integer.parseInt(length);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + length + "\" isn't a valid number.");
            System.exit(0);
        }
        int len = Integer.parseInt(length);
        System.out.println("Input the number of possible symbols in the code:");
        int posSym = sc.nextInt();
        if (len > 36) {
            System.out.println("Error: can't generate a secret number with a length of " +
                    len + " because there aren't enough unique digits.");
        } else if (len <= 0) {
            System.out.println("Error: please enter a code with a length greater than " + len + ".");
        } else if (posSym > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        } else if (posSym < len) {
            System.out.println("Error: it's not possible to generate a code with a length of " +
                    len + " with " + posSym + " unique symbols.");
        } else {
            prepareSecret(len, posSym);
            System.out.println("Okay, let's start a game!");
            countBullsAndCows(len, posSym);
        }
    }

    public static void prepareSecret(int len, int posSym) {
        System.out.print("The secret is prepared: ");
        for (int i = 0; i < len; i++) {
            System.out.print("*");
        }
        int lettersN = posSym - 10;
        char fromChar = 97;
        for (int i = 0; i < lettersN - 1; i++) {
            fromChar++;
        }
        char toChar = fromChar;
        if (posSym <=10) {
            System.out.println(" (0-9).");
        } else {
            System.out.println(" (0-9, a-" + toChar + ").");
        }
    }

    public static String randomGenerator(int len, int posSym) {
        String set = "0123456789abcdefghijklmnopqrstuvwxyz";
        String[] subSet = set.substring(0, posSym).split("");
        List<String> randomList = Arrays.asList(subSet);
        Collections.shuffle(randomList);
        StringBuilder result = new StringBuilder();
        for (var ch : randomList.subList(0, len)) {
            result.append(ch);
        }
        return result.toString();
    }

    public static void countBullsAndCows(int len, int posSym) {
        String code = randomGenerator(len, posSym);
        int bullsPerGuess = 0;
        int cowsPerGuess = 0;
        int turn = 0;
        while (bullsPerGuess != len) {
            turn++;
            System.out.println("Turn " + turn + ":");
            String guess = sc.next();
            if (guess.length() != len) {
                System.out.println("Error: please enter a code with a length of " + len + ".");
            }
            for (int i = 0; i < len; i++) {
                if (guess.charAt(i) == code.charAt(i)) {
                    bullsPerGuess++;
                } else if (guess.contains(String.valueOf(code.charAt(i)))) {
                    cowsPerGuess++;
                }
            }
            printResult(len, bullsPerGuess, cowsPerGuess);
            bullsPerGuess = 0;
            cowsPerGuess = 0;
        }
    }

    public static void printResult(int len, int bullsPerGuess, int cowsPerGuess) {
        if (bullsPerGuess == len) {
            System.out.println("Grade: " + len + " bulls\nCongratulations! You guessed the secret code.");
            System.exit(0);
        } else if (bullsPerGuess > 0 && cowsPerGuess > 0) {
            System.out.println("Grade: " + bullsPerGuess + " bull(s) and "+ cowsPerGuess + " cow(s)");
        } else if (cowsPerGuess > 0) {
            System.out.println("Grade: " + cowsPerGuess + " cow(s)");
        } else if (bullsPerGuess > 0) {
            System.out.println("Grade: " + bullsPerGuess + " bull(s)");
        } else {
            System.out.println("Grade: None.");
        }
    }
}
