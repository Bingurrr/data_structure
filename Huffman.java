import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        for(int i = 0; i < a; i++) {
            PriorityQueue<Node> hf = new Heap<Node>();
        	int b = sc.nextInt();
 
        	int weight = 0;
        	char [] ch = new char[b];
        	int[] feq = new int[b];
        	for(int j = 0; j < b; j++) {
        	 	ch[j] = sc.next().charAt(0);
        	 	feq[j] = sc.nextInt();
        	    Node tmp = new Node(feq[j], ch[j]);
        	    hf.insert(tmp);
        	}
            while(hf.size() >= 2){
                 Node n1 =hf.removeMin();
                 Node n2 =hf.removeMin();
                 int w = n1.value + n2.value;
                 Node tmp = new Node(n1, n2, w);
                 hf.insert(tmp);
                 if(hf.size() == 1) break;
             }
            Node root = hf.getMin();
            weight = inorder(root, 0);
            System.out.println(weight);
        }

    }
    public static int inorder(Node rt, int d){
		int depth = d;
		int weight = 0;
        if(rt == null) {
			return 0;
		}
		if(rt.leaf(rt)) weight = rt.value * depth;
        depth++;
		weight += inorder(rt.left, depth);
		weight += inorder(rt.right, depth);
		return weight;
	}
}


class Node implements Comparable<Node>{
	Node left;
	Node right;
	int value;
	char key;
	int depth;
	public Node(int value, char key) {
		this.left = null;
		this.right = null;
		this.value = value;
		this.key = key;
	}
    
    public Node(Node left, Node right, int value) {
		this.left = left;
		this.right = right;
		this.value = value;
	}
    
	public Node(Node left, Node right, int value, char key) {
		this.left = left;
		this.right = right;
		this.value = value;
		this.key = key;
	}
	public int compareTo(Node node) {
		if(value > node.value) {
			return 1;
		}
		else if(value == node.value) {
			return 0;
		}
		return -1;
	}
	public boolean leaf(Node root) {
		return (root.left == null && root.right == null);
	}
	
}


interface PriorityQueue<Node>{
	public void insert(Node e);
	public Node removeMin();
	public Node getMin();
	public int size();
	public void clear();
}

class Heap<Node extends Comparable<Node>> implements PriorityQueue<Node>{

	final int DEFAULT_CAPACITY = 10;
	Node[] data;
	int size;
	
	private int parent(int pos) {
		return (pos-1)/2;
	}
	
	private int leftchild(int pos) {
		return pos * 2 + 1;
	}
	
	private int rightchild(int pos) {
		return pos * 2 + 2;
	}
	
	private boolean isleaf(int pos) {
		return pos * 2 + 1 >= size && pos < size;
	}
	
	public Heap(Node[] data) {
		size = data.length;
		for(int i=size/2-1; i>=0; i--) {
			siftdown(i);
		}
	}
	
	public void siftdown(int pos) {
		if(isleaf(pos)||pos >=size) return;
		Node parent = data[pos];
		if(data[rightchild(pos)] == null) {
			if(data[leftchild(pos)].compareTo(parent) <= 0) {
				data[pos] = data[leftchild(pos)];
				data[leftchild(pos)] = parent;
				pos = leftchild(pos);
				siftdown(pos);
			}
		}
		else {
			if(data[leftchild(pos)].compareTo(parent) < 0 && data[leftchild(pos)].compareTo(data[rightchild(pos)]) <= 0) {
				data[pos] = data[leftchild(pos)];
				data[leftchild(pos)] = parent;
				pos = leftchild(pos);
				siftdown(pos);
			}
			else if(data[rightchild(pos)].compareTo(parent) < 0 && data[rightchild(pos)].compareTo(data[leftchild(pos)]) <= 0) {
				data[pos] = data[rightchild(pos)];
				data[rightchild(pos)] = parent;
				pos = rightchild(pos);
				siftdown(pos);
			}	
		}
		return;
	}
	public Heap() {
		data = (Node[]) new Comparable[DEFAULT_CAPACITY];
		size = 0;
	}
	
	public String toString() {
		return Arrays.toString(Arrays.copyOf(data, size)); 
	}

	public void insert(Node e) {
		if(size == data.length) {
			data = Arrays.copyOf(data, data.length*2);
		}
		data[size] = e;
		int num = size;
		size++;
		while(data[num].compareTo(data[parent(num)]) < 0) {
			data[num] = data[parent(num)]; 
			data[parent(num)] = e;
			num = parent(num);
			if(num == 0) break;
		}
	}

	public Node removeMin() {
		if(size==0) {return null;}
		Node ret = data[0];
		data[0] = data[size-1];
		data[size-1] = null;
		size--;
		siftdown(0);
		return ret;
	}


	public Node getMin() {
		if(size == 0) return null;
		return data[0];
	}

	public int size() {
		return size;
	}
  

	public void clear() {
        data = (Node[]) new Comparable[DEFAULT_CAPACITY];
		size = 0;
	}

	
}
