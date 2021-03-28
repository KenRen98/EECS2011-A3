import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

public class A3BSTree  <E extends Comparable <? super E>> implements Tree<E>{

	static class Node<E>{
		E data;
		Node<E> L;
		Node<E> R;

		public Node(E e)
		{
			this.data=e;
		}
	}

	private Node<E> root;
	private int size;

	public A3BSTree(){
		this.root=null;
		size=0;
	}

	@Override
	public boolean add(E e) {
		Node<E> current = new Node<>(e);

		//Null e
		if(e == null){
			System.out.println(e+" , null value can't be added.");
			return false;
		}
		//Empty Tree
		else if(root==null){
			root = current;
			size++;
			return true;
		}
		//All other cases
		else{
			Node<E> PRlocation = this.locator(e);
			if(current.data.compareTo(PRlocation.data)==0){
				System.out.println(e+" exist.");
				return false;
			}
			else if(current.data.compareTo(PRlocation.data)>0){
			PRlocation.R = current;
			size++;
			return true;
			}
			else{
			PRlocation.L = current;
			size++;
			return true;
			}
		}
	}

	//Return the parent for e or existing Node of data e
	private Node<E> locator(E e){
		Node<E> current = this.root;
		while(true){
			if(e.compareTo(current.data)==0){
				return current;
			}
			else if(e.compareTo(current.data)>0){
				if(current.R==null){
					return current;
				}
				current = current.R;
			}
			else{
				if(current.L==null){
					return current;
				}
				current = current.L;
			}
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		Iterator<E> iter = (Iterator<E>)c.iterator();
		while(iter.hasNext())
		{
			this.add(iter.next());
		}
		return true;
	}

	@Override
	public boolean remove(Object o) {
		Node<E> temp = this.locator((E)o);
		//exist
		if(temp.data.compareTo((E)o) == 0){
			//case1: it is a leaf
			if(temp.L == null && temp.R == null){
				//it is the root
				if(temp.data.compareTo(this.root.data)==0){
					this.root.data = null;
				}
				//it is a leaf
				else{
					if(removehelper((E)o).R != null){
						if(temp.data.compareTo(removehelper((E)o).R.data)==0){
							removehelper((E)o).R = null;
						}
						else{
							removehelper((E)o).L = null;
						}					
					}
					else{
						removehelper((E)o).L = null;
					}
				}
			}

			//case2: it has one child

			//Left is null
			else if (temp.L == null && temp.R != null){

				//it is root
				if(temp.data.compareTo(this.root.data)==0){		
					this.root = temp.R;					
			}
				//not root
				else{
					
					if(removehelper((E)o).R != null){
						//Node is on right of parent
						if(temp.data.compareTo(removehelper((E)o).R.data)==0){
						removehelper((E)o).R = temp.R;
						}
						else{
							removehelper((E)o).L = temp.R;
						}
					}
					//Node is on left of parent
					else{
						removehelper((E)o).L = temp.R;
					}
			}

			}
			//Right is null
			else if (temp.R == null && temp.L != null){
				//it is root
				if(temp.data.compareTo(this.root.data)==0){
					this.root = temp.L;				
				}
				//not root
				else{
					if(removehelper((E)o).R != null){
						//Node is on right of parent
						if(temp.data.compareTo(removehelper((E)o).R.data)==0){
						removehelper((E)o).R = temp.L;
						}
						else{
						removehelper((E)o).L = temp.L;
					}
					}
					//Node is on left of parent
					else{
						removehelper((E)o).L = temp.L;
					}
				}
			}

			//case3: it has two child
			else{
			Node<E> currentLEFT = temp.R;
			//it is root
			if(temp.data.compareTo(this.root.data)==0){
				//The Right child has a empty left leaf
				if (currentLEFT.L == null){
					currentLEFT.L = temp.L;
					this.root = currentLEFT;
					}
					//The Right child has at least one leaf
				else{
					//Trace to the smallest leaf's parent
					currentLEFT = currentLEFT.L;
					while(currentLEFT.L.L != null){
					currentLEFT = currentLEFT.L;
					}
					currentLEFT.L.L = temp.L;
					currentLEFT.L.R = temp.R;
					this.root = currentLEFT.L;
					currentLEFT.L = null;
					}
			}
			//Not a root
			else{
				//The Right child has a empty left leaf
				if (currentLEFT.L == null){
					if(removehelper((E)o).R != null){
						//Node is on right of parent
						if(temp.data.compareTo(removehelper((E)o).R.data)==0){
						currentLEFT.L = temp.L;
						removehelper((E)o).R = currentLEFT;
						}
						//Node is on left of parent
						else{
						currentLEFT.L = temp.L;
						removehelper((E)o).L = currentLEFT;
						}
					}
					//Node is on left of parent
					else{
					currentLEFT.L = temp.L;
					removehelper((E)o).L = currentLEFT;
					}
				}
				//The Right child has at least one leaf
				else{
					//Trace to the smallest leaf's parent
					while(currentLEFT.L.L != null){
					currentLEFT = currentLEFT.L;
					}
					if(removehelper((E)o).R != null){
						//Node is on right of parent
						if(temp.data.compareTo(removehelper((E)o).R.data)==0){
						currentLEFT.L.L = temp.L;
						currentLEFT.L.R = temp.R;
						removehelper((E)o).R = currentLEFT.L;
						}
						//Node is on left of parent
						else{
						currentLEFT.L.L = temp.L;
						currentLEFT.L.R = temp.R;
						removehelper((E)o).L = currentLEFT.L;
						}
					}
					//Node is on left of parent
					else{
					currentLEFT.L.L = temp.L;
					currentLEFT.L.R = temp.R;
					removehelper((E)o).L = currentLEFT.L;
					}
				currentLEFT.L = null;
				}
			}
			}
			size--;
			return true;
		}
		//Not exist
		else{
			System.out.println(o+" not exist.");
			return false;
		}
	}

	//Remover-helper finds parent of the node to be removed
	private Node<E> removehelper(E e){
		Node<E> current = this.root;
		while(true){
			if(e.compareTo(current.data)>0){
				if(e.compareTo(current.R.data)==0){
					return current;
				}
				current = current.R;
			}
			else if(e.compareTo(current.data)<0){
				if(e.compareTo(current.L.data)==0){
					return current;
				}
				current = current.L;
			}
		}
	}

	@Override
	public boolean contains(Object o) {
		Node<E> current = locator((E)o);
		if(e.compareTo(current.data)==0){
			return true;
		}
		return false;
	}

	//Iterator reference: https://medium.com/algorithm-problems/binary-search-tree-iterator-19615ec585a
	//Push left chain in first, take out node(smallest), then push the node’s right first node’s left chain in, recursively
	@Override
	public Iterator<E> iterator() {
		return new BSTiterator(this.root);
	}

	private class BSTiterator implements Iterator<E> {
		Stack<Node> stack = new Stack<Node>();
		
		public BSTiterator(Node root) {
			pushLeftchain(root);
		}
	
		public boolean hasNext(){
			if(stack.empty()){
				return false;
			}
			return true;
		}

		//Take out the smallest node, then push it's rightleaf's left chain on the stack
		public E next(){
			Node<E> temp = stack.pop();
			pushLeftchain(temp.R);
			return temp.data;
		}

		//push the node's left chain on stack, smallest on top
		private void pushLeftchain(Node<E> node) {
			if (node != null){
				stack.push(node);
				pushLeftchain(node.L);
			}
		}
	}

	//Height Reference:https://www.educative.io/edpresso/how-to-find-the-height-of-a-binary-tree
	@Override
	public int height() {
		return heighthelper(this.root)-1;
	}

	private int heighthelper(Node<E> root) {
		if(root == null){
			return 0;
		}
		return findmax(heighthelper(root.L), heighthelper(root.R))+1;
	}

	private int findmax(int a, int b) {
		if(a>b)	return a;
		else return b;
	}

	@Override
	public int size() {
		return this.size;
	}

}
