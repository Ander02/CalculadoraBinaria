package main;

import binary.BinaryInt;
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

        /*for (int i = 0; i < 10; i++) {
            BinaryInt bin = new BinaryInt(i);
            bin.sum(new BinaryInt(1)).toInt();
        }*/
        
        BinaryInt bin = new BinaryInt(5);
        BinaryInt test = Util.complementoDeUm(bin);
        BinaryInt test2 = Util.complementoDeDois(bin);
        
        System.out.println("Array Original:   "+Arrays.toString(bin.fullBynaryNumber()));
        System.out.println("Complemento de 1: "+Arrays.toString(test.fullBynaryNumber()));
        System.out.println("Complemento de 2: "+Arrays.toString(test2.fullBynaryNumber()));
        System.out.println("Numero Original : "+bin.toInt());

        //System.out.println(soma);
        //System.out.println(soma.sum(bin3));
    }
}
