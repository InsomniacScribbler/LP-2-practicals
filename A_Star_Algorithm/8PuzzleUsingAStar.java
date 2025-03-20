import java.util.*;

class Node {
    private int[][] matrix;
    private int g_x;
    private int h_x;
    private int f_x;
    private int[][] goal;
    private int n;
    
    public Node(int[][] matrix, int g_x, int[][] goal) {
        this.matrix = matrix;
        this.g_x = g_x;
        this.h_x = 0;
        this.f_x = 0;
        this.goal = goal;
        this.n = 3;
        calculate_h_x();
        calculate_f_x();
    }
    
    public int[][] getMatrix() {
        return matrix;
    }
    
    public int getF_x() {
        return f_x;
    }
    
    public int getH_x() {
        return h_x;
    }
    
    public int[] locateZero() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
    
    public List<Node> generateChildren() {
        int[] zeroPos = locateZero();
        int x = zeroPos[0];
        int y = zeroPos[1];
        int[][] possible = {
            {x+1, y},
            {x, y+1},
            {x-1, y},
            {x, y-1}
        };
        
        List<Node> children = new ArrayList<>();
        for (int[] pos : possible) {
            int i = pos[0];
            int j = pos[1];
            if (i >= 0 && i < n && j >= 0 && j < n) {
                int[][] child = deepCopy(matrix);
                int temp = child[x][y];
                child[x][y] = child[i][j];
                child[i][j] = temp;
                children.add(new Node(child, g_x + 1, goal));
            }
        }
        return children;
    }
    
    private int[][] deepCopy(int[][] original) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }
    
    private void calculate_h_x() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (goal[i][j] != matrix[i][j]) {
                    h_x += 1;
                }
            }
        }
    }
    
    private void calculate_f_x() {
        f_x = g_x + h_x;
    }
    
    public void printNode() {
        System.out.println("g(x) = " + g_x);
        System.out.println("h(x) = " + h_x);
        System.out.println("f(x) = " + f_x);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return Arrays.deepEquals(matrix, node.matrix);
    }
    
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(matrix);
    }
}

class Puzzle {
    private List<Node> visited;
    private List<Node> expanded;
    private int n;
    private int[][] initial;
    private int[][] goal;
    private Scanner scanner;
    
    public Puzzle() {
        visited = new ArrayList<>();
        expanded = new ArrayList<>();
        n = 3;
        initial = new int[n][n];
        goal = new int[n][n];
        scanner = new Scanner(System.in);
    }
    
    public void input(int[][] variable) {
        System.out.println("Enter state.\n0 represents blank tile");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                variable[i][j] = scanner.nextInt();
            }
        }
    }
    
    public void solve() {
        List<int[][]> matrices = new ArrayList<>();
        System.out.println("Enter initial state");
        input(initial);
        System.out.println("Enter goal state");
        input(goal);
        System.out.println("\nSTART");
        
        Node current = new Node(initial, 0, goal);
        matrices.add(current.getMatrix());
        expanded.add(current);
        
        while (true) {
            current = expanded.remove(0);
            if (current.getH_x() == 0) {
                break;
            }
            current.printNode();
            
            for (Node child : current.generateChildren()) {
                boolean found = false;
                for (int[][] matrix : matrices) {
                    if (Arrays.deepEquals(child.getMatrix(), matrix)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    matrices.add(child.getMatrix());
                    expanded.add(child);
                }
            }
            visited.add(current);
            expanded.sort(Comparator.comparingInt(Node::getF_x));
        }
        current.printNode();
    }
    
    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle();
        puzzle.solve();
    }
}
