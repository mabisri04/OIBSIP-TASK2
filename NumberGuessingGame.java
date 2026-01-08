import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Implements a console-based "Guess the Number" game with multiple rounds and scoring.
 */
public class NumberGuessingGame {
    
    // Constants for game rules
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 100;
    private static final int MAX_ATTEMPTS = 7;
    private static final int MAX_ROUNDS = 3;

    private int totalScore = 0;
    private int roundsWon = 0;
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

    public static void main(String[] args) {
        // --- COMMAND LINE INSTRUCTIONS FOR RUNNING ---
        // 1. Navigate to the directory containing NumberGuessingGame.java in your CMD.
        // 2. Compile: javac NumberGuessingGame.java
        // 3. Run: java -cp . NumberGuessingGame
        // ---------------------------------------------
        NumberGuessingGame game = new NumberGuessingGame();
        game.start();
    }

    /**
     * Initializes and controls the flow of the entire game.
     */
    public void start() {
        System.out.println("--- Welcome to the Number Guessing Game! ---");
        System.out.printf("I will generate a number between %d and %d.\n", MIN_RANGE, MAX_RANGE);
        System.out.printf("You have %d attempts per round.\n", MAX_ATTEMPTS);
        System.out.printf("The game consists of %d rounds.\n\n", MAX_ROUNDS);

        for (int round = 1; round <= MAX_ROUNDS; round++) {
            System.out.println("=================================================");
            System.out.printf("STARTING ROUND %d of %d\n", round, MAX_ROUNDS);
            playRound(round);
        }

        displayFinalScore();
        scanner.close();
        System.out.println("Thank you for playing!");
    }

    /**
     * Executes a single round of the game.
     * @param roundNumber The current round number.
     */
    private void playRound(int roundNumber) {
        // Generate the secret number
        int secretNumber = random.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
        int attemptsLeft = MAX_ATTEMPTS;
        boolean guessedCorrectly = false;

        while (attemptsLeft > 0 && !guessedCorrectly) {
            System.out.printf("\n[Round %d] Attempts Left: %d\n", roundNumber, attemptsLeft);
            System.out.printf("Enter your guess (%d-%d): ", MIN_RANGE, MAX_RANGE);

            try {
                int userGuess = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (userGuess < MIN_RANGE || userGuess > MAX_RANGE) {
                    System.out.printf("Warning: Your guess must be between %d and %d.\n", MIN_RANGE, MAX_RANGE);
                    continue;
                }

                if (userGuess == secretNumber) {
                    int score = calculateScore(attemptsLeft);
                    totalScore += score;
                    roundsWon++;
                    guessedCorrectly = true;
                    System.out.println("\n🎉 CONGRATULATIONS! You guessed the number!");
                    System.out.printf("The secret number was %d. You earned %d points.\n", secretNumber, score);
                } else if (userGuess < secretNumber) {
                    System.out.println("The number is HIGHER. Try again.");
                } else {
                    System.out.println("The number is LOWER. Try again.");
                }

                attemptsLeft--;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a whole number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        if (!guessedCorrectly) {
            System.out.println("\n💔 GAME OVER for this round. You ran out of attempts.");
            System.out.printf("The secret number was %d.\n", secretNumber);
        }
    }

    /**
     * Calculates score based on attempts left (more attempts left = higher score).
     * @param attemptsLeft The number of attempts remaining when the number was guessed.
     * @return The points awarded.
     */
    private int calculateScore(int attemptsLeft) {
        // Award 10 points for a win + bonus points based on remaining attempts
        return 10 + (attemptsLeft * 5); 
    }

    /**
     * Displays the final score and summary of the game.
     */
    private void displayFinalScore() {
        System.out.println("\n=================================================");
        System.out.println("               FINAL GAME RESULTS                ");
        System.out.println("=================================================");
        System.out.printf("Rounds Played: %d\n", MAX_ROUNDS);
        System.out.printf("Rounds Won:    %d\n", roundsWon);
        System.out.printf("Total Score:   %d points\n", totalScore);
        System.out.println("=================================================");
    }
}