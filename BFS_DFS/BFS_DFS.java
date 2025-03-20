import java.util.*;

public class Graph {
    private int n;
    private Map<Integer, List<Integer>> nodes;
    
    public Graph() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of nodes: ");
        this.n = scanner.nextInt();
        this.nodes = new HashMap<>();
        for (int i = 0; i < this.n; i++) {
            nodes.put(i+1, new ArrayList<>());
        }
    }
    
    public void addEdge(int start, int end) {
        nodes.get(start).add(end);
        nodes.get(end).add(start);
    }
    
    public void printGraph() {
        for (Map.Entry<Integer, List<Integer>> entry : nodes.entrySet()) {
            int node = entry.getKey();
            List<Integer> adjacentNodes = entry.getValue();
            System.out.print(node + " -> ");
            for (int vertex : adjacentNodes) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        }
    }
    
    private void dfsHelper(int current, boolean[] visited) {
        System.out.print(current + " ");
        visited[current] = true;
        for (int adjacentNode : nodes.get(current)) {
            if (!visited[adjacentNode]) {
                dfsHelper(adjacentNode, visited);
            }
        }
    }
    
    public void dfs(int start) {
        boolean[] visited = new boolean[n + 1];
        System.out.print("DFS from " + start + ": ");
        dfsHelper(start, visited);
        System.out.println();
    }
    
    private void bfsHelper(Deque<Integer> queue, boolean[] visited) {
        if (queue.isEmpty()) {
            return;
        }
        int current = queue.pollFirst();
        System.out.print(current + " ");
        
        for (int adjacentNode : nodes.get(current)) {
            if (!visited[adjacentNode]) {
                queue.add(adjacentNode);
                visited[adjacentNode] = true;
            }
        }
        bfsHelper(queue, visited);
    }
    
    public void bfs(int start) {
        Deque<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n + 1];
        System.out.print("BFS from " + start + ": ");
        queue.add(start);
        visited[start] = true;
        bfsHelper(queue, visited);
        System.out.println();
    }
    
    public static void main(String[] args) {
        Graph g = new Graph();
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(5, 2);
        g.addEdge(5, 3);
        g.addEdge(5, 4);
        g.printGraph();
        g.dfs(3);
        g.bfs(1);
    }
}
