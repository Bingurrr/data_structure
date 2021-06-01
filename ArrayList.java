import java.util.Arrays;

interface MyList<E> {
	public void clear();
	public void insert(int pos, E item);
	public void append(E item);
	public void update(int pos, E item);
	public E getValue(int pos);
	public E remove(int pos);
	public int length();
}

class MyArrayList<E> implements MyList<E> {

	private static final int defaultSize = 10;
	private int size;
	private E[] data;

	public E[] getData() {
		return data;
	}

	public MyArrayList() {
		this(defaultSize);
	}
    
	public MyArrayList(int size) {
		this.size = 0;
		data = (E[]) new Object[size];
	}

	public void clear() {
		size = 0;
	}

	public void insert(int pos, E item) {
        for(int i = size; i>pos; i--) {
			data[i] = data[i-1];
		}
		data[pos] = item;
		size++; 
        if(data[size-1] != null){
            data = Arrays.copyOf(data, data.length *2);
        }
	}

	@Override
	public void update(int pos, E item) {
		data[pos] = item;
	}

	@Override
	public E getValue(int pos) {
        return data[pos];
	}

	@Override
	public E remove(int pos) {
        E ret = data[pos];
		
		size--;
		for(int i = pos; i<size; i++) {
			data[i] = data[i+1];
		}
		return ret;
	}

	public int length() {
        return size;
	}

	public void append(E item) {
        insert(size,item);
	}

}

class Solution {
	public int solution(int[] arr) {

		MyList<Integer> L = new MyArrayList<>();

		for (int a : arr) {
			L.append(a);
		}
		L.insert(0, 10);
		L.remove(1);

		int sum = L.getValue(0);
		for(int i=0; i<L.length(); i++) {
			sum += L.getValue(i);
		}

		return sum;
	}
}
