package avl;


public class AVL{
	
	public Node root;
	public String rotationType;
	public AVL() {
		root = null;
	}
	
	
	public Node getNode(int key) {
		Node ret = new Node(null, key, 0, key*2, null);
		return ret;
	}
	public void insertBST(int key) {
		//삽입 위치 찾기
		Node p = root;
		Node q = null;
		
		while(p !=null) {
			if (key == p.key) {return;}
			q = p;
			if (key > p.key) {
				p = p.right;
			}
			else {
				p = p.left;
			}
		}

		//노드 생성
		Node newNode = getNode(key);
		//노드 삽입
		if (root == null) root = newNode;
		else if (key < q.key) {
			q.left = newNode;
		}
		else {
			q.right = newNode;
		}

		return;
	}
	public Node findparent(int key) {
		Node q;
		Node p = root;
		q = p;
		while(p != null) {
			if(key < p.key) {
				q = p;
				p = p.left;
				
			}
			else if (key > p.key) {
				q = p;
				p = p.right;
			}
			else {
				return q;
			}
		}
		return q;
		
	}
	
	
	//p : 발생 노드 q : 부모 노드 
	//p,q, rotationType를 담는 클래스를 만들어주고 반환하였다.
	//루트부터 삽입한 노드까지 BF를 계산한후에 BF 절댓값이 2이상이되면
	//ret에 p,q, rotationType를 담아두었다.
	
	public Check checkBalance(int key) {
		Check ret = new Check("NO");
		if(getHeight(root)<2) {
			return ret;
		}
		
		//newKey부터 루트까지 BF를 다시 계산함
		Node rt = root;
		while(rt != null) {
			rt.BF = getHeight(rt.left) - getHeight(rt.right);
			if(rt.key > key) rt = rt.left;
			else rt = rt.right;
		}
		
		//return 값 구하기 불균형 노드 p, 부모노두 q
		Node rt2 = root;
		boolean balance = true;
		while(rt2 != null) {
			if(rt2.BF > 1 || rt2.BF < -1) {
				ret.p = rt2;
				balance = false;
				if(getHeight(rt2.left) > getHeight(rt2.right)) {
					if(getHeight(rt2.left.left) > getHeight(rt2.left.right)) {
						ret.rotationType = "LL";
					}
					else {
						ret.rotationType = "LR";
					}
				}
				else {
					if(getHeight(rt2.right.left) > getHeight(rt2.right.right)) {
						ret.rotationType = "RL";
					}
					else {
						ret.rotationType = "RR";
					}
				}
			}
			
			if(rt2.key > key) {
				rt2 = rt2.left;
			}
			else {
				rt2 = rt2.right;
			}
		}
		
		if(balance == false) ret.q = findparent(ret.p.key);
		else ret.q = findparent(key);
		return ret;

	}
	
	
	public void rotateTree(String rotationType, Node p, Node q) {
		if(q == null || rotationType.equals("NO")) return;
		Node a = null;
		Node b = null;
		//LL
		if (rotationType.equals("LL")) {
			a = p.left;
			b = a.left;
			p.left = a.right;
			a.right=p;
			a.left = b;
			
			//BF값 수정
			p.BF = getHeight(p.left) - getHeight(p.right);
			a.BF = getHeight(a.left) - getHeight(a.right);
			b.BF = getHeight(b.left) - getHeight(b.right);
			
			if(p == root) root = a;
			else if(q.left.key == p.key) {
				q.left = a;
			}
			else {
				q.right = a;
			}
			q.BF = getHeight(q.left) - getHeight(q.right);
		}
		//RR
		else if (rotationType.equals("RR")) {
			a = p.right;
			b = a.right;
			p.right = a.left;
			a.left= p;
			a.right = b;
			
			p.BF = getHeight(p.left) - getHeight(p.right);
			a.BF = getHeight(a.left) - getHeight(a.right);
			b.BF = getHeight(b.left) - getHeight(b.right);
			
			if(p == root) root = a;
			else if(q.left.key == p.key) {
				q.left = a;
			}
			else {
				q.right = a;
			}
			q.BF = getHeight(q.left) - getHeight(q.right);
		}
		//LR
		else if (rotationType.equals("LR")) {
			a = p.left;
			b = a.right;
			p.left = b.right;
			a.right = b.left;
			b.left = a;
			b.right = p;
			
			//BF값 수정
			
			p.BF = getHeight(p.left) - getHeight(p.right);
			a.BF = getHeight(a.left) - getHeight(a.right);
			b.BF = getHeight(b.left) - getHeight(b.right);
			
			
			if(p == root) root = b;
			else if(q.left.key == p.key) {
				q.left = b;
			}
			else {
				q.right = b;
			}
			q.BF = getHeight(q.left) - getHeight(q.right);
		}
		//RL
		else if (rotationType.equals("RL")) {
			a = p.right;
			b = a.left;
			p.right = b.left;
			a.left = b.right;
			b.left = p;
			b.right = a;
			
			//BF값 수정
			
			p.BF = getHeight(p.left) - getHeight(p.right);
			a.BF = getHeight(a.left) - getHeight(a.right);
			b.BF = getHeight(b.left) - getHeight(b.right);
			
			if(p == root) root = b;
			else if(q.left.key == p.key) {
				q.left = b;
			}
			else {
				q.right = b;
			}
			q.BF = getHeight(q.left) - getHeight(q.right);
		}
		else {
			q.BF = getHeight(q.left) - getHeight(q.right);
			return;
		}
	}
	public void insertAVL(int key) {
		//삽입
		insertBST(key);
		
		//불균형 체크
		Check tmp = checkBalance(key);
		this.rotationType = tmp.rotationType;
		
		//회전
		rotateTree(tmp.rotationType, tmp.p, tmp.q);		
		
	}
	
	
	//높이
	public int getHeight(Node rt) {
		if(rt == null) return 0;
		int height = 0;
		if(rt != null) {
			height = 1 + (getHeight(rt.left) >= getHeight(rt.right) ? getHeight(rt.left) : getHeight(rt.right)); 
		}
		return height;
	}
	
