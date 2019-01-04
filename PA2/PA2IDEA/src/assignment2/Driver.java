// Driver.java 
/*
 * EE422C Project 2 submission by
 * Suhas Raja
 * scr2469
 * 16345
 * Fall 2018
 * Slip days used: 0
 */

package assignment2;

public class Driver {
	public static void main(String[] args) {
		System.out.println("Welcome to Mastermind."); //welcome message
		boolean testing = false;
		if(args.length>0) {//check whether testing
			if (args[0].equals("1")) {
				testing=true;
			}
		}
		Game game = new Game(testing); //instantiate game to play
	}
}
