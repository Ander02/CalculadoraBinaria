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

        int value = 5;
        int[] binaryArray = toBinaryBoolArray(value);

        System.out.println("Hello World");
        for(int item : binaryArray){
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

    public static int[] toBinaryArray(char[] binaryCharArray) {
        int[] binaryArray = new int[binaryCharArray.length];

        for (int i = 0; i < binaryCharArray.length; i++) {
            switch (binaryCharArray[i]) {
                case '1':
                    binaryArray[i] = 1;
                    break;
                case '0':
                    binaryArray[i] = 0;
                    break;
                default:
                    System.out.println("Deu merda");
                    break;
            }
        }

        return binaryArray;
    }

    public static int[] toBinaryBoolArray(int num) {
        char[] binaryCharArray = toBinaryCharArray(num);
        return toBinaryArray(binaryCharArray);
    }
    
    public static int[] complementoDeUm(int [] binaryCharArray){
        int [] result = new int[binaryCharArray.length];
        
        for (int i = 0; i < binaryCharArray.length; i++) {
            if (binaryCharArray[i]==0) result[i]=1;
            else result[i]=0;
        }
        
        return result;
        
    }
    
    public static int[] complementoDeDois (int [] binary){
            int [] aux = new int[binary.length];
            int [] one = new int[binary.length];
            
            for (int i = 0; i < (one.length-1); i++) {
                one[i] = 0;
        }
            one[(one.length-1)] = 1;
            
            aux = complementoDeUm(binary);
            aux = sumBin(aux,one);
            
            return aux;
    }
}
