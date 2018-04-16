package main;

import binary.BinaryInt;
import binary.SumBitAux;
import binary.Util;
import exception.BinaryOverflowException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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
//
//        for (int i = 0; i < 20; i++) {
//            BinaryInt bin = new BinaryInt(i);
//            try {
//                BinaryInt res = bin.sum(new BinaryInt(i));
//                System.out.println("Resposta: " + res.toString() + ": " + res.toInt());
//                System.out.println("");
//            } catch (BinaryOverflowException ex) {
//                System.out.println(ex);
//            }
//        }

        BinaryInt bin1 = new BinaryInt(5);
        BinaryInt bin2 = new BinaryInt(2);

        BinaryInt resp = bin1.sub(bin2);
        System.out.println(resp + ": " + resp.toInt());
    }

}

/* To do list:
        Corrigir a subtração, atualmente não funciona.
        Escrever o código da subtração. ( Basicamente é uma soma com o segundo int sendo jogado num novo construtor com o valor negativo e chamar a soma.)
        Escrever os códigos da multiplicação e divisão. (Ainda tenho que ver oq fazer até lá)
        Terminar a classe do binary float (modificar ela e sobreescrever parte de alguns métodos e ajustar para usar os métodos implementados.)

        PS. Não encontrei nenhum problema nos métodos com os números que eu testei no momento, o NULL pointer exception com numeros negativos se da
            pelo fato de um arranjo não ser iniciado no construtor pela falta do complemento de 2 não ter sido ajustado ainda.
 */
