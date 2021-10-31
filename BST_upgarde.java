package bst;

public class BST{
	public BST() {
		root = null;
	}
	public Node root;
	public Node getNode(int key) {
		Node ret = new Node(null, key, null);
		return ret;
	}
	public void insertBST(int key) {
		//삽입 위치 찾기
		Node p = root;
		Node q = null;
		boolean already_in = false;
		while(p !=null) {
			if (key == p.key) {already_in = true; return;}
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
		else if (key < q.key) q.left = newNode;
		else q.right = newNode;
		return;
	}
	//높이
	public int getHeight(Node rt) {
		int height = 0;
		if(rt != null) {
			height = 1 + (getHeight(rt.left) > getHeight(rt.right) ? getHeight(rt.left) : getHeight(rt.right)); 
		}
		return height;
	}
	//node 수
	public int noNodes(Node rt) {
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
			if(key == root.key) root = null;
			if(parent.left == erase) parent.left = null;
			else parent.right = null;
		}
		//자식이 1개 있을 경우
		else if(erase.left != null && erase.right == null) {
			if(parent.left == erase) {
				parent.left = erase.left;
			}
			else {
				parent.right = erase.left;
			}
		}
		else if(erase.left == null && erase.right != null) {
			if(parent.left == erase) {
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
				Node up = erase.left;
				while(up.right.right != null || up.right != null) {
					up = up.right;
				}
				ch = maxNode(erase.left);
				up.right = ch.left;
				if(parent.right == erase) {
					parent.right = ch;
				}
				else {
					parent.left = ch;
				}
			}
			else if (getHeight(erase.left)<getHeight(erase.right)) {
				Node up = erase.right;
				while(up.left.left != null || up.left != null) {
					up = up.left;
				}
				ch = minNode(erase.right);
				up.left = ch.right;
				if(parent.right == erase) {
					parent.right = ch;
				}
				else {
					parent.left = ch;
				}
			}
			else {
				//노드 수 비교
				if(noNodes(erase.left) > noNodes(erase.right)) {
					Node up = erase.left;
					while(up.right.right != null || up.right != null) {
						up = up.right;
					}
					ch = maxNode(erase.left);
					up.right = ch.left;
					if(parent.right == erase) {
						parent.right = ch;
					}
					else {
						parent.left = ch;
					}
				}
				else {
					Node up = erase.right;
					while(up.left.left != null || up.left != null) {
						up = up.left;
					}
					ch = minNode(erase.right);
					up.left = ch.right;
					if(parent.right == erase) {
						parent.right = ch;
					}
					else {
						parent.left = ch;
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
	public Node left;
	public Node right;
	
	public Node(Node left, int key, Node right) {
		this.left = left;
		this.key = key;
		this.right = right;
	}
	
}
