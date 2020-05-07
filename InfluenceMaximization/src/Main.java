import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Main {
	static Graph[] scenarios;
	static int numberOfScenarios = 100;
	static int k = 2;
	static double p = 0.1;
	static int numberOfNodes;
	static String path = "src/Dataset/Graph1.txt";
	static Random random = new Random();

	public static void main(String[] args) throws IOException {

		readInstanceandGenerateScenarios();

		long start = System.currentTimeMillis();
		ArrayList<Integer> resultBasic = new Solver().solveBasic();
		System.out.println("Basic time =" + (System.currentTimeMillis() - start) / 1000);

		start = System.currentTimeMillis();
		ArrayList<Integer> resultRandom = new Solver().solveRandom();
		System.out.println("Random time = " + (System.currentTimeMillis() - start) / 1000);

		start = System.currentTimeMillis();
		ArrayList<Integer> resultLazy = new Solver().solveLazy();
		System.out.println("Lazy time = " + (System.currentTimeMillis() - start) / 1000);

		double avgInfluencedBasic = 0;
		double avgInfluencedRandom = 0;
		double avgInfluencedLazy = 0;
		for (int j = 0; j < numberOfScenarios; j++) {
			Pair_Integer reachable = Main.scenarios[j].reachability(resultBasic, resultBasic.get(0));
			avgInfluencedBasic += reachable.getR_a();

			reachable = Main.scenarios[j].reachability(resultRandom, resultRandom.get(0));
			avgInfluencedRandom += reachable.getR_a();

			reachable = Main.scenarios[j].reachability(resultLazy, resultLazy.get(0));
			avgInfluencedLazy += reachable.getR_a();

		}

		System.out.println("Basic sol=" + resultBasic);
		System.out.println("Random sol=" + resultRandom);
		System.out.println("Lazy sol=" + resultLazy);

		System.out.println("Basic avg=" + (avgInfluencedBasic / numberOfScenarios));
		System.out.println("Random avg=" + (avgInfluencedRandom / numberOfScenarios));
		System.out.println("Lazy avg=" + (avgInfluencedLazy / numberOfScenarios));

//		generateRandomGraph(4000, 80000, "src/Dataset/Graph1.txt");

	}

	public static void generateRandomGraph(int numberOfNodes, int numberOfArcs, String path) throws IOException {

		try {
			FileWriter myWriter = new FileWriter(path);
			myWriter.write(numberOfNodes + "\n");
			myWriter.write(numberOfArcs + "\n");
			Random r = new Random();
			for (int i = 0; i < numberOfArcs; i++)
				myWriter.write(r.nextInt(numberOfNodes) + " " + r.nextInt(numberOfNodes) + "\n");
			myWriter.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// read instance from file
	public static void readInstanceandGenerateScenarios() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(path));
		numberOfNodes = Integer.valueOf(br.readLine());

		scenarios = new Graph[numberOfScenarios]; // create numberOfScenarios graphs

		for (int i = 0; i < numberOfScenarios; i++)
			Main.scenarios[i] = new Graph(numberOfNodes);

		int numberOfEdges = Integer.valueOf(br.readLine());
		String currentRow;

		for (int i = 0; i < numberOfEdges; i++) { // for each edge and for each graph, with probability equal to
													// "probability" add it in the graph
			currentRow = br.readLine();
			String nodes[] = currentRow.split(" ");
			int from = Integer.valueOf(nodes[0]);
			int to = Integer.valueOf(nodes[1]);
			for (int j = 0; j < numberOfScenarios; j++) {
				if (random.nextDouble() <= p) {
					scenarios[j].addArc(from, to);
				}
			}
		}
//
//		// print the graph
//		for (int i = 0; i < numberOfScenarios; i++)
//			scenarios[i].print();
	}

}
