package lists;

public class DLink<E> {
	public E item;
	public DLink<E> next;
	public DLink<E> prev;
	public DLink(E item, DLink<E> prev, DLink<E> next) {
		this.item = item;
		this.prev = prev;
		this.next = next;
	}
}
