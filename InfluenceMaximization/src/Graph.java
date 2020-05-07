import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Graph {

	private int numberOfNodes;
	private ArrayList<Integer>[] edges;

	public Graph(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
		edges = new ArrayList[numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++)
			edges[i] = new ArrayList<Integer>();
	}

	public void addArc(int from, int to) {
		if (!edges[from].contains(to))
			edges[from].add(to);
	}

	public void print() {
		System.out.println("Nodes = " + numberOfNodes);
		for (int i = 0; i < numberOfNodes; i++) {
			System.out.print("From " + i + " to: [");
			for (Integer j : edges[i])
				System.out.print(j + " ");
			System.out.println("]");
		}
	}

	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	public Pair_Integer reachability(ArrayList<Integer> A, int node) {

		int count_a = 0;
		int count_a_union_s = 0;
		ArrayList<Integer> nodes = new ArrayList<Integer>(A);

		boolean[] visited = new boolean[numberOfNodes];
		for (int iter = 0; iter <= 1; iter++) {
			for (int i = (nodes.size() - 1) * iter; i < nodes.size(); i++) { //alla prima iter parte da 0, alla seconda parte dall'ultimo che è stato aggiunto cioè node

				if (!visited[nodes.get(i)]) {
					for (Integer adjacent : edges[nodes.get(i)]) {
						nodes.add(adjacent);
					}
					visited[nodes.get(i)] = true;

					if (iter == 0) {
						count_a_union_s++;
						count_a++;
					} else {
						count_a_union_s++;
					}
				}
			}

			nodes.add(node);

		}
		return new Pair_Integer(count_a_union_s, count_a);
	}

}

class Edge {

	private int from;
	private int to;

	public Edge() {
		super();
	}

	public Edge(int from, int to) {
		super();
		this.from = from;
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

}
