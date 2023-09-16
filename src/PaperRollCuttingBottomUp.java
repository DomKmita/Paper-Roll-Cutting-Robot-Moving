public class PaperRollCuttingBottomUp {

    private double[] revenue;
    private int rollLength;

    private int[] possibleLengths;
    private int[] rollCuts;
    private double[] prices;

    PaperRollCuttingBottomUp(int rollLength) {
        possibleLengths = new int[]{1, 2, 3, 5};
        prices = new double[]{1.2, 3.0, 5.8, 10.1};
        this.rollLength = rollLength;
    }

    public void cut() {

        if (rollLength < 1) {
            return;
        }

        // these had to be instantiated in the cut function due to a NegativeArraySizeException
        // I could have a try and catch in the constructor but that is bad practice.
        revenue = new double[rollLength + 1];
        rollCuts = new int[rollLength + 1];

        // store 0 in first position
        revenue[0] = 0;
        int [] lastRollUsed = new int[rollLength + 1];
        int i, j;

        // iterate over values of n
        for (j = 1; j <= rollLength; j++){

            // create var for max revenue value
            double max_value = Double.MIN_VALUE; // set to arbitrarily low value
            int bestRollLen = 0; // used to store the rod sizes

            // we need to iterate twice to compare all values against each other
            for (i = 0; i < possibleLengths.length; i++) { // iterate only up to end possibleLengths array
                if (j - possibleLengths[i] >= 0) {
                    if (max_value < prices[i] + revenue[j - possibleLengths[i]]) {
                        max_value = prices[i] + revenue[j - possibleLengths[i]];
                        bestRollLen = possibleLengths[i];
                    }
                }
            }
            // once max value found we set it to new position in array
            revenue[j] = max_value;
            // store the used length in an array
            lastRollUsed[j] = bestRollLen;
        }

        j = 0;
        // all possible lengths were stored in the above array so we need
        // to prune it to calculate the actual cuts used to find the max value
        for (i = rollLength; i > 0; i -= lastRollUsed[i])
            rollCuts[j++] = lastRollUsed[i];
    }

    // returns revenue array
    public double[] getRevenue() {
        return revenue;
    }

    // return max value of cuts
    public double getMaxVal() {
        return revenue[rollLength];
    }

    // returns the cuts used for max value
    public int[] getRollCuts() {
        return rollCuts;
    }

    public double[] getPrices() {
        return prices;
    }

    // prints out roll cuts used and max value
    @Override
    public String toString() {


        StringBuilder txt = new StringBuilder();
        txt.append("Starting roll length: ")
                .append(rollLength)
                .append("\n\n");

        if (rollLength < 1)
            return txt.append("Please ensure valid roll lengths are used!\n") + "";


        for (int i = 0; i < prices.length; i++)
            txt.append("Length: ")
                    .append(possibleLengths[i])
                    .append(", Price: ")
                    .append(prices[i])
                    .append("\n");

        txt.append("\nThe roll lengths used are: ");
        for (int i = 0; i < rollCuts.length; i++) {
            if (rollCuts[i] == 0)
                break;
            txt.append(rollCuts[i])
                    .append(" ");
        }
        txt.append("\nThe max value is: ")
                .append(revenue[rollLength])
                .append("\n");

        return txt.toString();
    }
}
