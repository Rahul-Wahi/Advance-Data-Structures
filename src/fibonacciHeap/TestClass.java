package fibonacciHeap;

import java.util.Comparator;

public class TestClass {

	public static void main(String[] args)
	{
		FibonacciHeap<Integer> fheap = new FibonacciHeap<Integer> ( (Comparator) new Comparator<Integer>() {
			
			@Override
			public int compare(Integer a, Integer b)
			{
				return Integer.compare(a, b) ;
			}
		}) ;
		
		fheap.insert(5) ;
		//System.out.println( fheap.getMin() );
		fheap.insert(2) ;
		//System.out.println( fheap.getMin() );
		fheap.insert(3) ;
		//System.out.println( fheap.getMin() );
		fheap.insert(1) ;
		//System.out.println( fheap.getMin() );
		fheap.insert(5) ;
		
		fheap.insert(6) ;
		//System.out.println( fheap.getMin() );
		
		
//		System.out.println( fheap.removeMin() ) ;
//		System.out.println( fheap.removeMin() ) ;
//		System.out.println( fheap.removeMin() ) ;
//		System.out.println( fheap.removeMin() ) ;
//		System.out.println( fheap.removeMin() ) ;
//		System.out.println( fheap.removeMin() ) ;
//		System.out.println( fheap.removeMin() ) ;
//		System.out.println( fheap.getMin() ) ;
		System.out.println( fheap.nodes.get(0).data ) ;
		System.out.println( fheap.remove( fheap.nodes.get(0)) ) ;
		
		System.out.println( fheap.nodes.get(1).data ) ;
		System.out.println( fheap.remove( fheap.nodes.get(1)) ) ;
		
		System.out.println( fheap.nodes.get(2).data ) ;
		System.out.println( fheap.remove( fheap.nodes.get(2)) ) ;
		
		System.out.println( fheap.nodes.get(3).data ) ;
		System.out.println( fheap.remove( fheap.nodes.get(3)) ) ;
		
		System.out.println( fheap.nodes.get(4).data ) ;
		System.out.println( fheap.remove( fheap.nodes.get(4)) ) ;
		
		System.out.println( fheap.getMin() ) ;
		
		
		
	
		
	}
	
}
