import java.util.Iterator;


public class TreeSort{
	/** Sorts an array using TreeSort with a balanced BST implementation 
	 * @param a an array to sort
	 */
	public static <E extends Comparable <? super E>> void sort( E[] a){
		Tree <E> tree = new A3AVLTree<>();
		sort(tree, a);
	}
	
	/**
	 * Sorts an array using TreeSort with a specified BST
	 * @param tree tree to use
	 * @param a an array to sort
	 */
	public static <E extends Comparable <? super E>> void sort(Tree <E> tree, E[] a){
		for(int i=0;i<a.length; i++){
            tree.add(a[i]);
        }
		tree.remove((Integer)5);
		/*tree.remove((Integer)99);
		tree.remove((Integer)51);
		tree.remove((Integer)97);
		tree.remove((Integer)9);
		tree.remove((Integer)99);*/
		Iterator<E> iter = (Iterator<E>)tree.iterator();
		for(int i=0;i<a.length-1; i++){
            a[i]=iter.next();
        }
	}
}
