interface MyList<E>{
	public void clear();
	public void insert(int pos, E item);
	public void append(E item);
	public void update(int pos, E item);
	public E getValue(int pos);
	public E remove(int pos);
	public int length();
}

class Link<E> {
	private E item;
	private Link<E> next;

	Link(E item, Link<E> next) {
		this.item = item;
		this.next = next;
	}

	Link<E> next() { return next; }
	Link<E> setNext(Link<E> next) { return this.next = next; }
	E item() { return item; }
	E setItem(E item) { return this.item = item; }
}



class LinkedList<E> implements MyList<E>{

	private Link<E> head, tail;
	int size;
	
    public Link<E> head(){
        return head;
    }
    
	public LinkedList() {
		head = tail = new Link<>(null, null);
		size = 0;
	}
	
	public void clear() { head.setNext(null);}

	public void insert(int pos, E item) {
		/* write your code here!! */
        Link<E> curr = head;
        for(int i = 0; i < pos; i++){
            curr = curr.next();
        }
        curr.setNext(new Link<>(item, curr.next()));
        if(curr == tail){
            tail = curr.next();
        }
        size ++;
        
	}

	public void update(int pos, E item) {
		Link<E> curr = head;
		for(int i=0; i<pos; i++) curr = curr.next();
		curr.setItem(item);
	}

	public E getValue(int pos) {
		Link<E> curr = head;
		for(int i=0; i<pos; i++) curr = curr.next();
		return curr.item();
	}

	public E remove(int pos) {
        Link<E> curr = head;
        for(int i = 0; i < pos; i++){
            curr = curr.next();
        }
        E ret = curr.next().item();
        if(curr ==tail)
            tail = curr;
        
        curr.setNext(curr.next().next());
        
        size --;
	    
        return ret;
    }

	public int length() {
		return size;
	}

	public void append(E item) {
        tail = tail.setNext(new Link<> (item, null));
	}

}


class Solution {
    public int solution(int[] arr) {

        MyList<Integer> L = new LinkedList<>();

        for (int a : arr){
            L.append(a);
        }
        L.insert(0, 10);
        L.remove(1);

        int sum = 0;
        Link<Integer> n = ((LinkedList<Integer>) L).head().next();
        sum += n.item();
        
        while(n != null){
            sum += n.item();
            n = n.next();
        }

        return sum;
    }
}
