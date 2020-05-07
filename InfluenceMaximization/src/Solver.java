import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

public class Solver {

	public Solver() {
	}

	// solve using the basic idea
	public ArrayList<Integer> solveBasic() throws IOException {

		ArrayList<Integer> A = new ArrayList<Integer>(); // array of the result
		ArrayList<Integer> V_minus_A = new ArrayList<Integer>(); // array containing not used nodes

		for (int i = 0; i < Main.numberOfNodes; i++)
			V_minus_A.add(i);

		while (A.size() < Main.k) {
			System.out.println(A.size());
			double maxInfluenced = -1;
			int bestNode = -1;

			// for each node s not already used, compute the expected improvement delta_s
			// choosing the best one
			for (Integer s : V_minus_A) {

				double avgInfluenced = computeDelta_s(Main.numberOfScenarios, A, s);
				if (avgInfluenced > maxInfluenced) {
					maxInfluenced = avgInfluenced;
					bestNode = s;
				}
			}
			A.add(bestNode);
			V_minus_A.remove(Integer.valueOf(bestNode));
		}

		return A;
	}

	// select k random nodes
	public ArrayList<Integer> solveRandom() throws IOException {

		ArrayList<Integer> A = new ArrayList<Integer>();
		ArrayList<Integer> V_minus_A = new ArrayList<Integer>();
		for (int i = 0; i < Main.numberOfNodes; i++)
			V_minus_A.add(i);

		Random r = new Random();
		while (A.size() < Main.k)
			A.add(V_minus_A.remove(r.nextInt(V_minus_A.size())));

		return A;
	}

	public ArrayList<Integer> solveLazy() throws IOException {

		ArrayList<Integer> A = new ArrayList<Integer>();
		ArrayList<Integer> V_minus_A = new ArrayList<Integer>();
		boolean cur[] = new boolean[Main.numberOfNodes];
		PriorityQueue<Pair_Node_Delta> priorityQueue = new PriorityQueue<>();

		for (int i = 0; i < Main.numberOfNodes; i++) {
			V_minus_A.add(i);
			priorityQueue.add(new Pair_Node_Delta(i, Double.MAX_VALUE));
		}

		while (A.size() < Main.k) {
			for (Integer i : V_minus_A)
				cur[i] = false;
			while (true) {
				Pair_Node_Delta s_star = priorityQueue.poll(); // take the best node
				if (cur[s_star.getNode()]) { // if it was already checked, then add it to the solution
					A.add(s_star.getNode());
					V_minus_A.remove(Integer.valueOf(s_star.getNode()));
					break;
				}
				// otherwise, compute its value and readd it into the queue
				double delta = computeDelta_s(Main.numberOfScenarios, A, s_star.getNode());
				s_star.setDelta(delta);
				cur[s_star.getNode()] = true;
				priorityQueue.add(s_star);
			}
		}

		return A;
	}

	private double computeDelta_s(int numberOfScenarios, ArrayList<Integer> A, int s) {
		double avgInfluenced = 0;
		for (int j = 0; j < numberOfScenarios; j++) {
			Pair_Integer reachable = Main.scenarios[j].reachability(A, s);
			avgInfluenced += reachable.getR_a_union_s() - reachable.getR_a();
		}

		return avgInfluenced / numberOfScenarios;
	}
}

class Pair_Node_Delta implements Comparable<Pair_Node_Delta> {
	private int node;
	private double delta;

	public Pair_Node_Delta() {
	}

	public Pair_Node_Delta(int node, double delta) {
		super();
		this.node = node;
		this.delta = delta;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	@Override
	public int compareTo(Pair_Node_Delta obj) {
		if (obj.getDelta() > delta)
			return 1;
		else if (obj.getDelta() < delta)
			return -1;
		return 0;
	}

}
