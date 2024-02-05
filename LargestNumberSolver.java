package assign04;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
/**
 * This class uses insertion sort to take an array and orders the array 
 * in the order that would create the largest number from concatination
 * 
 * @version 2/3/2024
 * @author Archer Fox and Alex Dean
 */
public class LargestNumberSolver {
	
	/**
	 * This method takes in an array and custom logic via a Comparator
	 * and sorts the array usings insertion sort
	 * 
	 * @param <T> takes in a genetic array to be sorted
	 * @param cmp takes in a Comparator for how the array is sorted
	 */
	public static <T> void insertionSort(T[] arr, Comparator<? super T> cmp) {
		T temp;
		int j;
		for(int i = 1; i < arr.length; i++) {
			temp = arr[i];
			j = i - 1;
			while (j >= 0 && cmp.compare(arr[j], temp) > 0) {
				arr[j + 1] = arr[j];
				arr[j] = temp;
				j--;
			}
		}
	}
	/**
	 * This method calls insertion sort to return the largest number possible by concationation
	 * with the given ring
	 * 
	 * @param arr an array that you want to find the biggest number from
	 * @return returns a BigInteger of the largest number in the array
	 */
	public static BigInteger findLargestNumber(Integer[] arr) {
		Integer[] arrTemp = Arrays.copyOf(arr, arr.length);
		StringBuilder bigNumber = new StringBuilder();
		insertionSort(arrTemp, new CompareByConcatenation());
		for (int i = arr.length - 1; i >= 0; i--) {
		   bigNumber.append(arrTemp[i]);
		}

		return new BigInteger(bigNumber.toString());
		
	}
	
	/**
	 * Returns the largest number using the findLargestNumber method as an integer primitive
	 * 
	 * @param arr
	 * @return largest number as an int
	 * @throws OutOfRangeException if largest number is out of integer range
	 */
	public static int findLargestInt(Integer[] arr) throws OutOfRangeException{
		try {
			return findLargestNumber(arr).intValueExact();
		} catch (ArithmeticException ae) {
			throw new OutOfRangeException("the number is outside the range of the int type");
		}
	}
	
	/**
	 * Returns the largest number using the findLargestNumber method as a long
	 * 
	 * @param arr
	 * @return largest number as a long
	 * @throws OutOfRangeException if the largest number is out of long range
	 */
	public static long findLargestLong(Integer[] arr) throws OutOfRangeException{
		try {
			return findLargestNumber(arr).longValueExact();
			} catch (ArithmeticException ae) {
			throw new OutOfRangeException("the number is outside the range of the long type");
		}
		
	}
	
	/**
	 * Finds the sum of all the largest numbers found from a list of Integer arrays
	 * 
	 * @param list
	 * @return BigInteger sum
	 */
	public static BigInteger sum(List<Integer[]> list) {
		BigInteger sum = new BigInteger("0");
		for (int i = 0; i < list.size(); i++) {
			sum = sum.add(findLargestNumber(list.get(i)));
		}
		return sum;
	}
	
	/**
	 * Sorts a list of arrays based on the largest number they can form and then returns the kth largest of the list
	 * 
	 * @param list
	 * @param k
	 * @return the Integer[] with the kth largest number made from concatenation
	 * @throws IllegalArgumentException if k is not a valid index of the list
	 */
	public static Integer[] findKthLargest(List<Integer[]> list, int k) throws IllegalArgumentException{
		if (k > list.size() - 1 || k < 0)
			throw new IllegalArgumentException("k is out of bounds");
		Integer[][] sortedArray = new Integer[list.size()][];

		for (int i = 0; i < list.size(); i++) {
			sortedArray[i] = list.get(i);
		}

		LargestNumberSolver.insertionSort(sortedArray, new CompareForKthLargest());

		return sortedArray[k];
	}
	
	/**
	 * This class reads a file line by line and assigns each line of text
	 * to a list of integers
	 * 
	 * @param filename takes in the name and loccation of a file to be read
	 * @return returns a list of lists of integers taken from the file
	 */
	public static List<Integer[]> readFile(String filename){
		Path path = Paths.get(filename);
		ArrayList<String[]> stringArrays = new ArrayList<>();
		try {
			Files.lines(path).forEach(l -> stringArrays.add(l.split(" ")));
		} catch (IOException e) {
			return new ArrayList<Integer[]>();
		}
		
		List<Integer[]> returnList = new ArrayList<>();
		for (int i = 0; i < stringArrays.size(); i++) {
			Integer[] tempArray = new Integer[stringArrays.get(i).length];
			for (int j = 0; j < stringArrays.get(i).length; j++) {
				tempArray[j] = Integer.parseInt(stringArrays.get(i)[j]);
			}
			returnList.add(tempArray);
		}
		
		return returnList;
	}
	
	
	
	/**
	 * an inner class that impliments comparator and uses custum logic to 
	 * sort the array properly.
	 * 
	 * @author Archer Fox and Alex Dean
	 * @version 2/3/2024
	 */
	public static class CompareByConcatenation implements Comparator<Integer> {
		
		
		/**
		 * compares to Integer objects amd sees which order would result in the larger number
		 */
		@Override
		public int compare(Integer o1, Integer o2) {
			String concat1 = o1.toString() + o2.toString();
			String concat2 = o2.toString() + o1.toString();
			int valToMod= (int) Math.pow(10, concat1.length() -1 );
			int resultOne = Integer.parseInt(concat1);
			int resultTwo = Integer.parseInt(concat2);
			if(o1.toString().charAt(0) > o2.toString().charAt(0)) 
				return 1;
			else if(o1.toString().charAt(0) < o2.toString().charAt(0)) 
				return -1;
			if(resultOne % valToMod > resultTwo % valToMod) 
				return 1;
			return -1;
		}
	}
	
	/**
	 * an inner class that impliments comparator and uses custum logic to 
	 * sort the array properly.
	 * 
	 * @author Archer Fox and Alex Dean
	 * @version 2/3/2024
	 */
	public static class CompareForKthLargest implements Comparator<Integer[]> {
		/**
		 * custom logic for sorting an array of arrays
		 */
		@Override
		public int compare(Integer[] o1, Integer[] o2) {
			Integer[] o1Copy = Arrays.copyOf(o1, o1.length);
			Integer[] o2Copy = Arrays.copyOf(o2, o2.length);

			return LargestNumberSolver.findLargestNumber(o2Copy).compareTo(LargestNumberSolver.findLargestNumber(o1Copy));
		}
		
	}
}


