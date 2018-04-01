package main;

import java.util.Arrays;

/**
 *
 * @author Anderson
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int value = 1032;
        boolean[] binaryArray = toBinaryBoolArray(value);

        System.out.println("Hello World");
        for(boolean item : binaryArray){
            System.out.print(item + " ");
        }
        System.out.println(Arrays.toString(binaryArray));
    }

    /**
     *
     * @param bin1 The first binary number
     * @param bin2 The seconde binary number
     * @return The String with the sum between bin1 and bin2
     */
    public static boolean[] sumBin(boolean[] bin1, boolean[] bin2) {

        boolean[] sumBinaryArray = new boolean[bin1.length + bin2.length + 1];
        
        boolean prox = false;
        for (int i = 0; i < sumBinaryArray.length; i++) {
            boolean[] sum = sumBit(bin1[i], bin2[i]);
            prox = sum[0];
            sumBinaryArray[i] = sum[1];

        }

        return null;
    }

    public static boolean[] sumBit(boolean bit1, boolean bit2) {

        if (bit1 == true && bit2 == true) {
            return new boolean[]{true, false};
        } else if ((bit1 == true && bit2 == false) || (bit1 == false && bit2 == true)) {
            return new boolean[]{false, true};
        } else if (bit1 == false && bit2 == false) {
            return new boolean[]{false, false};
        }   
        return new boolean[]{false, false};
    }

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
     * @param num One int numbem
     * @return A char array with the binary representation
     */
    public static char[] toBinaryCharArray(int num) {
        return Integer.toBinaryString(num).toCharArray();
    }

    public static boolean[] toBinaryBoolArray(char[] binaryCharArray) {
        boolean[] binaryBoolArray = new boolean[binaryCharArray.length];

        for (int i = 0; i < binaryCharArray.length; i++) {
            switch (binaryCharArray[i]) {
                case '1':
                    binaryBoolArray[i] = true;
                    break;
                case '0':
                    binaryBoolArray[i] = false;
                    break;
                default:
                    System.out.println("Deu merda");
                    break;
            }
        }

        return binaryBoolArray;
    }

    public static boolean[] toBinaryBoolArray(int num) {
        char[] binaryCharArray = toBinaryCharArray(num);
        return toBinaryBoolArray(binaryCharArray);
    }

}
