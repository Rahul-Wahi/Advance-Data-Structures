package leftistTree;

import java.util.Comparator;

import intervalHeap.IntervalHeap;

public class TestClass {

	public static void main(String[] args) {
		LeftistTree<Integer> lefttistTree = new LeftistTree<Integer>((Comparator) new Comparator<Integer>() {

			@Override
			public int compare(Integer a, Integer b) {
				return Integer.compare(a, b);
			}
		});

		lefttistTree.put(1);
		lefttistTree.print();
		lefttistTree.put(2);
		lefttistTree.print();
		lefttistTree.put(3);
		lefttistTree.print();
		lefttistTree.put(4);
		lefttistTree.print();
		lefttistTree.put(5);
		lefttistTree.print();
		lefttistTree.put(6);
		lefttistTree.print();
		lefttistTree.removeMin();
		lefttistTree.print();
		lefttistTree.removeElement(4);
		lefttistTree.print();
		
	}
}
