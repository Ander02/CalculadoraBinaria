package main;

import binary.SumBitAux;
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
        /*
        System.out.println(new SumBitAux(false, false, false));
        System.out.println("0+0+0");
        System.out.println(new SumBitAux(false, false, true));
        System.out.println("0+0+1");
        System.out.println(new SumBitAux(false, true, false));
        System.out.println("0+1+0");
        System.out.println(new SumBitAux(false, true, true));
        System.out.println("0+1+1");
        System.out.println(new SumBitAux(true, false, false));
        System.out.println("1+0+0");
        System.out.println(new SumBitAux(true, false, true));
        System.out.println("1+0+1");
        System.out.println(new SumBitAux(true, true, false));
        System.out.println("1+1+0");
        System.out.println(new SumBitAux(true, true, true));
        System.out.println("1+1+1");
         */
        System.out.println(Arrays.toString(Util.toBinaryIntArray(3)));
        System.out.println(Arrays.toString(Util.toBinaryIntArray(-3)));

    }
}
