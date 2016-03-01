/* RPSLS.java
 * This program simulates a best of 3 game of Rock, Paper, Scissors, Lizard,
 * Spock against a computer opponent.
 */
package rpsls;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Rock Paper Scissors Lizard Spock
 * <p>
 * http://en.wikipedia.org/wiki/Rock-paper-scissors-lizard-Spock
 * <p>
 * Interface for a human to play against the computer.
 */

public class RPSLS {
    /**
     * Set up the rules for the game.
     * What are the moves, and what beats what?
     */
    public enum Move {
        Rock, Paper, Scissors, Lizard, Spock;

        static {
            Rock.willBeat(Scissors, Lizard);
            Paper.willBeat(Rock,Spock);
            Scissors.willBeat(Paper, Lizard);
            Lizard.willBeat(Spock,Paper);
            Spock.willBeat(Rock,Scissors);
        }

        // what will this move beat - populated in static initializer
        private Move[] ibeat;
        private void willBeat(Move...moves) {
            ibeat = moves;
        }

        /**
         * Return true if this Move will beat the supplied move
         * @param move the move we hope to beat
         * @return true if we beat that move.
         */
        public boolean beats(Move move) {
            // use binary search in case someone wants to set up crazy rules.
            return Arrays.binarySearch(ibeat, move) >= 0;
        }

    }    

    // This is a prompt that is set up just once per JVM
    private static final String MOVEPROMPT = buildOptions();
    private static String buildOptions() {
        StringBuilder sb = new StringBuilder();
        sb.append("     -> ");
        // go through the possible moves, and make a prompt string.
        for (Move m : Move.values()) {
            sb.append(m.ordinal() + 1).append(") ").append(m.name()).append(" ");
        }
        // include a quit option.
        sb.append(" q) Quit");
        return sb.toString();
    }

    /**
     * get some input from the human.
     * @param scanner What we read the input from.
     * @param prompt What we prompt the user for.
     * @param defval If the user just presses enter, what do we return.
     * @return the value the user entered (just the first char of it).
     */
    private static char prompt(Scanner scanner, String prompt, char defval) {
        // prompt the user.
        System.out.print(prompt + ": ");
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return defval;
        }
        return input.charAt(0);
    }

    /**
     * Simple conditional that prompts the user to play again, and returns true if we should.
     * @param scanner the scanner to get the input from
     * @return true if the user wants to continue.
     */
    private static boolean playAgain(Scanner scanner) {
        return ('n' != prompt(scanner, "\nPlay Again (y/n)?", 'y'));
    }

    /**
     * Prompt the user for a move.
     * @param scanner The scanner to get the move from
     * @return the move the user wants to do.
     */
    private static Move getHumanMove(Scanner scanner) {
        // loop until we get some valid input.
        do {
            char val = prompt(scanner, MOVEPROMPT, 'q');
            if ('q' == val) {
                // user does not want to make a move... or just presses enter too fast.
                return null;
            }
            int num = (val - '0') - 1;
            if (num >= 0 && num < Move.values().length) {
                // we got valid input. Return.
                return Move.values()[num];
            }
            System.out.println("Invalid move " + val);
        } while (true);
    }

    /**
     * Run the game.
     * @param args these are ignored.
     */
    public static void main(String[] args) {
        final Random rand = new Random();
        final Move[] moves = Move.values();
        final Scanner scanner = new Scanner(System.in);
        int htotal = 0;
        int ctotal = 0;
        do {
            System.out.println("\nReady to play Rock, Paper, Scissors, Lizard, Spock?");
            System.out.println("\nBest of 3.... Go!");
            System.out.println("\nChoose your weapon");
            int hscore = 0;
            int cscore = 0;
            bestofthree: do {
                final Move computer = moves[rand.nextInt(moves.length)];
                final Move human = getHumanMove(scanner);
                if (human == null) {
                    System.out.println("Human quits Best-of-3...");
                    // quit the best-of-three loop
                    break;
                }
                if (human == computer) {
                    System.out.printf("  DRAW... play again!! (%s same as %s)\n", human, computer);
                } else if (human.beats(computer)) {
                    hscore++;
                    System.out.printf("  HUMAN beats Computer (%s beats %s)\n", human, computer);
                } else {
                    cscore++;
                    System.out.printf("  COMPUTER beats Human (%s beats %s)\n", computer, human);
                }
                // play until someone scores 2....
            } while (hscore != 2 && cscore != 2);

            // track the total scores.
            if (hscore == 2) {
                htotal++;
            } else {
                // perhaps the human quit while ahead, computer wins that too.
                ctotal++;
            }

            String winner = hscore == 2 ? "Human" : "Computer";
            System.out.printf("\n %s\n **** %s wins Best-Of-Three (Human=%d, Computer=%d - game total is Human=%d Computer=%d)\n",
                    winner.toUpperCase(), winner, hscore, cscore, htotal, ctotal);

            // Shall we play again?
        } while (playAgain(scanner)); 

        System.out.printf("Thank you for playing. The final game score was Human=%d and Computer=%d\n", htotal, ctotal);

    }

    
}

