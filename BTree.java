import java.util.Stack;

public class BTree {
	public BTNode root;
	public int m;
	
	public BTree(int m) {
		this.root = null;
		this.m = m;
	}
	
	public BTree(BTNode n) {
		this.root = n;
	}
	
	public void inorderBT(BTree t) {
		BTNode n= t.root;
		if (t.root==null) return;
		
		if(n.childs[0]==null) {
			for(int i = 0; i < n.numKey; i++) 
				System.out.print(n.keys[i]+" ");
		}
		else {
			for(int i = 0 ;i <= n.numKey; i++) {
				inorderBT(new BTree(n.childs[i]));
				if(i<n.numKey)
					System.out.print(n.keys[i]+" ");
			}
		}
	}	
	
	public void insertBT(int key) {
		int m = this.m;
		BTree t = this;
		
		if(t.root == null) {
			BTNode node = new BTNode(m);
			t.root = node;
			node.keys[0] =  key;
			node.numKey++;
			return;
		}
		
		BTNode tmp = t.root;
		Stack<BTNode> st = new Stack<BTNode>();
		do {
			st.push(tmp);	
			if(key > tmp.keys[tmp.numKey-1]) {
				tmp = tmp.childs[tmp.numKey];
			}
			else {
				for(int i=0;i<tmp.numKey;i++) {
					if(key == tmp.keys[i]) {
						return;
					}
					if(key<tmp.keys[i]) {
						tmp = tmp.childs[i];
						break;	
					}
				}
			}
		}while(tmp != null);
		BTNode s = null; 
		BTNode parent;	   
		int middleKey = 0;
		do {
			tmp =st.pop();
			parent = st.isEmpty() ? null : st.peek();
			if((middleKey = this.insertKey(tmp, m, key)) == 0) {
				if(tmp.childs[0] != null) {
					for(int i = 0; i < tmp.numKey; i++) {
						if(key == tmp.keys[i]) {
							tmp.childs[i+1] = s;
						}
					}
				}
				return;
			}
			else {
				if(tmp.childs[0] != null) {
					for(int i = 0;i < tmp.numKey; i++) {
						if(key == tmp.keys[i]) {
							if(s.keys[0] < key) {
								tmp.childs[i] = s;
								break;
							}
							else {
								tmp.childs[i+1] = s;
							}
						}
					}
				}
				key = middleKey;
				//split
				BTNode newNode = new BTNode(m);
				for(int i = 0; i < m; i++) {
					if(middleKey == tmp.keys[i]) {
						newNode.makeChild(0,m);
						newNode.childs[0] = tmp.childs[i+1];
				
						for(int j = 0; j < m-1 - m/2; j++) {
							newNode.keys[j] = tmp.keys[i+1+j];
							newNode.makeChild(j+1,m);
							newNode.childs[j+1] = tmp.childs[i+2+j];
							newNode.numKey++;
						}
						break;
					}
				}
				s = newNode;
				for(int k = tmp.numKey-1; tmp.keys[k] >= middleKey;k--) {
				tmp.keys[k] = 0;
				tmp.childs[k+1] = null;
				tmp.numKey--;
				}
				if(parent==null) {					
					BTNode rt = new BTNode(m);
					rt.keys[0] = key;
					rt.numKey++;
					rt.makeChild(0,m);
					rt.makeChild(1,m);
					rt.childs[0] = tmp;
					rt.childs[1] = s;
					t.root = rt;
					return;
				}
			}
		} while(!st.isEmpty());
	}
	public int insertKey(BTNode n, int m, int key){
		if(!n.isfull(m)) {
			for(int i = 0; i < n.numKey; i++) {
				//삽입위치
				if(key < n.keys[i]) {
					for(int j = n.numKey; j > i; j--) {
						n.keys[j] = n.keys[j-1];
						if(!n.haschild()) 
							n.childs[j+1] = n.childs[j];
					}
					n.keys[i] = key;
					n.numKey++;
					return 0;
				}	
			}
			n.keys[n.numKey] = key;
			n.numKey++;
			return 0;
		}
		if(key>n.getmid()) {
			for(int i=m/2;i<n.numKey;i++) {
				if(key<n.keys[i]) {
					for(int j=n.numKey;j>i;j--) {
						n.keys[j] = n.keys[j-1];
						if(!n.haschild()) n.childs[j+1] = n.childs[j];
					}
					n.keys[i] = key;
					n.numKey++;					
					return n.getmid();
				}
			}
			n.keys[n.numKey] = key;
			n.numKey++;
			return n.getmid();
		}
		else if(key<n.getmid()) {
			for(int i = 0; i <= m/2; i++) {
				if(key < n.keys[i]) {
					for(int j=n.numKey; j > i; j--){
						n.keys[j] =  n.keys[j-1];
						if(!n.haschild()) {
							n.childs[j+1] = n.childs[j];;
						}
					}
					n.keys[i] = key;
					n.numKey++;
					return n.getmid();
				}
			}
			n.keys[m/2] = key;
			n.numKey++;
			return n.getmid();
		}
		else {
			for(int i = 0; i < n.numKey; i++) {
			}
			for(int i = n.numKey; n.keys[i-1] > key; i--)	{
				n.keys[i] = n.keys[i-1];
				n.childs[i+1] = n.childs[i];
			}
			n.keys[n.numKey/2] = key;
		}
		n.numKey++;
		return n.getmid();
	}

