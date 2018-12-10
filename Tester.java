import java.io.IOException;

public class Tester {
    public static void main(String args[]) throws IOException {
        String[] startingNode = {"a", "Folsum", "a", "h", "a", "f", "a", "e", "b", "a"};
        for(int i = 1; i < 11; i++){
            String fileName = "Graph" + Integer.toString(i) + ".txt";
            Graph graph = new Graph();
            graph.input(fileName);
            System.out.println("\n" + fileName + "Input Results");
            graph.output();
            System.out.println("\n" + fileName + " Test BFS -------- Aleksandra Trifonova \n========");
            graph.output_bfs(graph.findVertex(startingNode[i - 1]));
            System.out.println("\n" + fileName + " Test DFS -------- Aleksandra Trifonova\n=========");
            graph.output_dfs(graph.findVertex(startingNode[i - 1]));
        }
    }
}
