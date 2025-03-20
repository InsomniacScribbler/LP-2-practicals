import java.util.*;
import java.text.DecimalFormat;

public class EmployeeEvaluation {
    private String name;
    private Map<String, double[]> competencies;
    private Map<String, double[]> performance;
    private Scanner scanner;
    
    public EmployeeEvaluation() {
        scanner = new Scanner(System.in);
        System.out.print("Enter name of employee: ");
        this.name = scanner.nextLine();
        
        // Initialize competencies map with default values [rating, weightage, weighted score]
        this.competencies = new LinkedHashMap<>();
        competencies.put("Communication", new double[]{0, 0, 0});
        competencies.put("Productivity", new double[]{0, 0, 0});
        competencies.put("Creativity", new double[]{0, 0, 0});
        competencies.put("Integrity", new double[]{0, 0, 0});
        competencies.put("Punctuality", new double[]{0, 0, 0});
        
        // Initialize performance map with default values [rating, weightage, weighted score]
        this.performance = new LinkedHashMap<>();
        performance.put("Goal 1", new double[]{0, 0, 0});
        performance.put("Goal 2", new double[]{0, 0, 0});
        performance.put("Goal 3", new double[]{0, 0, 0});
        performance.put("Goal 4", new double[]{0, 0, 0});
        performance.put("Goal 5", new double[]{0, 0, 0});
    }
    
    public void printTable(Map<String, double[]> hashMap) {
        if (hashMap == competencies) {
            System.out.println("Competency Goals");
            System.out.println("Competency\t\tRating\tWeightage\tWeighted Score");
            for (Map.Entry<String, double[]> entry : competencies.entrySet()) {
                double[] value = entry.getValue();
                System.out.println(entry.getKey() + "\t\t" + (int)value[0] + "\t" + (int)value[1] + "\t\t" + value[2]);
            }
            System.out.println();
        } else {
            System.out.println("Performance Goals");
            System.out.println("Goals\t\tRating\tWeightage\tWeighted Score");
            for (Map.Entry<String, double[]> entry : performance.entrySet()) {
                double[] value = entry.getValue();
                System.out.println(entry.getKey() + "\t\t" + (int)value[0] + "\t" + (int)value[1] + "\t\t" + value[2]);
            }
            System.out.println();
        }
    }
    
    public void input() {
        System.out.println("Enter rating from 1-3");
        System.out.println("Weightage should be equal to 100");
        int weightTotal = 0;
        
        // Input for competencies
        for (String key : competencies.keySet()) {
            System.out.print("Enter rating for " + key + ": ");
            competencies.get(key)[0] = scanner.nextInt();
            System.out.print("Enter weightage(" + (100 - weightTotal) + " remaining): ");
            competencies.get(key)[1] = scanner.nextInt();
            weightTotal += competencies.get(key)[1];
        }
        
        // Input for performance goals
        for (String key : performance.keySet()) {
            System.out.print("Enter rating for " + key + ": ");
            performance.get(key)[0] = scanner.nextInt();
            System.out.print("Enter weightage(" + (100 - weightTotal) + " remaining): ");
            performance.get(key)[1] = scanner.nextInt();
            weightTotal += performance.get(key)[1];
        }
    }
    
    public void calcScore() {
        // Calculate weighted scores for competencies
        for (String key : competencies.keySet()) {
            double[] values = competencies.get(key);
            values[2] = values[0] * values[1] / 100;
        }
        
        // Calculate weighted scores for performance goals
        for (String key : performance.keySet()) {
            double[] values = performance.get(key);
            values[2] = values[0] * values[1] / 100;
        }
    }
    
    public void calculate() {
        input();
        System.out.println();
        calcScore();
        printTable(competencies);
        
        double sumCompetency = 0;
        for (String key : competencies.keySet()) {
            sumCompetency += competencies.get(key)[2];
        }
        System.out.println("Sum of weighted scores-Competency = " + sumCompetency);
        System.out.println();
        
        double sumPerformance = 0;
        printTable(performance);
        for (String key : performance.keySet()) {
            sumPerformance += performance.get(key)[2];
        }
        System.out.println("Sum of weighted scores-Performance = " + sumPerformance);
        System.out.println();
        
        double total = sumCompetency + sumPerformance;
        DecimalFormat df = new DecimalFormat("0.00");
        
        if (total >= 2.7) {
            System.out.println("Overall Rating of " + name + " (out of 3): " + df.format(total));
            System.out.println("Employee Exceeds expectations");
        } else if (total >= 1.7 && total < 2.7) {
            System.out.println("Overall Rating of " + name + " (out of 3): " + df.format(total));
            System.out.println("Employee meets expectations");
        } else {
            System.out.println("Overall Rating of " + name + " (out of 3): " + df.format(total));
            System.out.println("Employee fails expectations");
        }
    }
    
    public static void main(String[] args) {
        EmployeeEvaluation obj = new EmployeeEvaluation();
        obj.calculate();
    }
}