	//node 수
	public int noNodes(Node rt) {
		if(rt == null) return 0;
		int count = 0;
		if(root != null) {
			count = 1 + noNodes(rt.left) + noNodes(rt.right);
		}
		return count;
	}
	
	//max node
	public Node maxNode(Node rt) {
		Node ret = rt;
		while(ret.right != null) {
			ret = ret.right;
		}
		return ret;
	}
	
	//min node
	public Node minNode(Node rt) {
		Node ret = rt;
		while(ret.left != null) {
			ret = ret.left;
		}
		return ret;
	}
	
	public void deleteBST(int key) {
		Node erase = this.root; //지워야하는 노드
		Node parent = null; //부모노드
		//노드 찾기
		//key값이 없는경우
		if(searchBST(key) == false) {
			return;
		}
		parent = erase;
		while(erase.key != key) {
			parent = erase;
			if(erase.key > key) {
				erase = erase.left;
			}
			else {
				erase = erase.right;
			}
			
		}
		//자식이 없을 경우
		if(erase.left == null && erase.right == null) {
			//지우는게 루트인경우
			if(erase == root) {
				root = null;
			}
			if(parent.left == erase) parent.left = null;
			else parent.right = null;
		}
		//자식이 1개 있을 경우
		else if(erase.left != null && erase.right == null) {
			if(erase == root) {
				root = erase.left;
			}
			else if(parent.left == erase) {
				parent.left = erase.left;
			}
			else {
				parent.right = erase.left;
			}
		}
		else if(erase.left == null && erase.right != null) {
			if(erase == root) {
				root = erase.right;
			}
			else if(parent.left == erase) {
				parent.left = erase.right;
			}
			else {
				parent.right = erase.right;
			}
		}
		//자식이 2개 있을 경우
		else {
			Node ch = erase;
		//왼쪽 높이 > 오른쪽 높이
			if (getHeight(erase.left)>getHeight(erase.right)) {
				ch = maxNode(erase.left);
				Node up = findparent(ch.key);
				if(erase == root) {
					up.right = ch.left;
					ch.left = root.left;
					ch.right = root.right;
					root = ch;
				}
				else if(parent.right == erase) {
					if(up == erase) {
						ch.right = erase.right;
						parent.right = ch;
					}
					else {
						up.right = ch.left;
						ch.right = erase.right;
						ch.left = erase.left;
						parent.right = ch;
					}
				}
				else {
					if(up == erase) {
						ch.right = erase.right;
						parent.left = ch;
					}
					else {
						up.right = ch.left;
						ch.right = erase.right;
						ch.left = erase.left;
						parent.left = ch;
					}
				}
			}
			//오른쪽 높이 > 왼쪽 높이
			else if (getHeight(erase.left)<getHeight(erase.right)) {
				ch = minNode(erase.right);
				Node up = findparent(ch.key);
				if(erase == root) {
					up.left = ch.right;
					ch.left = root.left;
					ch.right = root.right;
					root =ch;
				}
				else if(parent.right == erase) {
					if(up == erase) {
						ch.left = erase.left;
						parent.right = ch;
					}
					else {
						up.left = ch.right;
						ch.left = erase.left;
						ch.right = erase.right;
						parent.right = ch;
					}
				}
				else {
					if(up == erase) {
						ch.left = erase.left;
						parent.left = ch;
					}
					else {
						up.left = ch.right;
						ch.left = erase.left;
						ch.right = erase.right;
						parent.left = ch;
					}
				}
			}
			else {
				//노드 수 비교
				if(noNodes(erase.left) > noNodes(erase.right)) {
					ch = maxNode(erase.left);
					Node up = findparent(ch.key);
					if(erase == root) {
						up.right = ch.left;
						ch.left = root.left;
						ch.right = root.right;
						root = ch;
					}
					else if(parent.right == erase) {
						up.right = ch.left;
						ch.left = erase.left;
						ch.right = erase.right;
						parent.right = ch;
					}
					else {
						up.right = ch.left;
						ch.left = erase.left;
						ch.right = erase.right;
						parent.left = ch;
					}
				}
				else {	
					ch = minNode(erase.right);
					Node up = findparent(ch.key);
					if(erase == root) {
						up.left = ch.right;
						ch.left = root.left;
						ch.right = root.right;
						root =ch;
					}
					else if(parent.right == erase) {
						if(up == erase) {
							ch.left = erase.left;
							parent.right = ch;
						}
						else {
							up.left = ch.right;
							ch.left = erase.left;
							ch.right = erase.right;
							parent.right = ch;
						}
					}
					else {
						if(up == erase) {
							ch.left = erase.left;
							parent.left = ch;
						}
						else {
							up.left = ch.right;
							ch.left = erase.left;
							ch.right = erase.right;
							parent.left = ch;
						}
					}
				}
				
			}
		}
		
		
	}
	
