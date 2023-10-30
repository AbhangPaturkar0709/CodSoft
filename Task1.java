import java.util.Random;
import java.util.Scanner;

public class Task1 {
    public static void printSeparator(int n) {
        for (int i = 0; i <= n; i++) {
            System.out.print("- = ");
        }
        System.err.println();
    }

    public static void printSubSeparator(int n) {
        System.out.print("\t\t");
        for (int i = 0; i <= n; i++) {
            System.out.print("- ");
        }
        System.err.println();
    }

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int score = 0;
        int level = 1;
        int maxRange = 100;
        int maxAttempts = 10;

        System.out.println("\t\t- - - WELCOME TO NUMBER GUESSING GAME..! - - -\n");

        while (level <= 10) {
            int round = 1;
            boolean levelCompleted = true;
            int attempts = maxAttempts + level - 1; // Increase attempts by level

            while (round <= 5) {
                int numberToGuess = random.nextInt(maxRange) + 1;
                boolean hasGuessedCorrectly = false;

                System.out.println("\t\t\tLevel " + level + "\t\t\tRound " + round
                        + "\n\n\t\t   Random Number Selected between 1 and " + maxRange
                        + ".\n\t\t\t\tTry to guess it..!\n");

                while (attempts > 0) {
                    System.out.print("\tEnter your guess (Attempts left: " + attempts + ")" + numberToGuess + ": ");
                    int userGuess = scanner.nextInt();
                    attempts--;

                    if (userGuess == numberToGuess) {
                        hasGuessedCorrectly = true;
                        System.out.println("\n\tCongratulations! You've guessed the correct number: " + numberToGuess);
                        break;
                    } else if (userGuess < numberToGuess) {
                        System.out.println("\n\tYour guess is too low. Try a higher number.");
                        printSubSeparator(20);
                    } else {
                        System.out.println("\n\tYour guess is too high. Try a lower number.");
                        printSubSeparator(20);
                    }
                }

                if (!hasGuessedCorrectly) {
                    System.out.println("Sorry, you've reached the maximum number of attempts. The correct number was: "
                            + numberToGuess);
                    levelCompleted = false;
                    break;
                }

                score += hasGuessedCorrectly ? 1 : 0;

                System.out.println("\n\t\t- : - : - : Your current score: " + score + ": - : - : -");
                printSeparator(20);

                if (round < 5) {
                    attempts = maxAttempts + level - 1 - round; // Decrease attempts for the next round
                }
                round++;
            }

            if (levelCompleted) {
                if (level < 10) {
                    System.out.print("Congratulations! Do you want to move to the next level? (yes/no): ");
                    String nextLevel = scanner.next();
                    if (nextLevel.equalsIgnoreCase("yes")) {
                        level++;
                        maxRange += 100;
                    } else {
                        break;
                    }
                } else {
                    System.out.println("\t\t\tCongratulations! \n\t\t\tYou've completed all 10 levels.");
                    break;
                }
            } else {
                System.out.println(
                        "You need to successfully complete the current level before moving to the next level.");
            }
        }

        System.out.println("Thank you for playing the Number Guessing Game!");
        scanner.close();
    }
}
