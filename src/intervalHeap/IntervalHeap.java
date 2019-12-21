package intervalHeap;

import java.util.*;

public class IntervalHeap<T> {

	int size ; 
	ArrayList<Node<T>> ihArray ;
	Comparator<T> comp ;
	
	public IntervalHeap( Comparator<T> comp )
	{
		this.size = 0 ;
		this.comp = comp ;
		ihArray = new ArrayList<>() ;
	}
	
	/***********************
	Function Name: insert
	Argument: T key
	Description: This function will insert the given key ( type can be anything, as this class supports generic type<T>)
				  and heapify the heap
	Return: None
	 *******************
	 */
	public void insert( T element )
	{
		if(size == 0)
		{
			Node<T> newnNode = new Node<>() ;
			newnNode.left = element ;
			ihArray.add(newnNode) ;
			size++ ;
		}
		else
		{
			Node<T> node = ihArray.get(size-1) ;
			
			if(node.right == null)
			{
				if( comp.compare((T) node.left, element) > 0 )
				{
					node.right = node.left ;
					node.left = element ;
					minBottomUpHeapify( size - 1 ) ;
					//call minBottomUpHeapify
				}
				else
				{
					node.right = element ;
					maxBottomUpHeapify(size - 1) ;
					//call maxBottomupHeapify
				}
					
			}
			else
			{
				Node<T> newnNode = new Node<>() ;
				newnNode.left = element ;
				ihArray.add(newnNode) ;
				minBottomUpHeapify(size) ; // if smaller this will run
				maxBottomUpHeapify(size) ;// if larger this will run
				size++;
			}
		}
		 
	}
	

	/***********************
	Function Name: minBottomupHeapify
	Argument: int index
	Description: This function will start the min heapification from the given index and will continue up till the root 
	Return: none
	 ***********************/
	private void minBottomUpHeapify(int index)
	{
		while( parent(index) != index && comp.compare((T)ihArray.get( parent(index) ).left , (T)ihArray.get( index ).left ) > 0   )
		{
			T tmp = (T) ihArray.get( index ).left ;
			ihArray.get( index ).left = ihArray.get( parent(index) ).left ;
			ihArray.get( parent(index) ).left = tmp ;
			
			index = parent(index) ;
		}
	}
	
	/***********************
	Function Name: minTopdownHeapify
	Argument: int index
	Description: This function will start the min heapification from the given index and will continue down till the leaf elements 
				 if necessary swap left and right elements of node
	Return: none
	 ***********************/
	private void minTopdownHeapify(int index)
	{
		int smallestIndex = index ;
		int leftIndex  = left(index) ;
		int rightIndex = right(index) ;
		
		T tmp1 =  (T) ihArray.get( index ).left ;
		//Swap right and left if left is greater than right
		if( comp.compare( tmp1 , (T) ihArray.get( index ).right  ) > 0)
		{
				ihArray.get( index ).left = ihArray.get( index ).right ;
				ihArray.get( index ).right = tmp1 ;
		}
		
		if( leftIndex < size  && comp.compare((T)ihArray.get( leftIndex ).left , (T)ihArray.get( smallestIndex ).left ) < 0 )
		{
			smallestIndex = leftIndex ;
		}
		if( rightIndex < size  && comp.compare((T)ihArray.get( rightIndex ).left , (T)ihArray.get( smallestIndex ).left ) < 0 )
		{
			smallestIndex = rightIndex ;
		}
		
		if( smallestIndex != index)
		{
			T tmp =  (T) ihArray.get( index ).left ;
			ihArray.get( index ).left = ihArray.get( smallestIndex ).left ;
			//Swap right and left if left is greater than right
			if( comp.compare( tmp , (T) ihArray.get( smallestIndex ).right  ) > 0)
			{
					ihArray.get( smallestIndex ).left = ihArray.get( smallestIndex ).right ;
					ihArray.get( smallestIndex ).right = tmp ;
					
			}
			else
			{
				ihArray.get( smallestIndex ).left = tmp ;
			}
			
			minTopdownHeapify(smallestIndex) ;
		}
	}
	
