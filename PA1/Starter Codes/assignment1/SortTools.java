// SortTools.java 
/*
 * EE422C Project 1 submission by
 * Suhas Raja
 * scr2469
 * 16345
 * Fall 2018
 * Slip days used: 0
 */

package assignment1;
public class SortTools {
	/**
	  * This method tests to see if the given array is sorted.
	  * @param x is the array
	  * @param n is the size of the input to be checked
	  * @return true if array is sorted
	  */
	public static boolean isSorted(int[] x, int n) {
		for(int i  = 1;i<n;i++){
			if(x[i]<x[i-1]){
				return false;
			} //if anomaly detected, break out and return false
		}
		return true; //no anomalies--> array is fine
	}

	/**
	 * This method returns the index of value v within array x.
	 * @param x is the array
	 * @param n is the size of the input to be checked
	 * @param v is the value to be searched for within x
	 * @return index of v if v is contained within x. -1 if v is not contained within x
	 */
	public static int find(int[] x, int n, int v) {
		for(int i=0;i<n;i++){//if found, return index
			if(x[i]==v){
				return i;
			}
		} 
        return -1; //if no matches, return -1
    }

	/**
	 * This method returns a newly created array containing the first n elements of x, and v.
	 * @param x is the array
	 * @param n is the number of elements to be copied from x
	 * @param v is the value to be added to the new array
	 * @return a new array containing the first n elements of x, and v
	 */
	public static int[] insertGeneral(int[] x, int n, int v){
		int[] out = new int[n+1];
		for(int i =0;i<n;i++) {
			out[i]=x[i];
		} //copy first n entries to new array
		out[n]=v; //add on new element
		return out;
    }

	/**
	 * This method inserts a value into the array and ensures it's still sorted
	 * @param x is the array
	 * @param n is the number of elements in the array
	 * @param v is the value to be added
	 * @return n if v is already in x, otherwise returns n+1
	 */
	public static int insertInPlace(int[] x, int n, int v){
		if(find(x, n, v)!=-1) {return n;} //if it is already found, no insertion needed
		int i = 0;
		while(i<n && v<x[i]){
			i++;
		} //find desired index for insertion to maintain sort
		for(int j=n;j>=i;j--){
			x[j]=x[j-1];
		} //move everything else back
		x[i]=v; //put elem in between segments
        return -1;
    }

	/**
	 * This method sorts a given array using insertion sort
	 * @param x is the array to be sorted
	 * @param n is the number of elements of the array to be sorted
	 */
	public static void insertSort(int[] x, int n){
		for(int i = 1;i<n;i++) {
			int j = i-1;
			int tmp=x[i]; //save current item
			while(tmp<x[j] && j>=0){
				x[j]=x[j-1];j--;
			} //find ideal swap 
			x[j]=tmp;
		}
    }
}
