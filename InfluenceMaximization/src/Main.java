import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws IOException {

		ArrayList<Integer> resultBasic = new Solver().solveBasic("src/Dataset/Graph4.txt", 2000, 5, 0.6);
		ArrayList<Integer> resultRandom = new Solver().solveRandom("src/Dataset/Graph4.txt", 2);
		ArrayList<Integer> resultLazy = new Solver().solveLazy("src/Dataset/Graph4.txt", 2000, 5, 0.6);

		System.out.println(resultBasic);
		System.out.println(resultRandom);
		System.out.println(resultLazy);

		// generateRandomGraph(20, 40, "src/Dataset/Graph4.txt");

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

}