	/***********************
	Function Name: maxTopdownHeapify
	Argument: int index
	Description: This function will start the max heapification from the given index and will continue down till the leaf elements 
				 if necessary swap left and right elements of node
	Return: none
	 ***********************/
	private void maxTopdownHeapify(int index)
	{
		int biggestIndex = index ;
		int leftIndex  = left(index) ;
		int rightIndex = right(index) ;
		
		if( leftIndex < size  &&  (T)ihArray.get( leftIndex ).right == null  )
		{
			
			if(comp.compare((T)ihArray.get( leftIndex ).left , (T)ihArray.get( biggestIndex ).right ) > 0)
			{
				//if greater left child left is greater than parent right
				T tmp = (T)ihArray.get( leftIndex ).left ;
				ihArray.get( leftIndex ).left  = ihArray.get( biggestIndex ).right ;
				ihArray.get( biggestIndex ).right = tmp ;
				return ;
				
			}
			return ;
		}
		
		if( leftIndex < size  && comp.compare((T)ihArray.get( leftIndex ).right , (T)ihArray.get( biggestIndex ).right ) > 0 )
		{
			biggestIndex = leftIndex ;
		}
		
		if( rightIndex < size  && ihArray.get( rightIndex ).right == null )
		{
			//if biggestIndex is same as index , than change the 
			if( biggestIndex == index )
			{
				if(comp.compare((T)ihArray.get( rightIndex ).left , (T)ihArray.get( biggestIndex ).right ) > 0)
				{
					//if greater left child left is greater than parent right
					T tmp = (T)ihArray.get( rightIndex ).left ;
					ihArray.get( rightIndex ).left  = ihArray.get( biggestIndex ).right ;
					ihArray.get( biggestIndex ).right = tmp ;
					return ;
					
				}
				return ;
			}
		}
		
		if( rightIndex < size  && comp.compare((T)ihArray.get( rightIndex ).right , (T)ihArray.get( biggestIndex ).right ) > 0 )
		{
			biggestIndex = rightIndex ;
		}
		
		if( biggestIndex != index)
		{
			T tmp =  (T) ihArray.get( index ).right ;
			ihArray.get( index ).right = ihArray.get( biggestIndex ).right ;
			//Swap right and left if left is greater than right
			if( comp.compare( tmp , (T) ihArray.get( biggestIndex ).left  ) > 0)
			{
					ihArray.get( biggestIndex ).right = ihArray.get( biggestIndex ).left ;
					ihArray.get( biggestIndex ).left = tmp ;
					
			}
			else
			{
				ihArray.get( biggestIndex ).right = tmp ;
			}
			
			maxTopdownHeapify(biggestIndex) ;
		}
	}
	
	
	/***********************
	Function Name: maxBottomupHeapify
	Argument: int index
	Description: This function will start the max heapification from the given index and will continue up till the root 
	Return: none
	 ***********************/
	private void maxBottomUpHeapify(int index)
	{
		T element = (T)ihArray.get( index ).right ;
		//case when last inserted node has only has left end point
		if( element == null )
		{
			element = (T)ihArray.get( index ).left ;
		}
		while( parent(index) != index && comp.compare((T)ihArray.get( parent(index) ).right , element ) < 0   )
		{
//			T tmp = (T) ihArray.get( index ).right ;
//			ihArray.get( index ).right = ihArray.get( parent(index) ).right ;
//			
//			
			if( ihArray.get( index ).right != null )
			{
				ihArray.get( index ).right = ihArray.get( parent(index) ).right  ;
			}
			else
			{
				ihArray.get( index ).left = ihArray.get( parent(index) ).right  ;
			}
			ihArray.get( parent(index) ).right = element ;
			index = parent(index) ;
		}
	}
	
