package guess;

import java.util.*;

public class Guess {
    
public static final int MAX_NUM = 100;  // The max number that is possible
    

    public static void main(String[] args) {
        instructions();
        
        Scanner console = new Scanner(System.in);
        Random rand = new Random();
        int n = rand.nextInt(MAX_NUM) + 1;  // Decides what the number to guess is 
        
        int guess;                          // Stores the guess of the user
        int tries = 0;                      // Stores the number of tries
        
        do {
            System.out.print("Enter a guess: ");
            guess = console.nextInt();
            tries++;
            
            // Determines whether the user guessed the right number, and gives hints if they do not
            if (guess == n) {
                 if (tries > 1) {
                     System.out.println("*****************");
                     System.out.println("Congratulations, you guessed in " + tries + " tries!");
                 }
                 else {
                     System.out.println("*****************");
                     System.out.println("Congratulations, you guessed in " + tries + " try!");
                 }
             }
            else if (guess < n) {
                System.out.println("Sorry, wrong number....try again.");
                System.out.println("Your guess was too low.");
                System.out.println();
            }
            else if (guess > n) {
                System.out.println("Sorry, wrong number....try again.");
                System.out.println("Your guess was too high.");
                System.out.println();
            }
        } while (guess != n);       
        
    }
    
    // Prints out the instructions for playing the game
    public static void instructions() {
        System.out.println("This is a guessing game. I'll think of");
        System.out.println("a number between 1 and 100 and you");
        System.out.println("try to guess it."); 
        System.out.println();
    }
}
