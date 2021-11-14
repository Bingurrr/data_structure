package bst;


public class BST{
	
	public Node root;
		
	
	public Node getNode(int key) {
		Node ret = new Node(null, key, key*2, null);
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
	
	public Node(Node left, int key, int value, Node right) {
		this.left = left;
		this.key = key;
		this.value = value;
		this.right = right;
	}
	
}



class Main{
	
	public static void main(String[] args) {
		
		BST T = new BST();
		T.root = null;
		
		T.insertBST(25);
		T.inorderBST();
		T.insertBST(500);
		T.inorderBST();
		T.insertBST(33);
		T.inorderBST();
		T.insertBST(49);
		T.inorderBST();
		T.insertBST(17);
		T.inorderBST();
		T.insertBST(403);
		T.inorderBST();
		T.insertBST(29);
		T.inorderBST();
		T.insertBST(105);
		T.inorderBST();
		T.insertBST(39);
		T.inorderBST();
		T.insertBST(66);
		T.inorderBST();
		T.insertBST(305);
		T.inorderBST();
		T.insertBST(44);
		T.inorderBST();
		T.insertBST(19);
		T.inorderBST();
		T.insertBST(441);
		T.inorderBST();
		T.insertBST(390);
		T.inorderBST();
		T.insertBST(12);
		T.inorderBST();
		T.insertBST(81);
		T.inorderBST();
		T.insertBST(50);
		T.inorderBST();
		T.insertBST(100);
		T.inorderBST();
		T.insertBST(999);
		T.inorderBST();
		
		T.deleteBST(25);
		T.inorderBST();
		T.deleteBST(500);
		T.inorderBST();
		T.deleteBST(33);
		T.inorderBST();
		T.deleteBST(49);
		T.inorderBST();
		T.deleteBST(17);
		T.inorderBST();
		T.deleteBST(403);
		T.inorderBST();
		T.deleteBST(29);
		T.inorderBST();
		T.deleteBST(105);
		T.inorderBST();
		T.deleteBST(39);
		T.inorderBST();
		T.deleteBST(66);
		T.inorderBST();
		T.deleteBST(305);
		T.inorderBST();
		T.deleteBST(44);
		T.inorderBST();
		T.deleteBST(19);
		T.inorderBST();
		T.deleteBST(441);
		T.inorderBST();
		T.deleteBST(390);
		T.inorderBST();
		T.deleteBST(12);
		T.inorderBST();
		T.deleteBST(81);
		T.inorderBST();
		T.deleteBST(50);
		T.inorderBST();
		T.deleteBST(100);
		T.inorderBST();
		T.deleteBST(999);
		T.inorderBST();
		
		T.root = null;
		
		T.insertBST(25);
		T.inorderBST();
		T.insertBST(500);
		T.inorderBST();
		T.insertBST(33);
		T.inorderBST();
		T.insertBST(49);
		T.inorderBST();
		T.insertBST(17);
		T.inorderBST();
		T.insertBST(403);
		T.inorderBST();
		T.insertBST(29);
		T.inorderBST();
		T.insertBST(105);
		T.inorderBST();
		T.insertBST(39);
		T.inorderBST();
		T.insertBST(66);
		T.inorderBST();
		T.insertBST(305);
		T.inorderBST();
		T.insertBST(44);
		T.inorderBST();
		T.insertBST(19);
		T.inorderBST();
		T.insertBST(441);
		T.inorderBST();
		T.insertBST(390);
		T.inorderBST();
		T.insertBST(12);
		T.inorderBST();
		T.insertBST(81);
		T.inorderBST();
		T.insertBST(50);
		T.inorderBST();
		T.insertBST(100);
		T.inorderBST();
		T.insertBST(999);
		T.inorderBST();
		
		T.deleteBST(999);
		T.inorderBST();
		T.deleteBST(100);
		T.inorderBST();
		T.deleteBST(50);
		T.inorderBST();
		T.deleteBST(81);
		T.inorderBST();
		T.deleteBST(12);
		T.inorderBST();
		T.deleteBST(390);
		T.inorderBST();
		T.deleteBST(441);
		T.inorderBST();
		T.deleteBST(19);
		T.inorderBST();
		T.deleteBST(44);
		T.inorderBST();
		T.deleteBST(305);
		T.inorderBST();
		T.deleteBST(66);
		T.inorderBST();
		T.deleteBST(39);
		T.inorderBST();
		T.deleteBST(105);
		T.inorderBST();
		T.deleteBST(29);
		T.inorderBST();
		T.deleteBST(403);
		T.inorderBST();
		T.deleteBST(17);
		T.inorderBST();
		T.deleteBST(49);
		T.inorderBST();
		T.deleteBST(33);
		T.inorderBST();
		T.deleteBST(500);
		T.inorderBST();
		T.deleteBST(25);
		T.inorderBST();
		
	}

}