	/***********************
	Function Name: removeMin
	Argument: None
	Description: This function will remove the minimum(root element's left) value and return it and heapify for correcting the heap
	Return: type T
	 ***********************/
	public T removeMin()
	{
		T min ;
		if(size == 0)
		{
			System.out.println("Invalid Opearatiion, Heap is empty") ;
			return null;
		}
		
		if( size ==  1 )
		{
			if( ihArray.get(0).right == null)
			{
				min = (T) ihArray.get(0).left ;
				ihArray.remove(0) ;
				size--;
			}
			else
			{
				min = (T) ihArray.get(0).left ;
				ihArray.get(0).left = ihArray.get(0).right ;
				ihArray.get(0).right = null ;
			}
			
			return min ;
		}
		
		min = (T) ihArray.get(0).left ; 
		
		//Replace with last node left point
		ihArray.get(0).left = ihArray.get(size-1).left ;
		
		//If last node has no right feild than delete the node , else replace the left with right node value
		// and set right to null
		if( ihArray.get(size-1).right == null )
		{
			size-- ;
			ihArray.remove(size) ; // remove the last node
		}
		else
		{
			ihArray.get(size-1).left = ihArray.get(size-1).right ;
			ihArray.get(size-1).right = null ;
		}
		
		//Call top Down min heapification
		minTopdownHeapify(0) ;		
				
		return min ;
		
	}
	
	/***********************
	Function Name: removeMax
	Argument: None
	Description: This function will remove the max(root element's right, if right null than root's right) value and return it and heapify for correcting the heap
	Return: type T
	 ***********************/
	public T removeMax()
	{
		T max ;
		if(size == 0)
		{
			System.out.println("Invalid Opearatiion, Heap is empty") ;
			return null;
		}
		
		if( size ==  1 )
		{
			if( ihArray.get(0).right == null)
			{
				max = (T) ihArray.get(0).left ;
				ihArray.remove(0) ;
				size--;
			}
			else
			{
				max = (T) ihArray.get(0).right ;
				ihArray.get(0).right = null ;
			}
			
			return max ;
		}
		
		max = (T) ihArray.get(0).right ; 
		
		//Replace with last node left point
		ihArray.get(0).right = ihArray.get(size-1).right ;
		
		//If last node has no right feild than delete the node , else replace the left with right node value
		// and set right to null
		if( ihArray.get(size-1).right == null )
		{
			
			size-- ;
			ihArray.get(0).right = ihArray.get(size).left ;
			ihArray.remove(size) ; // remove the last node
		}
		else
		{
			ihArray.get(size-1).left = ihArray.get(size-1).right ;
			ihArray.get(0).right = ihArray.get(size-1).right ;
			ihArray.get(size-1).right = null ;
		}
		
		//Call top Down max heapification
		maxTopdownHeapify(0) ;		
				
		return max ;
		
	}
	
	
	/***********************
	Function Name: getMin
	Argument: None
	Description: This function will return the minimum value, the root of heap
	Return: type T
	 ***********************/
	public T getMin()
	{
		if(size == 0)
			return null ;
		return (T) ihArray.get(0).left ;
	}
	
	/***********************
	Function Name: getMin
	Argument: None
	Description: This function will return the minimum value, the root of heap
	Return: type T
	 ***********************/
	public T getMax()
	{
		if(size == 0)
			return null ;
		if(ihArray.get(0).right == null )
			return (T) ihArray.get(0).left ;
		
		return (T) ihArray.get(0).right ;
	}
	
	public void print()
	{
		System.out.println( ihArray.toString() );
	}
	
	public boolean isEmpty()
	{
		return size == 0 ;
	}
	
	/***********************
	Function Name: parent
	Argument: int index
	Description: This function will return parent's index of the given index
	Return: int
	 ***********************/
	private int parent( int i )
	{
		return (i-1)/2 ;
	}
	
	/***********************
	Function Name: leftChild
	Argument: int index
	Description: This function will return left child's index of the given index
	Return: int
	 ***********************/
	private int left( int i )
	{
		return 2*i + 1 ;
	}
	
	/***********************
	Function Name: rightChild
	Argument: int index
	Description: This function will return right child's index of the given index
	Return: int
	 ***********************/
	private int right( int i )
	{
		return 2*i + 2 ;
	}
	
	class Node<T>
	{
		public T left, right ;
		
		@Override
		public String toString() 
	    { 
	        return  "( " + left + " , " + right + " ) " ; 
	    } 
	}
	
}
