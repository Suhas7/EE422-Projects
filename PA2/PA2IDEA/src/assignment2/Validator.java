// Validator.java 
/*
 * EE422C Project 2 submission by
 * Suhas Raja
 * scr2469
 * 16345
 * Fall 2018
 * Slip days used: 0
 */
package assignment2;

public class Validator{
	//will return whether a guess was a valid guess.
	public boolean checkValidity(String inpGuess, String code, String[] colors){
		boolean validGuess = true;
		if(inpGuess.length()!=code.length()) { //if the lengths are different, invalid guesss
			return false;
		}
		for(int i=0;i<inpGuess.length();i++) { //confirm characters are valid
			boolean exists = false;
			for(int j = 0; j<colors.length;j++) {//check that they are all colors
				if(colors[j].charAt(0)==(inpGuess.charAt(i))) {
					exists = true;
					j = colors.length;
				}
			}
			if(!exists) { //if an invalid character is reached, break out of loop and return false
				validGuess=false;
				i=inpGuess.length();
			}
		}
		return validGuess;
	}
	//This function will return the response to the guess, indicating the peg counts
	//the returned string contains the guess and peg info so it can be directly printed
	//with the correct format
	public String pegCount(String inpGuess,String code) {
		int b = 0;
		int w = 0;
		for(int i = 0; i<code.length();i++) { //find the number of black pegs
			if(code.charAt(i)==inpGuess.charAt(i)) {
				b+=1;
			}
		}
		int[] x = new int[26];
		for(int i = 0; i<26;i++) { //clear char array
			x[i]=0;
		}
		for(int i = 0; i<code.length();i++) { //population char trackers
			x[(int) code.charAt(i)-65]++;
		}
		for(int i = 0; i<26;i++) { //iterate for each unique letter to generate the count
			if(x[i] != 0) {
				int currChar=0;
				for(int j = 0; j<inpGuess.length();j++) {
					if(inpGuess.charAt(j)==(char)i+65) {
						currChar++;
					}
				}
				if(currChar>x[i]) {
					currChar = x[i];
				}
				w+=currChar;
			}
		}
		w=w-b;
		return inpGuess+" -> "+b+"b_"+w+"w";		
	}
}