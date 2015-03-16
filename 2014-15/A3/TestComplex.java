import java.util.*;
public class TestComplex {

    public static void main(String[] args) {
		
    	runComplexTests();

    }

    public static void runComplexTests() {

    	int cases = 0;
    	int passes = 0;

    	int[][] additionTests = {
    		{ 1, 2, 1, 2, 2, 4 },     // ++ case
    		{-3, -3, 3, 3, 0, 0},     // +- case
    		{-4, 5, 5, 3, 1, 8},      // -+ case
    		{-1, -1, -1, -1, -2, -2}  // -- case
    	};

    	int[][] multiplicationTests = {
    		{ -3, 2, -3, 2, 5, -12 },     
    		{-1, 1, -1, 1, 0, -2},     
    		{0,0,0,0,0,0},      
    		{-2,-2,-2,-2, 0, 8}  
    	};

    	

    	cases += additionTests.length;
    	cases += multiplicationTests.length;


    	for (int[]testcase:additionTests) {
    		if (testAddition(testcase))
    			passes++;
    	}

    	for (int[]testcase:multiplicationTests) {
    		if (testMultiplication(testcase))
    			passes++;
    	}

    	System.out.printf("%d/%d TESTCASES PASSED\n", passes, cases);

    }

    public static boolean testMultiplication( int[] arr) {

    	Complex a = new Complex(arr[0], arr[1]);
    	Complex b = new Complex(arr[2], arr[3]);
    	a.multiply(b);
    	
    	if (a.getReal() != arr[4] || a.getImag() != arr[5]) {
    		System.err.printf("Multiplication failure: (%d + %di) + (%d + %di) != %d + %di, GOT: %d + %di\n", arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],(int)a.getReal(), (int)a.getImag() );
			
			return false;
		}
		return true;

    }

    public static boolean testAddition( int [] arr) {

    	Complex a = new Complex(arr[0], arr[1]);
    	Complex b = new Complex(arr[2], arr[3]);
    	a.add(b);
    	
    	if (a.getReal() != arr[4] || a.getImag() != arr[5]) {
    		System.err.printf("Addition failure: (%d + %di) + (%d + %di) != %d + %di\n", arr[0],arr[1],arr[2],arr[3],arr[4],arr[5] );
			return false;
		}
		return true;   	
    }

}