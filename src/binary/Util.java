package binary;

import java.util.Arrays;

/**
 *
 * @author Anderson
 */
public class Util {

    /**
     *
     * @param num1
     * @param num2
     * @return
     */
    public static int max(int num1, int num2) {
        if (num1 >= num2) {
            return num1;
        } else {
            return num2;
        }
    }

    /**
     *
     * @param num A õint numbem
     * @return A char array with the binary representation
     */
    public static char[] toBinaryCharArray(int num) {
        return Integer.toBinaryString(num).toCharArray();
    }

    public static boolean[] toBinaryIntArray(char[] binaryCharArray) {
        boolean[] binaryArray = new boolean[binaryCharArray.length];

        for (int i = 0; i < binaryCharArray.length; i++) {
            switch (binaryCharArray[i]) {
                case '1':
                    binaryArray[i] = true;
                    break;
                case '0':
                    binaryArray[i] = false;
                    break;
                default:
                    System.out.println("Deu merda");
                    break;
            }
        }

        return binaryArray;
    }

    public static boolean[] toBinaryIntArray(int num) {
        char[] binaryCharArray = toBinaryCharArray(num);
        return toBinaryIntArray(binaryCharArray);
    }

     
    

}