	public void deleteBT(BTree t, int m, int oldKey) {
		BTNode tmp=t.root;
		if(tmp==null) {
			return;
		}
		Stack<Integer> num = new Stack<Integer>();
		Stack<BTNode> st = new Stack<BTNode>();
		//삭제할 키 찾기.
		int oldPos = 0;
		st.push(tmp);
		boolean findOldKey = false;
		do {
			if(oldKey > tmp.keys[tmp.numKey-1]) {
				num.push(tmp.numKey);
				tmp=tmp.childs[tmp.numKey];
				st.push(tmp);
			}
			else {
				for(int i = 0; i < tmp.numKey; i++) {			
					if(oldKey == tmp.keys[i]) {
						oldPos=i;	
						findOldKey=true;
						break;
					}
					else if(oldKey < tmp.keys[i]) {
						tmp=tmp.childs[i];
						st.push(tmp);
						num.push(i);
						break;
					}
				}
			}
		}while(tmp != null && findOldKey == false);
		//키 없을 떄
		if(tmp == null) {
			return;
		}
		BTNode leaf=tmp; 
		BTNode old = tmp; 
		//내부에 있는 경우
		int l=0;
		if(tmp.childs[0] != null) {
			l = oldPos;	
			st.push(tmp=tmp.childs[l+1]);
			num.push(l+1);		
			
			while(tmp.childs[0] != null) {
				st.push(tmp=tmp.childs[0]);
				num.push(0);
			}
			
			leaf=st.peek();
			l=num.peek();
			l = 0;

			int ch = leaf.keys[0]; 
			old.keys[oldPos] =ch;
		}
		//단말에 있는 경우.
		else l = oldPos;
		for(int i = l; i<leaf.numKey; i++) {
			leaf.keys[i] = leaf.keys[i+1];
		}
		leaf.numKey--;
		do{
			if(tmp == t.root || !(tmp.numKey < (m-1)/2)) {
				return;
			}
			tmp=st.pop();
			BTNode parent = st.peek();
			int nodePos=num.pop();
			int bestSiblingPos = getPosSibling(parent,tmp,m,nodePos);
			if(bestSiblingPos != 0){
				if(bestSiblingPos ==-1) {
					for(int i = tmp.numKey-1; i>0; i--) {
						tmp.keys[i+1] = tmp.keys[i];
						tmp.childs[i+2] = tmp.childs[i+1];
					}
					tmp.childs[1] = tmp.childs[0];
					tmp.keys[0] = parent.keys[nodePos-1];
					tmp.numKey++;
					BTNode sibling=parent.childs[nodePos-1];
				
					
					parent.keys[nodePos-1] = sibling.keys[sibling.numKey-1];
					tmp.childs[0] = sibling.childs[sibling.numKey];
					sibling.numKey--;
				}
				//오른쪽
				else {
					BTNode sibling=parent.childs[nodePos+1];

					tmp.keys[tmp.numKey] = parent.keys[nodePos];
					tmp.numKey++;
					tmp.childs[tmp.numKey] = sibling.childs[0];
					parent.keys[nodePos] = sibling.keys[0];
					for(int i=1;i<sibling.numKey;i++) {
						sibling.keys[i-1] = sibling.keys[i];
						sibling.childs[i-1] = sibling.childs[i];
					}
					sibling.childs[sibling.numKey-1] = sibling.childs[sibling.numKey];
					sibling.numKey--;
				}
			}
			//합병
			else{
				merge(parent, tmp, m, nodePos);
			}
			
			if(parent == t.root && parent.numKey == 0) {
				if(parent.childs[0].numKey==0) t.root =parent.childs[1];
				else t.root = parent.childs[0];
				return;
			}
			
			tmp = parent;
		}while(true);		
	}
	
	//위치
	public int getPosSibling(BTNode parent, BTNode tmp, int m, int nodePos) {
		//오른쪽
		if(nodePos==0) {
			if(parent.childs[1].numKey >= ((m-1)/2)+1) return 1;
		}
		//왼쪽
		else if(nodePos == parent.numKey) {
			if(parent.childs[nodePos-1].numKey >= ((m-1)/2)+1) return -1;
		}
		else if(parent.childs[nodePos-1].numKey>=((m-1)/2)+1 || parent.childs[nodePos+1].numKey>=((m-1)/2)+1) {
			if(parent.childs[nodePos-1].numKey>=parent.childs[nodePos+1].numKey) return -1;
			else return 1;
		}
		return 0;
	}
	
