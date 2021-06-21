import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Solution {

	public static <E extends Comparable<E>> void swap(E[] arr, int i, int j) {
		E tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static <E extends Comparable<E>> int partition(E[] arr, int l, int r, int p) {
		E pivot = arr[p];
        r--;
        int a = r;
        int b = l;
        l--;
        swap(arr,p,a);
        while(true){
            while((l<a)&&arr[++l].compareTo(pivot) < 0);
            while((r>b)&&arr[--r].compareTo(pivot) > 0);
            swap(arr,l,r);
            if(l>=r) break;
        }
        swap(arr,l,r);
        swap(arr,l,a);
        
        return l;
	}

	public static <E extends Comparable<E>> void quicksort(E[] arr, int l, int r) {
		int p = (l+r)/2;
        int k = partition(arr,l,r,p);
        if(l < k-1) quicksort(arr, l, k);
        if(k+1 < r) quicksort(arr, k+1, r);
	}

	public static <E extends Comparable<E>> void quicksort(E[] arr) {
		quicksort(arr, 0, arr.length);
	}

	
	/* DO NOT MODIFY THE CODE BELOW */
	
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		Integer[] arr1 = new Integer[N];
		Integer[] arr2 = new Integer[N];
		
		for (int i = 0; i < N; i++)
			arr1[i] = arr2[i] = Integer.parseInt(br.readLine());
		
		br.close();
		
		Random rand = new Random(0);
		
		// verifying partition function
		for (int k=0; k<1000; k++) {
			int l = rand.nextInt(arr1.length-1);
			int r = l + rand.nextInt(arr1.length - l) + 1;
			int p = partition(arr1, l, r, (l+r)/2);
			
			for(int i=l; i<p; i++)
				if(arr1[i].compareTo(arr1[p]) > 0)
					System.out.println("err1");
			
			for(int i=p+1; i<r; i++)
				if(arr1[i].compareTo(arr1[p]) < 0)
					System.out.println("err2");
		}
		
		
		//verifying quicksort function
		quicksort(arr2);
		for (int i=0; i<arr2.length-1; i++)
			if(arr2[i] > arr2[i+1])
				System.out.println("err3");
		
		quicksort(arr1);
		for (int i=0; i<arr1.length-1; i++)
			if(arr1[i] > arr1[i+1])
				System.out.println("err4");
		
		
		//printing the result of quicksort
		for (int i=0; i<arr2.length; i++)
			System.out.println(arr2[i]);

	}
}
