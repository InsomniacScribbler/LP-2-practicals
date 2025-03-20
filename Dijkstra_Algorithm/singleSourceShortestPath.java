import java.util.*;

public class Dijkstra {
    private int vertices;
    private int[][] graph;
    private int edges;
    private Scanner scanner;
    
    public Dijkstra() {
        scanner = new Scanner(System.in);
        System.out.print("Enter number of vertices: ");
        this.vertices = scanner.nextInt();
        this.graph = new int[vertices][vertices];
        
        // Initialize graph with infinity values
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                graph[i][j] = Integer.MAX_VALUE;
            }
        }
        
        System.out.print("Enter number of edges: ");
        this.edges = scanner.nextInt();
        System.out.println("Start\tEnd\tDistance");
        
        for (int i = 0; i < edges; i++) {
            int v1 = scanner.nextInt();
            int v2 = scanner.nextInt();
            int e = scanner.nextInt();
            graph[v1-1][v2-1] = e;
            graph[v2-1][v1-1] = e;  // For undirected graph
        }
        
        // Set diagonal elements to zero
        for (int i = 0; i < vertices; i++) {
            graph[i][i] = 0;
        }
    }
    
    public void printMatrix() {
        System.out.print("\t");
        for (int i = 0; i < vertices; i++) {
            System.out.print((i+1) + "\t");
        }
        System.out.println();
        
        for (int i = 0; i < vertices; i++) {
            System.out.print((i+1) + "\t");
            for (int j = 0; j < vertices; j++) {
                if (graph[i][j] == Integer.MAX_VALUE) {
                    System.out.print("∞\t");
                } else {
                    System.out.print(graph[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }
    
    public void solve() {
        System.out.print("Enter starting node: ");
        int source = scanner.nextInt();
        
        Set<Integer> visited = new HashSet<>();
        int[] distances = new int[vertices];
        
        // Initialize distances with infinity
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source - 1] = 0;
        
        while (true) {
            int minDist = Integer.MAX_VALUE;
            int minId = -1;
            
            // Find unvisited vertex with minimum distance
            for (int i = 0; i < vertices; i++) {
                if (distances[i] < minDist && !visited.contains(i)) {
                    minDist = distances[i];
                    minId = i;
                }
            }
            
            // If no more vertices to visit, break
            if (minId == -1) {
                break;
            }
            
            // Update distances of adjacent vertices
            for (int i = 0; i < vertices; i++) {
                if (graph[minId][i] != Integer.MAX_VALUE) {
                    int newDist = minDist + graph[minId][i];
                    if (distances[i] > newDist) {
                        distances[i] = newDist;
                    }
                }
            }
            
            visited.add(minId);
        }
        
        System.out.println("Distances from source " + source + ":");
        for (int i = 0; i < vertices; i++) {
            if (distances[i] == Integer.MAX_VALUE) {
                System.out.println("Distance to " + (i+1) + ": ∞");
            } else {
                System.out.println("Distance to " + (i+1) + ": " + distances[i]);
            }
        }
    }
    
    public static void main(String[] args) {
        Dijkstra solver = new Dijkstra();
        solver.printMatrix();
        solver.solve();
    }
}