	public int findmergeSibling(BTNode parent, BTNode tmp, int m, int nodePos) {
		if(nodePos == 0) return 1;
		else if(nodePos==parent.numKey) return -1;
		else {
			if(parent.childs[nodePos-1].numKey >= parent.childs[nodePos+1].numKey)
				return -1;
			else return 1;
		}
	}
	
	public void merge(BTNode parent,BTNode tmp, int m, int nodePos) {
		int parentKey=0;
		BTNode si = null;
		//왼
		if(findmergeSibling(parent, tmp, m, nodePos)<0) {
			parentKey = parent.keys[nodePos-1];
			si = parent.childs[nodePos-1];
			si.keys[si.numKey] = parentKey;
			si.numKey++;
			for(int i = 0; i < tmp.numKey; i++) {
				si.keys[i+si.numKey] = tmp.keys[i];
				si.childs[i+si.numKey] = tmp.childs[i];
				si.numKey++;
				tmp.numKey--;
			}
			si.childs[si.numKey] = tmp.childs[0];
			
			for(int i=nodePos;i<parent.numKey;i++) {
				parent.keys[i-1] = parent.keys[i];
				parent.childs[i] = parent.childs[i+1];
			}
			parent.numKey--;
			
		}
		//오
		else {
			parentKey=parent.keys[nodePos];
			si =parent.childs[nodePos+1];
			tmp.keys[tmp.numKey] = parentKey;
			tmp.numKey++;
			tmp.childs[tmp.numKey] = si.childs[0];
			for(int i=0; i < si.numKey; i++) {
				tmp.keys[tmp.numKey+i] = si.keys[i];
				tmp.childs[tmp.numKey+1] = si.childs[i];
				tmp.numKey++;
			}
			tmp.childs[tmp.numKey] = si.childs[si.numKey];
			for(int i = nodePos+1; i < parent.numKey;i++) {
				parent.keys[i-1] = parent.keys[i];
				parent.childs[i] = parent.childs[i+1];
			}
			parent.numKey--;
		}
	}
	
}


// node
class BTNode {
	public int keys[];
	public BTNode childs[];
	public int numKey;

	public BTNode(int m) {
		keys = new int[m]; 
		childs = new BTNode[m+1];
		numKey=0;	
	}
	public void makeChild(int i,int m) {
		this.childs[i] = new BTNode(m);
	}
	
	public int getmid() {
		return keys[numKey/2];
	}	
	
	public boolean isfull(int m) {
		if (numKey == m-1) return true;
		return false;
	}
	
	public boolean haschild() {
		if(numKey > 0) return false;
		return true;
	}
	
	public int findPos(int key) {
		int i;
		for(i=0; key != this.keys[i]; i++);
		return i;
	}
	
	public void copyChild(BTNode n) {
		for(int i = 0; i < n.numKey; i++) {
			this.keys[i] = n.keys[i];
			this.childs[i] = n.childs[i];
		}
		this.childs[n.numKey] = n.childs[n.numKey];
	}
}


class BTreeMain {
	public static void main(String[] args) {
		
		int [] insert_arr = {40, 11, 77, 33, 20, 90, 99, 70, 88, 80, 66, 10, 22, 30, 44, 55, 50,
						60, 100, 28, 18, 9 ,5, 17, 6, 3 ,1, 4, 2, 7, 8, 73, 12, 13, 14, 16, 15, 25, 24, 28,
						45, 49, 42, 43, 41, 47, 48, 46, 63, 68, 61, 62, 64, 69, 67, 65, 54, 59, 58, 51,
						53, 57, 52, 56, 83, 81, 82 ,84 ,75 ,89};
		int [] delete_arr = {66, 10, 22, 30, 44, 55, 50, 60 ,100, 28, 18, 9, 5, 17, 6 ,3, 1, 4, 2, 7, 8, 73, 12,
						13, 14, 16, 15, 25, 24, 28, 40, 11, 77, 33, 20, 90, 99, 70, 88, 80, 45, 49, 42, 43, 41, 47, 48, 46,
						63, 68, 53, 57, 52, 56, 83, 81, 82, 84, 75, 89, 61, 62, 64, 69, 67, 65, 54, 59, 58, 51};
		
		BTree t= new BTree(3);		
		int m = 3;
		for(int i = 0; i < insert_arr.length; i++) {
			t.insertBT(insert_arr[i]);
			t.inorderBT(t);
			System.out.println();
		}
		for(int i = 0; i<delete_arr.length; i++) {
			t.deleteBT(t,m,delete_arr[i]);
			t.inorderBT(t);
			System.out.println();
		}
		
		
		t = null;
		t = new BTree(4);		
		m = 4;
		for(int i = 0; i<insert_arr.length; i++) {
			t.insertBT(insert_arr[i]);
			t.inorderBT(t);
			System.out.println();
		}
		for(int i = 0; i < delete_arr.length; i++) {
			t.deleteBT(t,m,delete_arr[i]);
			t.inorderBT(t);
			System.out.println();
		}
		
	}
}

