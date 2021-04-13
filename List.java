package lists;

public interface List<E> {
	public void claer();
	public void insert(int pos, E item);
	public void append(E item);
	public void update(int pos, E item);
	public E getvalue(int pos);
	public E remove(int pos);
	public int length();
	public ListIterator<E> listIterator();
}

