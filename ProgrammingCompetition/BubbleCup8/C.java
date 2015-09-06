import java.util.*;
public class C {

    static Scanner sc = new Scanner(System.in);
    static boolean[] skipindexs;

    static int fridayClubPicks = 0;
    static int satClubPicks = 0;
    static boolean fridayClubFull = false;
    static boolean satClubFull = false;

	static void print2dArray(int [][] club) {
		for (int r[] : club ) {
			System.out.println(Arrays.toString(r));
		}
	}

    public static void main(String[] args) {
		
		int N = Integer.parseInt(sc.nextLine());

		skipindexs = new boolean[N];
		int[][] fridayClubs = new int[N][N];
		int[][] satClubs = new int[N][N];

		int j = 0;
		String line;

		while(j < N) {
			line = sc.nextLine();
			String[] split = line.split(" ");
			for (int i = 0; i < N; i++) {
				fridayClubs[j][i] = Integer.parseInt(split[i]);
			}
			j++;
		}

		j = 0;
		while(j < N) {
			line = sc.nextLine();
			String[] split = line.split(" ");
			for (int i = 0; i < N; i++) {
				satClubs[j][i] = Integer.parseInt(split[i]);
			}
			j++;
		}

		int[] fridayMaxs = findNHighestValues(fridayClubs);
		int[] satMaxs = findNHighestValues(satClubs);
		// System.out.println(Arrays.toString(fridayMaxs));
		// System.out.println(Arrays.toString(satMaxs));

		int s = 0;
		for (int i = 0; i < N; i++) {
			int v = getMaxWithSkips(fridayMaxs, satMaxs);
			// System.out.println(v); 
			s += v;
		}
		System.out.println(s);
    }

    static int getMaxWithSkips( int [] a, int [] b ) {

    	int max = 0;
    	int maxIndex = 0; 

    	boolean pickedFridayClub = true;

    	for (int i = 0; i < a.length; i++) {
    		boolean skip = skipindexs[i];
    		if (!skip) {

    			int fridayClubVal = 0;
    			if (!fridayClubFull) {
    				fridayClubVal = a[i];
    			}
    			int satClubVal = 0;
    			if (!satClubFull) {
    				satClubVal = b[i];
    			}

    			if ( fridayClubVal > satClubVal ) {
    				if (fridayClubVal >= max) {
	    				pickedFridayClub = true;
	    				max = fridayClubVal;
	    				maxIndex = i;    					
    				}
    			} else {
    				if (satClubVal >= max) {
	    				pickedFridayClub = false;
	    				max = satClubVal;
	    				maxIndex = i;    					
    				}
    			}

    		}
    	}

    	// Determine if a club is full
    	if (pickedFridayClub) {
    		fridayClubPicks++;
    		if (fridayClubPicks == a.length/2) 
    			fridayClubFull = true;
    	} else {
    		satClubPicks++;
    	    if (satClubPicks == a.length/2) 
    			satClubFull = true;
    	}

    	skipindexs[maxIndex] = true;
    	return max;
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