package leftistTree;

import java.util.Comparator;
import java.util.LinkedList;

public class LeftistTree<T> {

	int size ;
	Node<T> root ;
	Comparator<T> comp ;
	
	public LeftistTree( Comparator<T> comp)
	{
		this.comp = comp ;
	}
	
	/***********************
	Function Name: put
	Argument: T element
	Description: This function will insert element into the tree
	Return: None
	 ***********************/
	public void put( T element )
	{
		Node newNode = new Node<>( element ) ;
		
		meld(root , newNode) ;
	}
	
	/***********************
	Function Name: removeMin
	Argument: Node
	Description: This function will remove the minimum(root) element from the tree
	Return: None
	 ***********************/
	public void removeMin( )
	{
		if( root == null)
		{
			System.out.println("Invalid Opearation , Tree is empty");
			return ; 
		}
		
		Node<T> left = root.left ;
		Node<T> right = root.right ;
		meld(left , right) ;
	}
	
	/***********************
	Function Name: meld
	Argument: Node<T> node1 , Node<T> node2
	Description: This function will meld the two min trees
	Return: Return root of resulting tree
	 ***********************/
	private Node<T> meld( Node<T> node1 , Node<T> node2 )
	{
		if(node1 == null)
			return node2 ;
		
		if(node2 == null)
			return node1 ;
		
		if( comp.compare(node1.element , node2.element ) > 0)
		{
			Node node  =  meld(node1, node2.right) ;
			
			
			return node ;
		}
		
		Node node = meld(node1.right , node2 ) ;
		
		return node ;
	}
	
	class Node<T>
	{
		T element ;
		Node left , right ;
		int s ;
		
		public Node()
		{
			
		}
		
		public Node(T element)
		{
			this.element = element ;
			s = 0;
		}
	}
}
