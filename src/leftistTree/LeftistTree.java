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
		
		this.root =  meld(root , newNode) ;
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
		this.root = meld( left , right ) ;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               meld(left , right) ;
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
		
		Node node ;
		
		//Go in node2 right subtree , node2 has smaller value else go to right subtree of node1
		if( comp.compare(node1.element , node2.element ) > 0)
		{
			
			
				node2.right = meld( node1, node2.right ) ;
				if( node2.right != null)
				{
					node2.right.parent = node2 ;
				}
					
			
			if( getS(node2.left) < getS( node2.right ) ) 
			{
				swap(node2) ;
			}
			else
			{
				node2.s = 1 + node2.right.s ;
			}
			
			return node2 ;
		}
		
		if( node1.right == null )
		{
			node1.right = node2 ;
			if( node1.right != null)
			{
				node1.right.parent = node2 ;
			}
			node2.parent = node1 ;
			node = node1 ;
			//node.s =  1 + node1.s ; 
		}
		else
		{
			node1.right = meld( node1.right , node2 ) ;
			if( node1.right != null)
			{
				node1.right.parent = node1 ;
			}
		}
		
		if( getS(node1.left) < getS( node1.right ) ) 
		{
			swap(node1) ;
		}
		else
		{
			node1.s = 1 + node1.right.s ;
		}
		
		return node1 ;
		
	}
	
	public void removeElement(T element)
	{
		if( root == null )
		{
			System.out.println("Tree is empty");
			return ;
		}
		
		if( comp.compare(root.element, element) == 0 )
		{
			removeMin() ;
			return ;
		}
		
		Node<T> nodeToDelete  = search( root, element ) ;
		
		if( nodeToDelete == null )
		{
			System.out.println("Element " + element + " Not present " );
		}
		Node<T> parent = nodeToDelete.parent ;
		if( parent.left == nodeToDelete)
		{
			parent.left = nodeToDelete.left ;
			
		}
		else
		{
			parent.right = nodeToDelete.left ;
		}
		
		if(nodeToDelete.left.parent != null )
		{
			nodeToDelete.left.parent = parent ;
		}
		
		adjustS( parent ) ;
		meld( root, nodeToDelete.right) ;
		 
	}
	
	public void adjustS(Node<T> node)
	{
		if( node == null || node == root )
			return ;
		
		if( node.left.s < node.right.s ) 
		{
			swap(node);
			adjustS(node.parent) ;
		}
	}
	
	public Node<T> search(Node<T> node , T element)
	{
		if( node == null )
			return null ;
		Node<T> result ;
		if( comp.compare(node.element, element ) == 0 )
		{
			return node ;
		}
		
		result = search(node.left, element) ;
		
		if( result == null )
		{
			result = search(node.right , element ) ;
		}
		
		return result ;
		
		
	}
	/***********************
	Function Name: swap
	Argument: Node<T> node
	Description: This function will swap the left and right child of given nodes
	Return: None
	 ***********************/
	private void swap( Node<T> node)
	{
		Node<T> tmp = node.left ;
		node.left = node.right ;
		node.right = tmp ;
		node.s = 1 + node.right.s ;
	}
	
	public int getS( Node node )
	{
		if( node == null )
			return 0 ;
		
		return node.s ;
	}
	
	class Node<T>
	{
		T element ;
		Node left , right, parent ;
		int s ;
		
		public Node(T element)
		{
			this.element = element ;
			s = 1;
		}
		
		
	}
}
