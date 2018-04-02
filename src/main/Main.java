package main;

import binary.Util;
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
        int[] binaryArray = Util.toBinaryIntArray(value);

        System.out.println("Hello World");
        for (int item : binaryArray) {
            System.out.print(item + " ");
        }
        System.out.println("");
        System.out.println(Arrays.toString(binaryArray));
    }
}
