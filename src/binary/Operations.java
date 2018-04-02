package binary;

/**
 *
 * @author Anderson
 */
public class Operations {
    
    /**
     *
     * @param bin1 The first binary number
     * @param bin2 The seconde binary number
     * @return The String with the sum between bin1 and bin2
     */
    public static int[] sumBin(int[] bin1, int[] bin2) {

        int[] sumBinaryArray = new int[bin1.length + bin2.length + 1]; //max entre os 2?

        int prox = 0;
        for (int i = 0; i < sumBinaryArray.length; i++) {
            int[] sum = sumBit(bin1[i], bin2[i]);
            prox = sum[0];
            sumBinaryArray[i] = sum[1];

        }

        return null;
    }

    public static int[] sumBit(int bit1, int bit2) {

        if (bit1 == 1 && bit2 == 1) {
            return new int[]{1, 0};
        } else if ((bit1 == 1 && bit2 == 0) || (bit1 == 0 && bit2 == 1)) {
            return new int[]{0, 1};
        } else if (bit1 == 0 && bit2 == 0) {
            return new int[]{0, 0};
        }
        return new int[]{0, 0};
    }

    
}
