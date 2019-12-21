package intervalHeap;

import java.util.Comparator;

public class TestClass {

	IntervalHeap<Integer> iheap = new IntervalHeap( (Comparator) new Comparator<Integer>() {
		
		@Override
		public int compare(Integer a, Integer b)
		{
			return Integer.compare(a, b) ;
		}
	}) ;
	
	iheap.insert(1) ;
}
