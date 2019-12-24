package pairingHeap;

import java.util.Comparator;

public class TestClass {

	public static void main(String[] args)
	{
		PairingHeap<Integer> pheap = new PairingHeap<Integer> ( (Comparator) new Comparator<Integer>() {
			
			@Override
			public int compare(Integer a, Integer b)
			{
				return Integer.compare(a, b) ;
			}
		}) ;
		
		pheap.insert(5) ;
		//System.out.println( pheap.getMin() );
		pheap.insert(2) ;
		//System.out.println( pheap.getMin() );
		pheap.insert(3) ;
		//System.out.println( pheap.getMin() );
		pheap.insert(1) ;
		//System.out.println( pheap.getMin() );
		pheap.insert(5) ;
		
		pheap.insert(6) ;
		//System.out.println( pheap.getMin() );
		
		
		System.out.println( pheap.removeMin() ) ;
		System.out.println( pheap.removeMin() ) ;
		System.out.println( pheap.removeMin() ) ;
		System.out.println( pheap.removeMin() ) ;
		System.out.println( pheap.removeMin() ) ;
		System.out.println( pheap.removeMin() ) ;
		System.out.println( pheap.removeMin() ) ;
		System.out.println( pheap.getMin() ) ;
		System.out.println( pheap.nodes.get(0).data ) ;
		System.out.println( pheap.remove( pheap.nodes.get(0)) ) ;
		
		System.out.println( pheap.nodes.get(1).data ) ;
		System.out.println( pheap.remove( pheap.nodes.get(1)) ) ;
		
		System.out.println( pheap.nodes.get(2).data ) ;
		System.out.println( pheap.remove( pheap.nodes.get(2)) ) ;
		
		System.out.println( pheap.nodes.get(3).data ) ;
		System.out.println( pheap.remove( pheap.nodes.get(3)) ) ;
		
		System.out.println( pheap.nodes.get(4).data ) ;
		System.out.println( pheap.remove( pheap.nodes.get(4)) ) ;
		
		System.out.println( pheap.getMin() ) ;
		
		
		
	
		
	}
	
}
