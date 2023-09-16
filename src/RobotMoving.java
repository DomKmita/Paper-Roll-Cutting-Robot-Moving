import java.text.DecimalFormat;
import java.util.ArrayList;

public class RobotMoving {
    private double finalCost;
    private ArrayList<int[]> shortestPath;

    private double[] cost1;
    private double[] cost2;

    private int n;

    // I chose to not pass any parameters to the robot and pass them to the shortestPath function instead.
    RobotMoving(int n) {
        this.n = n;
        this.finalCost = 0.0; // initialise at first in case toString called with invalid n
        double[] cost1 = new double[]{1.1, 1.3, 2.5};
        double[] cost2 = new double[]{1.5, 1.2, 2.3};
        // this arraylist will store the shortest path taken
        shortestPath = new ArrayList<>();

        this.shortestPath(n, cost1);
        this.shortestPath(n, cost2);
    }

    private void shortestPath(int n, double[] cost) {

        // make sure n is in valid
        if (n < 1)
            return;
        //cost index 0 is for right move, 1 is for down and 2 is for across.

        // the path array stores the possible step choices. I needed it to calculate the path taken and not just the
        // energy cost of reaching the end.

        // path value 1 is right, 2 is down and 3 is across
        int[][] path = new int[n][n];

        // constructing matrix
        double[][] matrix = new double[n][n];

        // set first weight to 0 as it is the position of the robot
        matrix[0][0] = 0;

        // Initialize first row and first column
        for (int j = 1; j < n; j++) {
            matrix[0][j] = matrix[0][j - 1] + (cost[0]);
            path[0][j] = 1; // Set path direction to right
        }
        for (int i = 1; i < n; i++) {
            matrix[i][0] = matrix[i - 1][0] + (cost[1]);
            path[i][0] = 2; // Set path direction to down
        }

        // Fill in the rest of the matrix and path array
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {

                // using Math.min looked a little ugly so I decided to use these 3 variables to make the code
                // look more legible
                double right = matrix[i][j - 1] + cost[0];
                double down = matrix[i - 1][j] + cost[1];
                double across = matrix[i - 1][j - 1] +cost[2];

                if (across <= right && across <= down) {
                    matrix[i][j] = across; // set weight at the current position to across
                    path[i][j] = 3; // Set path direction to across
                } else if (right <= down) {
                    matrix[i][j] = right; // set weight at the current position to right
                    path[i][j] = 1; // Set path direction to right
                } else {
                    matrix[i][j] = down; // set weight at the current position to down
                    path[i][j] = 2; // Set path direction to down
                }
            }
        }

        // set final cost to energy consumption. Which is stored in the last position in the matrix.
        finalCost = matrix[n-1][n-1];



        // figuring out how to store the path was a bit tricky. Hence why I used a new matrix and an array list.

        int i = n - 1;
        int j = n - 1;
        // once one of the values is 0 we break.
        while (i > 0 || j > 0) {
            // We add the final co-ordinate to the arrayList.
            shortestPath.add(0, new int[]{i, j});

            // if the stored value is 3 then a diagonal move was used. Hence we reduce both i and j.
            if (path[i][j] == 3) {
                i--;
                j--;
                // if the stored value is 2 then the previous move was down. Hence we reduce j only.
            } else if (path[i][j] == 2) {
                i--;
                // if the stored value is 1 then the previous move was right. Hence we reduce i only.
            } else {
                j--;
            }
        }
        // add the start position to the arrayLIst
        shortestPath.add(0, new int[]{0, 0});

        System.out.println(this.toString(cost));
        shortestPath = new ArrayList<>(); // to clear array for further use.
    }


    // added these getters for completions sake and to aid in correcting/ testing.

    public double[] getCost1() {
        return cost1;
    }

    public double[] getCost2() {
        return cost2;
    }

    public int getN() {
        return n;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public ArrayList<int[]> getShortestPath() {
        return shortestPath;
    }

    @Override
    public String toString() {
        return toString(cost1); // default toString defaults to cost1;
    }

    public String toString(double[] cost) {
        StringBuilder txt = new StringBuilder();
        // add matrix size to return value
        txt.append("The matrix size is :")
                .append(n)
                .append("x")
                .append(n)
                .append("\n\n");

        // return error if matrix impossible
        if (n < 1)
            return txt.append("Please use a valid integer for n!\n\n") + "";

        // did a bit of a hack job to differentiate between the costs in the toString.
        txt.append("The cost used was: ");
        if (cost[0] == 1.1)
            txt.append("Cost 1\n\n");
        else
            txt.append("Cost 2\n\n");

        // needed to use decimal format for the toString as some of the weights were longer than 2 decimal places.
        DecimalFormat df = new DecimalFormat("#.##");
        // add final cost to print
        txt.append("The cost of the movement set was: ")
                .append(df.format(finalCost))
                .append("\nThe path taken was the following:\n");

        // add all co-ordinates to the string
        for (int i = 0; i < shortestPath.size(); i++) {
            int[] step = shortestPath.get(i);
            txt.append("(")
                    .append(step[0])
                    .append(",")
                    .append(step[1])
                    .append(") ");
        }
        // return resulting final cost and path taken
        return txt + "\n\n";
    }
}
