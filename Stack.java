package lists;

public interface Stack<E> {
	public void clear();
	public void push(E item);
	public E pop();
	public E topValue();
	public int length();
	
	
}
