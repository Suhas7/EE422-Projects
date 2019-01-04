// Game.java 
/*
 * EE422C Project 2 submission by
 * Suhas Raja
 * scr2469
 * 16345
 * Fall 2018
 * Slip days used: 0
 */
package assignment2;
import java.util.Scanner;

public class Game {
	int pegs = GameConfiguration.pegNumber;
	Scanner kb = new Scanner(System.in);
	boolean isTesting;
	public Game(boolean isTestingInp) {
		isTesting=isTestingInp;
		System.out.println("Do you want to play a new game? (Y/N):");
		String Bool = kb.next();
		while(Bool.equals("Y")) {
			runGame();	//run the game if they want to play
			System.out.println("Do you want to play a new game? (Y/N):"); //check whether they want to play again
			Bool = kb.next();
		}
		kb.close();
	}
	private void runGame() {
		String code = SecretCodeGenerator.getInstance().getNewSecretCode();
		String[] hist = new String[GameConfiguration.guessNumber];
		if(isTesting) { //if you're testing output the right code
			System.out.println("Secret code: "+code);
		}
		int currGuesses=GameConfiguration.guessNumber;
		boolean noWin=true;
		Validator val=new Validator();
		while(currGuesses!=0 && noWin) { //while you have guesses left and haven't won
			System.out.println("\nYou have "+currGuesses+" guess(es) left.");
			System.out.println("Enter guess:");
			String inputGuess = kb.next();
			if(val.checkValidity(inputGuess, code, GameConfiguration.colors)) { //confirm valid guess
				hist[(GameConfiguration.guessNumber-currGuesses)]=val.pegCount(inputGuess, code); //log guess
				System.out.println(hist[(GameConfiguration.guessNumber-currGuesses)]); //print analysis
				currGuesses-=1;
				if(code.equals(inputGuess)) { //check win condition, if true, exit
					noWin = false;
					System.out.println("You win!\n");
				}
			}else if(inputGuess.equals("HISTORY")){ //if it is macro command, print the log without taking a guess
				for(int i = 0; i<(GameConfiguration.guessNumber-currGuesses); i++) {
					System.out.println(hist[i]);
				}
			}else { //if invalid just restart loop
				System.out.println("INVALID_GUESS");
			}
		}
		if(currGuesses==0 && noWin) { //lose condition and user notification  of loss
			System.out.println("You lose! The pattern was "+code);
		}
	}
}
