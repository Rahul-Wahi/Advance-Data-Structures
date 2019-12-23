package binomialHeap;

import java.util.Comparator;

public class TestClass {

	public static void main(String[] args)
	{
		BinomialHeap<Integer> iheap = new BinomialHeap<Integer> ( (Comparator) new Comparator<Integer>() {
			
			@Override
			public int compare(Integer a, Integer b)
			{
				return Integer.compare(a, b) ;
			}
		}) ;
		
		iheap.insert(5) ;
		System.out.println( iheap.getMin() );
		iheap.insert(2) ;
		System.out.println( iheap.getMin() );
		iheap.insert(3) ;
		System.out.println( iheap.getMin() );
		iheap.insert(1) ;
		System.out.println( iheap.getMin() );
		iheap.insert(5) ;
		
		iheap.insert(6) ;
		System.out.println( iheap.getMin() );
		iheap.removeMin() ;
		System.out.println( iheap.getMin() );
		iheap.removeMin() ;
		System.out.println( iheap.getMin() );
		
		iheap.removeMin() ;
		System.out.println( iheap.getMin() );
		
		
		
	
		
	}
	
}