	public void deleteAVL(int key) {
		this.deleteBST(key);
		if(root == null) {
			return;
		}
		Check tmp = checkBalance(key);
		this.rotationType = tmp.rotationType;
		rotateTree(tmp.rotationType, tmp.p, tmp.q);	

	}

	public boolean searchBST(int key) {
		Node q = null;
		Node p = root;
		while(p != null) {
			if(key == p.key) return true;
			q = p;
			if(key < p.key) p = p.left;
			else p = p.right;
		}
		return false;
	}
	
	public String inorder(Node rt) {
		String ret = "";
		if(rt == null) return ret;
		
		ret += inorder(rt.left);
		ret += rt.key + " ";
		ret += inorder(rt.right);
		
		return ret;
	}
	
	public void inorderBST() {
		String ret = inorder(root);
		System.out.println(ret);
	}

}

class Node{
	public int key;
	public int value;
	public int BF;
	public Node left;
	public Node right;
	
	public Node(Node left, int key, int BF, int value, Node right) {
		this.left = left;
		this.key = key;
		this.value = value;
		this.right = right;
		this.BF = BF;
	}
	
}

class Check{
	public String rotationType;
	public Node p;
	public Node q;
	
	public Check(String rotationType) {
		this.rotationType = rotationType;
	}
}


class Main{

	public static void main(String[] args) {
		
		AVL T = new AVL();
		T.root = null;
		
		T.insertAVL(40);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(11);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(77);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(33);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(20);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(90);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(99);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(70);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(88);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(80);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(66);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(10);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(22);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(30);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(44);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(55);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(50);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(60);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(25);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(49);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		

		T.deleteAVL(40);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(11);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(77);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(33);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(20);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(90);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(99);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(70);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(88);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(80);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(66);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(10);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(22);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(30);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(44);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(55);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(50);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(60);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(25);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(49);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		
		T.root = null;
		
		T.insertAVL(40);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(11);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(77);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(33);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(20);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(90);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(99);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(70);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(88);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(80);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(66);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(10);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(22);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(30);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(44);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(55);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(50);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(60);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(25);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.insertAVL(49);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		
		

		T.deleteAVL(49);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(25);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(60);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(50);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(55);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(44);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(30);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(22);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(10);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(66);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(80);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(88);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(70);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(99);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(90);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(20);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(33);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(77);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(11);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		T.deleteAVL(40);
		System.out.print(T.rotationType+" ");
		T.inorderBST();
		
		
	}

}
