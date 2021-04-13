package lists;

public class ArrList<E> implements List<E>{
	int listSize;
	E[] data;
	
	public ArrList(int capacity) {
		data = (E[]) new Object[capacity];
		this.listSize = 0;
	}
	@Override
	public void claer() {
		listSize = 0;
	}

	@Override
	public void append(E item) {
		insert(listSize, item);
	}

	@Override
	public void update(int pos, E item) {
		data[pos] = item;
	}

	@Override
	public E getvalue(int pos) {
		return data[pos];
	}

	@Override
	public E remove(int pos) {
		E ret = data[pos];
		listSize--;
		for(int i = pos; i<listSize; i++) {
			data[i] = data[i+1];
		}
		return ret;
	}
	@Override
	public int length() {
		return listSize;
	}
	@Override
	public void insert(int pos, E item) {
		for(int i = listSize; i > pos; i--) {
			data[i] = data[i-1];
		}
		data[pos] = item;
		listSize++;
	}
	@Override
	public ListIterator<E> listIterator() {
		
		ListIterator<E> ret = new ListIterator<E>() {
			
			int curr = 0;

			@Override
			public boolean hasNext() {
				
				return curr < listSize;
			}

			@Override
			public E next() {
				return data[curr++];
			}

			@Override
			public boolean hasPrevious() {
				return curr > 0;
			}

			@Override
			public E previous() {
				return data[--curr];
			}
			
		};
		
		return ret;
	}

}
