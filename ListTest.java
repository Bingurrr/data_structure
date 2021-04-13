package lists;

public class ListTest {
	public static void main(String[] args) {
		List<Integer>mylist = new LinkedList<Integer>();
		mylist.append(2);
		mylist.append(3);
		mylist.append(8);
		mylist.append(7);
		mylist.append(5);
		mylist.insert(2,99);
		
		
		System.out.println("Iterate using 'for loop'");
		for(int i = 0; i < mylist.length(); i ++) 
			System.out.print(mylist.getvalue(i)+ " ");
		System.out.println();
		
		System.out.println("Iterate using 'listIterator'");
		ListIterator<Integer> itr = mylist.listIterator();
		while(itr.hasNext()) {
			System.out.print(itr.next() + " ");
		}
		System.out.println();
		
		
		mylist.remove(3);
		mylist.update(1, 12345);
		System.out.println();
	
		for(int i = 0; i < mylist.length(); i ++) {
			System.out.print(mylist.getvalue(i)+ " ");
		}
		System.out.println();
		System.out.println(mylist.length());
		
	}
	
	

}
