public class Main {
    public static void main(String[] args) {
        // range of values to be tested
       int[] roll_lengths = new int[]{5, 3, 1, 0, 7, -4, 10, 33, 9};

       System.out.println("PAPER CUTTING ALGORITHM");
       System.out.println("-----------------------");
       for (int i = 0; i < roll_lengths.length; i++) {
           PaperRollCuttingBottomUp cuttingAlgorithm = new PaperRollCuttingBottomUp(roll_lengths[i]);
           cuttingAlgorithm.cut();
           System.out.println(cuttingAlgorithm.toString());
       }

        System.out.println("\n\n\nROBOT MOVING ALGORITHM");
        System.out.println("----------------------");
        // range of values to be tested
        int[] n_values = new int[]{5, 1, -3, 10, 6, 0, 3};

        for (int i = 0; i < n_values.length; i++) {
            RobotMoving robot = new RobotMoving(n_values[i]);
        }




    }
}