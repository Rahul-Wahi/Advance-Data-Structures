package fibonacciHeap;

import java.util.*;

public class FibonacciHeap<T> {

	Node<T> minPointer;
	Comparator<T> comp;
	Map<Integer, Node<T>> degreeTable;
	int size;
	public List<Node<T>> nodes;

	public FibonacciHeap(Comparator<T> comp) {
		this.comp = comp;
		degreeTable = new HashMap<>();
		size = 0;
		nodes = new ArrayList<>();
	}

	/***********************
	 * Function Name: insert Argument: T key Description: This function will insert
	 * the given key ( type can be anything, as this class supports generic type<T>)
	 * Return: None
	 *******************
	 */
	public void insert(T data) {
		Node<T> node = new Node<>(data);

		nodes.add(node);
		size++;

		insert(node);

	}

	private void insert(Node<T> node) {
		addLast(minPointer, node);

		if (minPointer == null) {
			minPointer = node;
			return;
		}

		// If new node is smaller change min pointer
		if (comp.compare(minPointer.data, node.data) > 0) {
			minPointer = node;
		}
	}

	public void addLast(Node<T> minPointer, Node<T> node) {

		if (minPointer == null) {
			// minPointer = node;

			// sibling pointer point to itself for circular list
//			node.sibling = node;
			node.leftSibling = node;
			node.rightSibling = node;

			return;
		}
		Node<T> minLeftSibling = minPointer.leftSibling;

		minLeftSibling.rightSibling = node;
		minPointer.leftSibling = node;

		node.leftSibling = minLeftSibling;
		node.rightSibling = minPointer;
	}

	/***********************
	 * Function Name: removeMin Argument: T key Description: This function remove
	 * the min value and update the minPointer Return: Return min element value of
	 * Type T
	 *******************
	 */
	public T removeMin() {
		if (minPointer == null) {
			System.out.print("Invalid Opearation, Tree is empty ");
			return null;
		}

		size--;
//		Node<T> minSibling = minPointer.sibling;
		Node<T> minChildPointer = minPointer.childPointer;

		T minData = minPointer.data;

		// If sibling is same as minpointer, then minPointer is only node, so set it to
		// null
		if (minPointer.rightSibling == minPointer) {
			minPointer = null;
			// return minData;
		} else {
			// shift the min pointer to right sibling
			// at this point we wont care about min value, min pointer will be updated to
			// correct value after pairwise combine
			minPointer.rightSibling.leftSibling = minPointer.leftSibling;
			minPointer.leftSibling.rightSibling = minPointer.rightSibling;

			minPointer = minPointer.rightSibling;
		}

		// All the child Node will become root, so set cascadeCut value to null
		setCascadeCutNull(minChildPointer);

		// meld child circular list to top level circular list
		minPointer = meld(minPointer, minChildPointer);

		if (minPointer == null) {
			return minData;
		}
		// pairwise combine to get amortized complexity of log(n) for remove min
		pairwiseCombineAllNodes(minPointer);

		// create top level list from the degree table
		minPointer = listFromResutlingDegreeTable();
		return minData;
	}

	/***********************
	 * Function Name: remove Argument: Node<T> node Description: This function
	 * remove the given node from heap Return: Return min element value of Type T
	 *******************
	 */
	public T remove(Node<T> node) {
		if (node == null)
			return null;

		if (node == this.minPointer) {
			return removeMin();
		}

		Node<T> childPointer = node.childPointer;

		// This if will always run for childs ,because in top list, if there is only 1
		// node
		// then it will be remove min case
		if (node.rightSibling == node) {

			node.parent.childPointer = null;

		} else {
			node.leftSibling.rightSibling = node.rightSibling;
			node.rightSibling.leftSibling = node.leftSibling;
			if (node.parent != null && node.parent.childPointer == node) {
				node.parent.childPointer = node.rightSibling;
			}

		}

		// if parent childcut true then call cascadeCut on parent
		if (node.parent != null && node.parent.childCut)
			cascadeCut(node.parent);

		meld(this.minPointer, childPointer);

		return node.data;
	}

	/***********************
	 * Function Name: decreaseKey Argument: Node<T> node, T newSmallValue
	 * Description: This function will update the node value if newSmallValue is
	 * smaller than stored value Return: None
	 *******************
	 */
	public void decreaseKey(Node<T> node, T newSmallValue) {
		if (node == null || node.data == null) {
			System.out.print("Please provide existing node value ");
			return;
		}

		if (comp.compare(node.data, newSmallValue) < 0) {
			System.out.print("Please provide smaller vale then the existing value " + node.data + " Provide : "
					+ newSmallValue + " ");
			return;
		}
		node.data = newSmallValue;

		if (node.parent != null && comp.compare(node.data, node.parent.data) < 0) {
			cascadeCut(node);

			if (comp.compare(node.data, this.minPointer.data) < 0)
				this.minPointer = node;
		}

	}

