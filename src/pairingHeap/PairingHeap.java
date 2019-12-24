package pairingHeap;

import java.util.*;

public class PairingHeap<T> {

	Node<T> minPointer;
	Comparator<T> comp;
	Map<Integer, Node<T>> degreeTable;
	int size;
	public List<Node<T>> nodes;

	public PairingHeap(Comparator<T> comp) {
		this.comp = comp;
		degreeTable = new HashMap<>();
		size = 0;
		nodes = new ArrayList<>();
	}

	/***********************
	 * Function Name: insert Argument: T key Description: This function will insert
	 * the given key ( type can be anything, as this class supports generic type<T>)
	 * Return: None
	 *******************
	 */
	public void insert(T data) {
		Node<T> node = new Node<>(data);

		nodes.add(node);
		size++;

		this.minPointer = meld( this.minPointer , node) ;

	}

	

	
	/***********************
	 * Function Name: removeMin Argument: T key Description: This function remove
	 * the min value and update the minPointer Return: Return min element value of
	 * Type T
	 *******************
	 */
	public T removeMin() {
		if (minPointer == null) {
			System.out.print("Invalid Opearation, Tree is empty ");
			return null;
		}
		
		size--;
//		Node<T> minSibling = minPointer.sibling;
		Node<T> minChildPointer = minPointer.childPointer;

		T minData = minPointer.data;

		

		minPointer = meld2Pass(minChildPointer) ;
		
		return minData;
	}

	public Node<T> meld2Pass( Node<T> node )
	{
		if( node == null )
		{
			return null ; 
		}
		Stack<Node<T>> stack = new Stack<>() ;
		
		LinkedList<Node<T>> nodes = new LinkedList<>() ;
		
		while( node != null)
		{
			nodes.addLast( node) ;
			node = node.rightSibling ;
		}
		
		while( nodes.size() > 0 ) 
		{
			Node<T> node1 = nodes.removeFirst();
			Node<T> node2 = null ;
			
			if( !nodes.isEmpty() )
			{
				node2 = nodes.removeFirst() ;
			}
			
			if( node2 == null)
			{
				if( stack.size() == 0)
				{
					return node1;
				}
				else
				{
					Node<T> tmp =  meld(node1, stack.pop() ) ;
					stack.push(tmp) ;
				}
				
				//we have reached last node (odd no of nodes case) so break
				break;
			}
			else
			{
				stack.push( meld( node1 , node2 ) ) ;
			}
			//meld( node1 , node2 ) ;
//			/node = node.rightSibling.rightSibling ;
			
		}
		
		Node<T> node1 = stack.pop() ;
		
		while( stack.size() > 0)
		{
			Node<T> node2 = stack.pop() ;
			node = meld( node1 , node2 ) ;
		}
		return node1 ;
	}
	
	
	
	public Node<T> meldMultiplass( Node<T> node )
	{
		if( node == null )
			return null ;
		
		LinkedList<Node<T>> queue = new LinkedList<>() ;
		
		while( node != null )
		{
			queue.addLast( node ) ;
			node = node.rightSibling ;
		}
		
		while( true )
		{
			Node<T> node1 = queue.removeFirst()  ;
			
			if( queue.size() == 0)
				return node1 ;
			
			Node<T> node2 = queue.removeFirst() ;
			
			queue.addLast( meld( node1 , node2 ) );
		}
	}
	/***********************
	 * Function Name: remove Argument: Node<T> node Description: This function
	 * remove the given node from heap Return: Return min element value of Type T
	 *******************
	 */
	public T remove(Node<T> node) {
		if (node == null)
			return null;
		
		if (minPointer == null) {
			System.out.print("Invalid Opearation, Tree is empty ");
			return null;
		}
		
		if (node == this.minPointer) {
			return removeMin();
		}
		
		size--;
		
		Node<T> childPointer = node.childPointer;
		
		deleteFromDoublyLinkedList( node ) ;

		Node<T> subtree = meld2Pass(childPointer) ;
		
		minPointer = meld(minPointer , subtree ) ;
		
		return node.data;
	}
	
	public void deleteFromDoublyLinkedList( Node<T> node )
	{
		if( node == null)
			return ;
			// if parent is pointing to this node then change the child pointer
		if (node.leftSibling.childPointer == node) {
			node.leftSibling.childPointer = node.rightSibling;

		} else {
			node.leftSibling.rightSibling = node.rightSibling;
		}

		if (node.rightSibling != null) {
			node.rightSibling.leftSibling = node.leftSibling;
		}
				
	}

	/***********************
	 * Function Name: decreaseKey Argument: Node<T> node, T newSmallValue
	 * Description: This function will update the node value if newSmallValue is
	 * smaller than stored value Return: None
	 *******************
	 */
	public void decreaseKey(Node<T> node, T newSmallValue) {
		if (node == null || node.data == null) {
			System.out.print("Please provide existing node value ");
			return;
		}

		if (comp.compare(node.data, newSmallValue) < 0) {
			System.out.print("Please provide smaller vale then the existing value " + node.data + " Provide : "
					+ newSmallValue + " ");
			return;
		}
		node.data = newSmallValue;
		
		if( node == this.minPointer )
			return ;
		
		deleteFromDoublyLinkedList( node ) ;
		
		minPointer = meld( node , minPointer) ;
	

	}

	/***********************
	 * Function Name: meld 
	 * Argument: Node<T> tree1 , Node<T> tree2 
	 * Description: This function will meld the two min trees 
	 * Return: Node<T> , min pointer
	 *******************
	 */
	private Node<T> meld(Node<T> tree1, Node<T> tree2) {
		if (tree1 == null)
			return tree2;

		if (tree2 == null)
			return tree1;
		
		Node<T> tmp ;
		
		if( comp.compare(tree1.data, tree2.data) > 0)
		{
			tmp = tree1 ;
			tree1 = tree2 ;
			tree2 = tmp ;
		}
		
		Node<T> tree1ChildPointer = tree1.childPointer ;
		
		tree1.childPointer = tree2 ;
		tree2.leftSibling = tree1 ;
		
		if(  tree1ChildPointer != null )
		{
			tree2.rightSibling = tree1ChildPointer ;
			tree1ChildPointer.leftSibling = tree2 ;
		}
		else
		{
			tree2.rightSibling = null ;
		}

		return tree1;

	}

	
	
	/***********************
	 * Function Name: getMin Argument: None Description: This function will return
	 * the minimum value Return: min value of type T
	 *******************
	 */
	public T getMin() {
		if (minPointer == null)
			return null;

		return minPointer.data;
	}

	public int getSize() {
		return this.size;
	}


	// Node structure
	class Node<T> {
		T data;
		Node<T> childPointer;
		Node<T> leftSibling;
		Node<T> rightSibling;

		public Node(T element) {
			data = element;
		}

		@Override
		public String toString() {
			return "( " + data + " , "  + " L " + leftSibling.data + " R " + rightSibling 
					+ " ) ";
		}
	}

}
