package binary;

import exception.BinaryOverflowException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static BinaryInt complementoDeUm(BinaryInt binary) {
        boolean[] result = new boolean[binary.binaryNumberWithoutSignal.length];
        BinaryInt aux;

        for (int i = result.length - 1; i > 0; i--) {
            result[i] = !binary.binaryNumberWithoutSignal[i];

        }

        if (binary.signed) {
            aux = new BinaryInt(false, result);
        } else {
            aux = new BinaryInt(true, result);
        }

        return aux;
    }

    public static BinaryInt complementoDeDois(BinaryInt binary) {

        try {
            BinaryInt one = new BinaryInt(1);
            BinaryInt aux = complementoDeUm(binary);
            BinaryInt result = aux.sum(one);

            return result;
        } catch (BinaryOverflowException ex) {
            return null;
        }
    }

}
