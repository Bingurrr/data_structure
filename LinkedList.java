package lists;

public class LinkedList<E> implements List<E>{
	
	Link<E> head, tail;
	int size;
	
	public LinkedList(){
		head = tail = new Link<E>(null,null);
		size = 0;
	}
	@Override
	public void claer() {
		head.setNext(null);
		size = 0;
	}

	@Override
	public void insert(int pos, E item) {
		Link<E> curr = head;
		for(int i = 0; i < pos; i ++) {
			curr = curr.next;
		}
		curr.next = new Link<E>(item, curr.next);
		size++;
		//new Link<E> it = new Link<E> (item,null);
	}

	@Override
	public void append(E item) {
		tail.next = new Link<E>(item, null);
		tail = tail.next;
		size++;
	}

	@Override
	public void update(int pos, E item) {
		Link<E> curr = head;
		for(int i = 0; i < pos; i++) {
			curr = curr.next();
		}
		curr.next.setItem(item);
	}

	@Override
	public E getvalue(int pos) {
		Link<E> curr = head;
		for(int i = 0; i < pos; i++) {
			curr = curr.next();
		}
		return curr.next.item();
	}

	@Override
	public E remove(int pos) {
		Link<E>curr = head;
		for(int i = 0; i < pos; i++) 
			curr = curr.next;
		
		if(curr.next == tail)
			tail = curr;
		
		E ret = curr.next.item;
		
		curr.next = curr.next.next;
		size--;
		return ret;
	}

	@Override
	public int length() {
		return size;
	}
	@Override
	public ListIterator<E> listIterator() {
		
		return new ListIterator<E>() {
			
			Link<E> curr = head;

			@Override
			public boolean hasNext() {	
				return curr != tail;
			}

			@Override
			public E next() {
				curr = curr.next;
				return curr.item();
			}

			@Override
			public boolean hasPrevious() {
				return curr != head;
			}

			@Override
			public E previous() {
				Link<E> prev = head;
				while(prev.next() != curr) 
					prev = prev.next;
				curr = prev;
				return curr.next.item();
			}
			
		};
	}

}
