package assign04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;


/**
 * Test class for LargestNumberSolver
 * @author Archer Fox and Alex Dean
 */
public class LargestNumberSolverTest {
	private Integer[] smallIntegerArr;
	private Integer[] mediumIntegerArr;
	private Integer[] largeIntegerFitsInLong;
	private Integer[] largeIntegerArr;
	private ArrayList<Integer[]> listOfArrays = new ArrayList<>();
	private ArrayList<Integer[]> listOfArraysSingle = new ArrayList<>();

	
	@BeforeEach
	void setup() {
		smallIntegerArr = new Integer[]{999};
		
		mediumIntegerArr = new Integer[]{98,100,76};
		
		largeIntegerArr = new Integer[]{12,100,99,789,199,453,333,98,7};
		
		largeIntegerFitsInLong = new Integer[]{55,78,99,23,100,999,47};
		
		listOfArrays.add(smallIntegerArr);
		listOfArrays.add(mediumIntegerArr);
		listOfArrays.add(largeIntegerArr);
		
		listOfArraysSingle.add(largeIntegerArr);
		
	}
	
	@Test
	void testInsertionSort() {
		Integer[] fiveElementArray = new Integer[] {1,5,4,3,2};
		LargestNumberSolver.insertionSort(fiveElementArray, Comparator.naturalOrder());
		assertEquals(1, fiveElementArray[0]);
		assertEquals(5, fiveElementArray[fiveElementArray.length - 1]);
	}
	
	@Test
	void findLargestIntSingleNum(){
		assertEquals(999, LargestNumberSolver.findLargestInt(smallIntegerArr));
	}
	
	@Test
	void findLargestIntManyUnsorted(){
		assertEquals(9876100, LargestNumberSolver.findLargestInt(mediumIntegerArr));
	}
	
	@Test
	void findLargestIntThrows() {
		assertThrows(OutOfRangeException.class, () -> LargestNumberSolver.findLargestInt(largeIntegerArr));
	}
	
	@Test
	void findLargestLongThrows() {
		assertThrows(OutOfRangeException.class, () -> LargestNumberSolver.findLargestLong(largeIntegerArr));
	}
	
	@Test
	void findLargestLongOutOfIntBounds() {
		assertEquals(new BigInteger("9999978554723100"), LargestNumberSolver.findLargestNumber(largeIntegerFitsInLong));
	}
	
	@Test
	void testK() {
		assertEquals(listOfArrays.get(2), LargestNumberSolver.findKthLargest(listOfArrays, 0));
	}
	@Test
	void testKOneItem() {
		assertEquals(listOfArraysSingle.get(0), LargestNumberSolver.findKthLargest(listOfArraysSingle, 0));
	}

    @Test
    void testFindLargestNumber() {
        Integer[] arr = { 3, 30, 34, 5, 9 };
        assertEquals(new BigInteger("9534330"), LargestNumberSolver.findLargestNumber(arr));
    }

    @Test
    void testFindLargestInt() throws OutOfRangeException {
        Integer[] arr = { 3, 30, 34, 5, 9 };
        assertEquals(9534330, LargestNumberSolver.findLargestInt(arr));
    }

    @Test
    void testFindLargestLong() throws OutOfRangeException {
        Integer[] arr = { 3, 30, 34, 5, 9 };
        assertEquals(9534330L, LargestNumberSolver.findLargestLong(arr));
    }

    @Test
    void testSum() {
        List<Integer[]> list = new ArrayList<>();
        list.add(new Integer[] { 3, 30, 34, 5, 9 });
        list.add(new Integer[] { 1, 10, 100 });
        assertEquals(new BigInteger("9644430"), LargestNumberSolver.sum(list));
    }

    @Test
    void testFindKthLargest() {
        List<Integer[]> list = new ArrayList<>();
        list.add(new Integer[] { 3, 30, 34, 5, 9 });
        list.add(new Integer[] { 1, 10, 100 });
        list.add(new Integer[] { 50, 56, 5 });
        assertEquals(Arrays.asList(50, 56, 5), Arrays.asList(LargestNumberSolver.findKthLargest(list, 2)));
        assertEquals(Arrays.asList(3, 30, 34, 5, 9), Arrays.asList(LargestNumberSolver.findKthLargest(list, 0)));
    }

    @Test
    void testFindKthLargestWithIllegalArgumentException() {
        List<Integer[]> list = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> LargestNumberSolver.findKthLargest(list, 0));
    }
    

    @Test
    void testFindKthLargestLargerThanArrayIllegalArgumentException() {
        List<Integer[]> list = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> LargestNumberSolver.findKthLargest(list, list.size() + 1));
    }

    @Test
    void testReadFile() {
        String filename = "src/assign04/test_input.txt"; 
        List<Integer[]> result = LargestNumberSolver.readFile(filename);
        assertEquals(2, result.size());
        assertArrayEquals(new Integer[] { 3, 30, 34, 5, 9 }, result.get(0));
        assertArrayEquals(new Integer[] { 1, 10, 100 }, result.get(1));
    }
    
    @Test
    void testReadFileNotFound() {
    	List<Integer[]> result = LargestNumberSolver.readFile("thisdoesntexist");
    	assertEquals(0, result.size());
    }
    
    @Test
    void testAltersArrayFindLargestInt() {
    	Integer[] arrCopy = Arrays.copyOf(mediumIntegerArr, mediumIntegerArr.length);
    	LargestNumberSolver.findLargestInt(mediumIntegerArr);
    	assertTrue(Arrays.equals(arrCopy, mediumIntegerArr));
    }
    
    @Test
    void testAltersArrayFindLargestLong() {
    	Integer[] arrCopy = Arrays.copyOf(mediumIntegerArr, mediumIntegerArr.length);
    	LargestNumberSolver.findLargestLong(mediumIntegerArr);
    	assertTrue(Arrays.equals(arrCopy, mediumIntegerArr));
    }
    
    @Test
    void testAltersArrayFindLargestNumber() {
    	Integer[] arrCopy = Arrays.copyOf(mediumIntegerArr, mediumIntegerArr.length);
    	LargestNumberSolver.findLargestNumber(mediumIntegerArr);
    	assertTrue(Arrays.equals(arrCopy, mediumIntegerArr));
    }
    
    
    @Test
    void testLargestNumberVeryLarge() {
    	Integer[] veryLargeArr = new Integer[]{10,234,1000,98,67567,12,100,99,789,199,453,333,98,7,55,78,99,23,100,999,47,98,100,76};
    	assertEquals(new BigInteger("9999999989898789787766756755474533332342319912101001001001000"), LargestNumberSolver.findLargestNumber(veryLargeArr));
    }
    
    @Test
    void findLargestNumberBigInt() {
    	Integer[] arr = {1 , 45 , 9};
    	assertEquals(LargestNumberSolver.findLargestNumber(arr), new BigInteger("9451"));
    }
}
