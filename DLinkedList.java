package lists;

public class DLinkedList<E> implements List<E> {
	
	public DLink<E> head, tail;
	public int size = 0;
	
	public DLinkedList() {
		head = new DLink<E>(null, null, null);
		tail = new DLink<E>(null, null, null);
		head.next = tail;
		tail.prev = head;
		size = 0;
	}

	@Override
	public void claer() {
		head.next = tail;
		tail.prev = head;
		size = 0;
	}

	@Override
	public void insert(int pos, E item) {
		DLink<E> curr = head;
		for(int i = 0; i < pos; i ++) {
			curr = curr.next;
		}
		//curr.next = new DLink<E>(item,curr,curr.next);
		//curr.next.next.prev = curr.next;
		DLink<E> it = new DLink<E>(item,curr, curr.next);
		curr.next.prev = it;
		curr.next = it;
		size++;
	}

	@Override
	public void append(E item) {
		insert(size, item);
	}

	@Override
	public void update(int pos, E item) {
		DLink<E> curr = head;
		for(int i = 0; i < pos; i++)
			curr = curr.next;
		curr.item = item;
	}
	@Override
	public E getvalue(int pos) {
		DLink<E> curr = head;
		for(int i = 0; i < pos; i ++) 
			curr = curr.next;
		return curr.item;
	}

	@Override
	public E remove(int pos) {
		DLink<E> curr = head;
		for(int i = 0; i <pos; i++)
			curr = curr.next;
		E ret = curr.next.item;
		curr.next = curr.next.next;
		curr.next.prev = curr;
		size--;
		return ret;
	}

	@Override
	public int length() {
		return size;
	}

	@Override
	public ListIterator<E> listIterator() {
		
		return new ListIterator<E>(){
			
			DLink<E> curr = head;
			
			@Override
			public boolean hasNext() {
				return head != tail;
			}

			@Override
			public E next() {
				curr = curr.next;
				return curr.item;
			}

			@Override
			public boolean hasPrevious() {
				return tail != head;
			}

			@Override
			public E previous() {
				curr = curr.prev ;
				return curr.item;
			} 		
				
		};
	}

}
