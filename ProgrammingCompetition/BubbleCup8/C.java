import java.util.*;
public class C {

    static Scanner sc = new Scanner(System.in);

	static void print2dArray(int [][] club) {
		for (int r[] : club ) {
			System.out.println(Arrays.toString(r));
		}
	}

    public static void main(String[] args) {
		
		int N = Integer.parseInt(sc.nextLine());

		int[][] fridayClubs = new int[N][N];
		int[][] satClubs = new int[N][N];

		int j = 0;
		String line;

		while(j < N) {
			line = sc.nextLine();
			String[] split = line.split(" ");
			for (int i = 0; i < N; i++) 
				fridayClubs[j][i] = Integer.parseInt(split[i]);
			j++;
		}

		j = 0;
		while(j < N) {
			line = sc.nextLine();
			String[] split = line.split(" ");
			for (int i = 0; i < N; i++)
				satClubs[j][i] = Integer.parseInt(split[i]);
			j++;
		}

		int[] fridayMaxs = findNHighestValues(fridayClubs);
		int[] satMaxs = findNHighestValues(satClubs);

		// The Two arays I was talking about
		System.out.println(Arrays.toString(fridayMaxs));
		System.out.println(Arrays.toString(satMaxs));

		int m = getMax(fridayMaxs, satMaxs);

		System.out.println(m);

    }

    static int getMax(int[] f, int [] s) {

    	int N = f.length;

    	int [] maxValArr = new int[N];
    	int [] maxVals = new int[N];

    	for (int i = 0; i < N; i++) {
    		if (f[i] > s[i]) {
    			maxVals[i] = f[i];
    			maxValArr[i] = 0;
    		} else {
    			maxVals[i] = s[i];
    			maxValArr[i] = 1;
    		}
    	}

    	// Get the max values for each position keeping in mind that
    	// You can only take len/2 from each

    	// [4, 5, 3, 4] f
    	// [1, 2, 7, 2] s
    	// -> 18 (7+2 + 5+4)

    	// [2, 3, 0, 1]
    	int[] fIndexs = getSortedIndex(f,s);

    	// [0, 1, 3, 2]
		// int[] sIndexs = getSortedIndex(s,f);

		// Answer is then the sum of the last half of fs indexs from f
		// and the sum of indexs of sf from s where the indexs do not overlap

		// int m = f[0] + f[1] + s[3] + s[2]
		// m = 4 + 5 + 7 + 2 = 18

    	return 0;

    }


    public static int[] getSortedIndex( int[] a, int [] b ){

    	int [] aSorted = Arrays.copyOf(a, a.length);
    	Arrays.sort(aSorted);

    	int sortedIndex [] = new int[a.length];
    	int index = 0;
    	int lastVal = -1;

    	for (int i = 0; i < a.length; i++) {
    		if (aSorted[i] != lastVal) {

    			ArrayList<Integer> indexs = new ArrayList<Integer>();
    			for (int j = 0; j < a.length; j++) {
    				if (aSorted[i] == a[j]) {
    					indexs.add(j);
    				}
    			}

    			int bVals [] = new int[indexs.size()];
    			for (int p = 0; p < bVals.length; p++ ) {
    				bVals[p] = b[indexs.get(p)];
    			}

    			Arrays.sort(bVals);
    			
    			// Loop though indexs
    				// Add values to sortedIndex if equals bVals

    			int sb = 0;
    			for (int k = 0; k < bVals.length; k++) {
    				int bv = bVals[k]; // + " " + indexs.get(k) );
					// Get smallest index of this b
					for (int bI = 0;  bI < bVals.length;  bI++) {
						if (b[indexs.get(bI)] == bv && bv < sb) {
							sb = bv;
							// System.out.println("sb = " + sb);
						}
					}
    			}

		    	// [4, 5, 3, 4]
		    	// [1, 2, 7, 2]
    			// System.out.println(Arrays.toString(bVals));

    			lastVal = aSorted[i];

    		}
    	}
		return sortedIndex;
    }

    static int[] findNHighestValues(int array[][]) {

    	int [] colMaxs = new int[array.length];
    	
    	for (int r = 0; r < array.length; r++) {
    		int max = 0;
    		for (int c = 0; c < array.length; c++) {
    			if (array[c][r] > max) {
    				max = array[c][r];
    			}
    		}
    		colMaxs[r] = max;
    	}
    	return colMaxs;

    }

}