	/***********************
	 * Function Name: meld Argument: Node<T> tree1 (smaller data than tree2),
	 * Node<T> tree2 Description: This function will meld the two min trees Return:
	 * Node<T> , starting pointer
	 *******************
	 */
	private Node<T> meld(Node<T> tree1, Node<T> tree2) {
		if (tree1 == null)
			return tree2;

		if (tree2 == null)
			return tree1;

		Node<T> tree1LeftSibling = tree1.leftSibling;

		tree1LeftSibling.rightSibling = tree2;
		tree1.leftSibling = tree2.leftSibling;

		tree2.leftSibling.rightSibling = tree1;

		tree2.leftSibling = tree1LeftSibling;

		return tree1;

	}

	/***********************
	 * Function Name: pairwiseCombineAllNodes Argument: Node<T> node Description:
	 * This function will pairwise combine the nodes of same degree by traversing
	 * all he top level circular list Return: Node
	 *******************
	 */
	private void pairwiseCombineAllNodes(Node<T> node) {
		Node<T> firstNode = node;
		degreeTable.put(firstNode.degree, node);

		node = node.rightSibling;
		List<Node<T>> nodes = new ArrayList<>();

		while (node != firstNode) {
			nodes.add(node);
			node = node.rightSibling;
		}
		for (int i = 0; i < nodes.size(); i++) {
			// pairWiseCombineDhiraj(node);
			node = nodes.get(i);
			if (node != null && degreeTable.containsKey(node.degree)) {
				Node<T> node2 = degreeTable.remove(node.degree);
				if (comp.compare(node.data, node2.data) < 0) {
					pairwiseCombine(node, node2);
				} else {
					pairwiseCombine(node2, node);
				}
			} else {
				if (node != null)
					degreeTable.put(node.degree, node);
			}

		}

	}

	/***********************
	 * Function Name: pairwiseCombine Argument: Node<T> node1, Node<T> node2
	 * Description: This function will pairwise combine given nodes and recursively
	 * combine the resulting node if there exists the node with same degree Return:
	 * Node
	 *******************
	 */
	private void pairwiseCombine(Node<T> node1, Node<T> node2) {

		addLast(node1.childPointer, node2);

		if (node1.childPointer == null) {
			node1.childPointer = node2;
		}

		node2.parent = node1;
		node2.childCut = false;
		node1.degree += 1;

		if (degreeTable.containsKey(node1.degree)) {
			Node<T> node = degreeTable.remove(node1.degree);

			// recursive call, until resulting degree tree is already present in the map
			if (comp.compare(node.data, node1.data) < 0) {
				pairwiseCombine(node, node1);
			} else {
				pairwiseCombine(node1, node);
			}

		} else {
			// if not present then add to the map
			degreeTable.put(node1.degree, node1);
		}

	}

	/***********************
	 * Function Name: listFromResutlingDegreeTable Argument: Node<T> node1, Node<T>
	 * node2 Description: This function collect the nodes from degreeTable map and
	 * create the top level circular list Return: Node<T> (Min node, min pointer)
	 *******************
	 */
	// will create the circular list from nodes presen in degree table
	// and return the min pointer
	private Node<T> listFromResutlingDegreeTable() {

		this.minPointer = null;
		for (Integer degree : degreeTable.keySet()) {
			Node<T> node = degreeTable.get(degree);
			insert(node);
		}

		degreeTable.clear();
		return this.minPointer;
	}

	/***********************
	 * Function Name: getMin Argument: None Description: This function will return
	 * the minimum value Return: min value of type T
	 *******************
	 */
	public T getMin() {
		if (minPointer == null)
			return null;

		return minPointer.data;
	}

	public int getSize() {
		return this.size;
	}

	public void setCascadeCutNull(Node<T> node) {
		if (node == null)
			return;

		Node<T> firstNode = node;

		while (node.rightSibling != firstNode) {
			node.childCut = null;
			node = node.rightSibling;
			node.parent = null;

		}

		// for last node
		node.childCut = null;
		node.parent = null;

	}

	public void cut(Node<T> node) {
		addLast(this.minPointer, node);

		node.childCut = null;

	}

	public void cascadeCut(Node<T> node) {
		if (node == null)
			return;

		cut(node);

		if (node.parent.childCut) {
			cascadeCut(node.parent);
		}

		if (node.parent.childCut == false)
			node.parent.childCut = true;
	}

	// Node structure
	class Node<T> {
		T data;
		int degree;
		Node<T> childPointer;
		// Node<T> sibling;
		Node<T> leftSibling;
		Node<T> rightSibling;
		Boolean childCut;
		Node<T> parent;

		public Node(T element) {
			data = element;
			degree = 0;
			// this.sibling = this;
		}

		@Override
		public String toString() {
			return "( " + data + " , " + degree + " L " + leftSibling.data + " R " + rightSibling + " Parent " + parent
					+ " ) ";
		}
	}

}
