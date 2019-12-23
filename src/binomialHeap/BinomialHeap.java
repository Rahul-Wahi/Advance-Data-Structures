package binomialHeap;

import java.util.*;


public class BinomialHeap<T> {

	Node<T> minPointer;
	Comparator<T> comp;
	Map<Integer, Node<T>> degreeTable;

	public BinomialHeap(Comparator<T> comp) {
		this.comp = comp;
		degreeTable = new HashMap<>();
	}

	/***********************
	Function Name: insert
	Argument: T key
	Description: This function will insert the given key ( type can be anything, as this class supports generic type<T>)
	Return: None
	 *******************
	 */
	public void insert(T data) {
		Node<T> node = new Node<>(data);

		// If empty new node is the min pointer
		if (minPointer == null) {
			minPointer = node;

			// sibling pointer point to itself for circular list
			node.sibling = node;

			return;
		}

		Node<T> minSibling = minPointer.sibling;

		minPointer.sibling = node;
		node.sibling = minSibling;

		// If new node is smaller change min pointer
		if (comp.compare(minPointer.data, data) > 0) {
			minPointer = node;
		}

	}

	
	/***********************
	Function Name: removeMin
	Argument: T key
	Description: This function remove the min value and update the minPointer
	Return: Return min element value of Type T
	 *******************
	 */
	public T removeMin() {
		if (minPointer == null) {
			System.out.println("Invalid Opearation, Tree is empty");
			return null;
		}

		Node<T> minSibling = minPointer.sibling;
		Node<T> minChildPointer = minPointer.childPointer;

		T minData = minPointer.data;

		// If sibling is same as minpointer, then minPointer is only node, so set it to
		// null
		if (minSibling == minPointer) {
			minPointer = null;
			//return minData;
		}
		else
		{
			// to delete min node, copy the sibling data in to
			// min node and delete sibling node (otherwise we have to search for left
			// sibling of min node and
			// update its sibling pointer
			minPointer.data = minSibling.data;
			minPointer.sibling = minSibling.sibling;
			minPointer.childPointer = minSibling.childPointer;
			minPointer.degree = minSibling.degree;

		}
		
		
		// mild child circular list to top level circular list
		minPointer = meld(minPointer, minChildPointer);

		if( minPointer == null )
		{
			return minData ;
		}
		// pairwise combine to get amortized complexity of log(n) for remove min
		pairwiseCombineAllNodes(minPointer);

		// create top level list from the degree table
		minPointer = listFromResutlingDegreeTable();
		return minData;
	}

	/***********************
	Function Name: meld
	Argument: Node<T> tree1, Node<T> tree2
	Description: This function will meld the two min trees
	Return: Node<T> , starting pointer
	 *******************
	 */
	private Node<T> meld(Node<T> tree1, Node<T> tree2) {
		if (tree1 == null)
			return tree2;

		if (tree2 == null)
			return tree1;

		Node<T> tree1Sibling = tree1.sibling;
		tree1.sibling = tree2.sibling;
		tree2.sibling = tree1Sibling;

		return tree1;

	}

	/***********************
	Function Name: pairwiseCombineAllNodes
	Argument: Node<T> node
	Description: This function will pairwise combine the nodes of same degree by traversing all he top
				 level circular list
	Return: Node
	 *******************
	 */
	private void pairwiseCombineAllNodes(Node<T> node) {
		Node<T> firstNode = node;
		degreeTable.put(firstNode.degree, node);

		node = node.sibling;
		List<Node<T>> nodes = new ArrayList<>() ;
		
		while (node != firstNode) {
			nodes.add(node) ;
			node = node.sibling ;
		}
		for (int i = 0; i < nodes.size(); i++)  {
			// pairWiseCombineDhiraj(node);
			node = nodes.get(i) ;
			if (node != null &&degreeTable.containsKey(node.degree)) {
				Node<T> node2 = degreeTable.remove(node.degree);
				if (comp.compare(node.data, node2.data) < 0) {
					pairwiseCombine(node, node2);
				} else {
					pairwiseCombine(node2, node);
				}
			} else {
				if(node != null)
				degreeTable.put(node.degree, node);
			}
			
		}

	}

	/***********************
	Function Name: pairwiseCombine
	Argument: Node<T> node1, Node<T> node2
	Description: This function will pairwise combine give nodes and recursively combine the resulting node
				 if there exists the node with same degree
	Return: Node
	 *******************
	 */
	private void pairwiseCombine(Node<T> node1, Node<T> node2) {

		if (node1.childPointer == null) {
			node1.childPointer = node2;
			node2.sibling = node2;
		} else {
			Node<T> childSibling = node1.childPointer.sibling;
			node1.childPointer.sibling = node2;
			node2.sibling = childSibling;
		}

		node1.degree += 1;

		if (degreeTable.containsKey(node1.degree)) {
			Node<T> node = degreeTable.remove(node1.degree);

			// recursive call, until resulting degree tree is already present in the map
			if (comp.compare(node.data, node1.data) < 0) {
				pairwiseCombine(node, node1);
			} else {
				pairwiseCombine(node1, node);
			}

		}
		else
		{
			// if not present then add to the map
			degreeTable.put(node1.degree, node1);
		}

	}

	/***********************
	Function Name: listFromResutlingDegreeTable
	Argument: Node<T> node1, Node<T> node2
	Description: This function collect the nodes from degreeTable map and 
				  create the top level circular list
	Return: Node<T> (Min node, min pointer)
	 *******************
	 */
	// will create the circular list from nodes presen in degree table
	// and return the min pointer
	private Node<T> listFromResutlingDegreeTable() {
		Node<T> min = null, prev = null, firstNode = null;

		for (Integer degree : degreeTable.keySet()) {
			Node<T> node = degreeTable.get(degree);

			if (prev == null) {
				node.sibling = node;
				firstNode = node;
			} else {
				prev.sibling = node;
			}
			if (min == null) {
				min = node;
			} else if (comp.compare(min.data, node.data) > 0) {
				min = node;
			}

			prev = node;

		}

		// For last Node
		if (prev != null ) {
			prev.sibling = firstNode;
		}
		
		degreeTable.clear();
		return min;
	}

	/***********************
	Function Name: getMin
	Argument: None
	Description: This function will return the minimum value
	Return: min value of type T
	 *******************
	 */
	public T getMin()
	{
		if(minPointer == null)
			return null;
		
		return minPointer.data ;
	}
	
	//Node structure
	class Node<T> {
		T data;
		int degree;
		Node<T> childPointer;
		Node<T> sibling;

		public Node(T element) {
			data = element;
			degree = 0;
			//this.sibling = this;
		}
		
		@Override
		public String toString() 
	    { 
	        return  "( " + data + " , " + degree + " " + sibling + " ) " ; 
	    } 
	}

}
