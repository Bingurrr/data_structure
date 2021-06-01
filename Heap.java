import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws IOException {

		PriorityQueue<Integer> pq = new Heap<Integer>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String line;
		while((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			String operation = st.nextToken().strip();
			
			Integer k;
			switch(operation) {
				case "insert":
					k = Integer.parseInt(st.nextToken());
					pq.insert(k);
					break;
				case "removemax":
					k = pq.removeMax();
					if(k != null)
						System.out.println("Removed value: " + k);
					break;
				case "getmax":
					k = pq.getMax();
					if(k != null)
						System.out.println("Found value: " + k);
					break;
				case "size":
					System.out.println("PQ size: " + pq.size());
					break;
				case "clear":
					System.out.println(pq);
					pq.clear();
					System.out.println("PQ is initialized.");
					break;
			}
			
		}
		
		
		br.close();
	}
}

interface PriorityQueue<E>{
	public void insert(E e);
	public E removeMax();
	public E getMax();
	public int size();
	public void clear();
}

class Heap<E extends Comparable<E>> implements PriorityQueue<E>{

	final int DEFAULT_CAPACITY = 10;
	E[] data;
	int size;
	
	private int parent(int pos) {
		return (pos-1)/2;
	}
	
	private int leftchild(int pos) {
		return pos * 2 + 1;
	}
	
	private int rightchild(int pos) {
		return pos * 2 + 2;
	}
	
	private boolean isleaf(int pos) {
		return pos * 2 + 1 >= size && pos < size;
	}
	
	public Heap(E[] data) {
		size = data.length;
		for(int i=size/2-1; i>=0; i--) {
			siftdown(i);
		}
	}
	
	public void siftdown(int pos) {
		if(isleaf(pos)||pos >=size) return;
		E parent = data[pos];
		if(data[rightchild(pos)] == null) {
			if(data[leftchild(pos)].compareTo(parent) >= 0) {
				data[pos] = data[leftchild(pos)];
				data[leftchild(pos)] = parent;
				pos = leftchild(pos);
				siftdown(pos);
			}
		}
		else {
			if(data[leftchild(pos)].compareTo(parent) > 0 && data[leftchild(pos)].compareTo(data[rightchild(pos)]) >= 0) {
				data[pos] = data[leftchild(pos)];
				data[leftchild(pos)] = parent;
				pos = leftchild(pos);
				siftdown(pos);
			}
			else if(data[rightchild(pos)].compareTo(parent) > 0 && data[rightchild(pos)].compareTo(data[leftchild(pos)]) >= 0) {
				data[pos] = data[rightchild(pos)];
				data[rightchild(pos)] = parent;
				pos = rightchild(pos);
				siftdown(pos);
			}	
		}
		return;
	}
	public Heap() {
		data = (E[]) new Comparable[DEFAULT_CAPACITY];
		size = 0;
	}
	
	public String toString() {
		return Arrays.toString(Arrays.copyOf(data, size)); 
	}

	@Override
	public void insert(E e) {
		if(size == data.length) {
			data = Arrays.copyOf(data, data.length*2);
		}
		data[size] = e;
		int num = size;
		size++;
		while(data[num].compareTo(data[parent(num)]) > 0) {
			data[num] = data[parent(num)]; 
			data[parent(num)] = e;
			num = parent(num);
			if(num == 0) break;
		}
	}

	@Override
	public E removeMax() {
		if(size==0) {return null;}
		E ret = data[0];
		data[0] = data[size-1];
		data[size-1] = null;
		size--;
		siftdown(0);
		return ret;
	}


	@Override
	public E getMax() {
		if(size == 0) return null;
		return data[0];
	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
        data = (E[]) new Comparable[DEFAULT_CAPACITY];
		size = 0;
	}
    
	
}
