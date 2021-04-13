package lists;

public class Link<E>{
	public E item;
	public Link<E> next;
	
	
	public Link(E item, Link<E> next) {
		this.item = item;
		this.next = next;
	}

	public E item() {
		return item;
	}

	public void setItem(E item) {
		this.item = item;
	}

	public Link<E> next() {
		return next;
	}

	public void setNext(Link<E> next) {
		this.next = next;
	}
	

	
}
