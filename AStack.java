package lists;

public class AStack<E> implements Stack<E>{
	
	public int maxSize;
	public int top;
	public E [] listArray;

	public AStack(int capacity){
		maxSize = capacity;
		listArray = (E[]) new Object[capacity];
		top = -1;
	}
	
	@Override
	public void clear() {
		top = -1;	
	}

	@Override
	public void push(E item) {
		listArray[++top] = item;
	}

	@Override
	public E pop() {
		if(top == -1) {
			return null;
		}
		else {
			return listArray[top--];
		}
	}
	
	@Override
	public E topValue() {
		if(top == -1) {
			return null;
		}
		else {
			return listArray[top];
		}
	}

	@Override
	public int length() {
		return top;
	}

}
