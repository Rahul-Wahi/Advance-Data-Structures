package intervalHeap;

import java.util.Comparator;

public class TestClass {

	public static void main(String[] args)
	{
		IntervalHeap<Integer> iheap = new IntervalHeap<Integer> ( (Comparator) new Comparator<Integer>() {
			
			@Override
			public int compare(Integer a, Integer b)
			{
				return Integer.compare(a, b) ;
			}
		}) ;
		
		iheap.insert(1) ;
		iheap.print();
		iheap.insert(2) ;
		iheap.print();
		iheap.insert(3) ;
		iheap.print();
		iheap.insert(4) ;
		iheap.print();
		iheap.insert(5) ;
		iheap.print();
		iheap.insert(6) ;
		iheap.print();
		iheap.removeMin() ;
		iheap.print();
		iheap.removeMax() ;
		iheap.print();
	}
	
}
