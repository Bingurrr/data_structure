import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


class Solution {
	public static void main(String[] args) throws IOException {
		
		Dictionary<Integer, String> dict = new BST<>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String line;
		while((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			String operation = st.nextToken().strip();
			
			int k;
			String v;
			switch(operation) {
				case "insert":
					k = Integer.parseInt(st.nextToken());
					v = st.nextToken().strip();
					dict.insert(k, v);
					break;
				case "remove":
					k = Integer.parseInt(st.nextToken());
					v = dict.remove(k);
					if(v != null)
						System.out.println("Removed value: " + v);
					break;
				case "find":
					k = Integer.parseInt(st.nextToken());
					v = dict.find(k);
					if(v != null)
						System.out.println("Found value: " + v);
					break;
				case "size":
					System.out.println("Dict size: " + dict.size());
					break;
				case "clear":
					System.out.println(inorder(((BST<Integer, String>) dict).root));
					System.out.println(preorder(((BST<Integer, String>) dict).root));
					dict.clear();
					System.out.println("Dict is initialized.");
					break;
			}	
		}
		
		br.close();
	
	}
	
	public static <K, V> String inorder(BinNode<K, V> rt){
		String ret = "";
		
		if(rt == null) return ret;
		
		ret += inorder(rt.left);
		ret += rt.key + ": " + rt.value + "\n";
		ret += inorder(rt.right);
		
		return ret;
	}
	
	public static <K, V> String preorder(BinNode<K, V> rt){
		String ret = "";
		
		if(rt == null) return ret;
		
		ret += rt.key + ": " + rt.value + "\n";
		ret += inorder(rt.left);
		ret += inorder(rt.right);
		
		return ret;
	}
	
}

interface Dictionary<K, V>{
	public void clear();
	public void insert(K k, V v);
	public V remove(K k);
	public V find(K k);
	public int size(); 
}

class BinNode<K, V> {
	public K key;
	public V value;
	public BinNode<K, V> left;
	public BinNode<K, V> right;
	public BinNode(K key, V value, BinNode<K, V> left, BinNode<K, V> right){
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}
}

class BST<K extends Comparable<K>, V> implements Dictionary<K, V>{

	BinNode<K, V> root;
	int size;
	
	public BST() {
		size = 0;
	}
	
	public V find(K k) {
		return find_helper(k, root);
	}
	
	private V find_helper(K k, BinNode<K, V> rt) {
		if(rt == null) {
			return null;
		}
		else {
			if(k.compareTo(rt.key) < 0)
				return find_helper(k, rt.left);
			else if(k.compareTo(rt.key) > 0)
				return find_helper(k, rt.right);
			else
				return rt.value;
		}
	}
	@Override
	public void clear() {
		size = 0;
		root = null; 
	}

	@Override
	public void insert(K k, V v){
        if(root == null){
            root = new BinNode<K, V>(k, v, null, null);
            size++;
            return;
        }
        else{
        	size++;
            BinNode<K, V> ret = root;
            BinNode<K, V> tmp;
            while(true){
                tmp = ret;
                if((ret.key).compareTo(k) > 0){
                    ret = ret.left;
                    if(ret == null){
                        tmp.left = new BinNode<K, V>(k, v, null, null);
                        break;
                    }
                }
                else if((ret.key).compareTo(k) < 0){
                    ret = ret.right;
                    if(ret == null){
                        tmp.right = new BinNode<K, V>(k, v, null, null);
                        break;
                    }
                }
                else {
                    tmp.value = v;
                    size--;
                    break;
                }
                
            }
        }
            
    }
	
	@Override
	public V remove(K k) {
		if(find(k) == null) {
			return null;
		}
		size--;
		V ret = find(k);
		BinNode<K, V> parent, erase;
		parent = erase = root;
		int direction=2;
		//지워야하는 node와 node의 부모노드 찾기
		while(erase.key.compareTo(k) != 0) {
			parent = erase;
			if(erase.key.compareTo(k) > 0) {
				erase = erase.left;
				direction = 0;
			}
			else {
				erase = erase.right;
				direction = 1;
			}
		}
		//루트 노드를 지울 경우
		if(direction == 2) {
			//자식이 없을 경우 0
			if(size == 1) {
				root = null;
			}
			//왼쪽 자식만 있을 경우 0
			else if(root.left != null && root.right == null) {
				erase = root.left;
				root = erase;
			} 
			//오른쪽 자식만 있을 경우 0
			else if(root.left == null && root.right != null) {
				erase = root.right;
				root = erase;
			}
			//자식이 두 곳 다 있을 경우(오른쪽에서 최솟값) 0
			else {
				BinNode<K, V> up_node = root.right;
				while(up_node.left != null) {
					parent = up_node;
					up_node = up_node.left;
				}
				if(up_node.right != null) {
					if(parent.key.compareTo(up_node.right.key) > 0) {
						parent.left = up_node.right;
					}
					else {
						parent.right = up_node.right;
					}
				}
				else if(up_node.right == null) {
					parent.left = null;
				}
				root.key = up_node.key;
				root.value = up_node.value;
			}
		}
		//단일 노드 일 굥우 0
		else if(erase.left == null && erase.right == null ) {
			if(direction==0) {parent.left = null; }
			else {parent.right = null;}
		}
		//삭제노드가 왼쪽 자식만 있을 경우 0
		else if (erase.left != null && erase.right == null) {
			if(direction==0) {parent.left = erase.left; }
			else {parent.right = erase.left;}
		}
		//삭제노드가 오른쪽 자식만 있을 경우 0
		else if(erase.left == null && erase.right != null) {
			if(direction==0) {parent.left = erase.right; }
			else {parent.right = erase.right;}
		}
		//삭제노드의 자식노드가 2개 있을 경우
		else {
			BinNode<K, V> up_node = erase.right;
			BinNode<K, V> up_parent = erase.right;
			if(up_node.left == null) {
				erase.right = up_node.right;
				erase.key = up_node.key;
				erase.value = up_node.value;
			}
			else {
				while(up_node.left != null) {
					up_parent = up_node;
					up_node = up_node.left;
				}
				if(up_node.right == null) {
					up_parent.left = null;
				}
				else {
					up_parent.left = up_node.right;
				}
	
				erase.key = up_node.key;
				erase.value = up_node.value;		
			}
			
		}
		return ret;
	}
	@Override
	public int size() {
		return size;
	}
}
