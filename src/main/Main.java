package main;

import binary.BinaryInt;
import binary.IntDivisionResult;
import exception.BinaryArrayException;
import binary.SumBitAux;
import binary.Util;
import java.util.Arrays;
import exception.BinaryArrayException;
import binary.BinaryFloat;

/**
 *
 * @author Anderson
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws exception.BinaryArrayException
     */
    public static void main(String[] args) throws BinaryArrayException {
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
        //BinaryInt test = Util.complementoDeUm(bin);
        //BinaryInt test2 = Util.complementoDeDois(bin);
        BinaryInt bin = new BinaryInt(73, 7);

        BinaryInt bin2 = new BinaryInt(-8, 7);
        //System.out.println(bin2 + ": " + bin2.toInt());
        BinaryInt res;
        IntDivisionResult divRes;
        try {
            res = bin.sum(bin2);
            System.out.println("Depois da soma " + res + " " + res.toInt());

            res = bin.sub(bin2);
            System.out.println("Depois da subtração " + res + " " + res.toInt());
            res = bin.mult(bin2);
            System.out.println("Depois da multiplicação " + res + " " + res.toInt());
            divRes = bin.div(bin2);
            System.out.println(divRes);
            //res = bin.mult(bin2);
            //System.out.println("Depois da multiplicação " + res + " " + res.toInt());
        } catch (Exception ex) {
            //System.out.println("Valor Somado: "+Arrays.toString(bin.fullBynaryNumber()));
            ex.printStackTrace();
        }

        //System.out.println("Array Original:   "+Arrays.toString(bin.fullBynaryNumber()));
        //System.out.println("Teste de Subtração :"+Arrays.toString(sub.fullBynaryNumber()));
        //System.out.println("Complemento de 1: "+Arrays.toString(test.fullBynaryNumber()));
        //System.out.println("Complemento de 2: "+Arrays.toString(test2.fullBynaryNumber()));
        //System.out.println("Resposta : "+sub.toInt());
        //System.out.println(soma);
        //System.out.println(soma.sum(bin3));
        /*boolean[] bin;
        try {
            bin = BinaryInt.toBinary(255,8);
            System.out.println(Arrays.toString(bin));
            bin = BinaryInt.toBinary(256, 9);
            System.out.println(Arrays.toString(bin));
            bin = BinaryInt.toBinary(256, 8);
            System.out.println(Arrays.toString(bin));
        }
        
        catch (BinaryArrayException ex){
                System.out.println(ex.message);
                }*/
    }
}

/* To do list:
        Escrever os códigos da multiplicação e divisão. (Ainda tenho que ver oq fazer até lá)
        Terminar a classe do binary float (modificar ela e sobreescrever parte de alguns métodos e ajustar para usar os métodos implementados.)
        PS. Não encontrei nenhum problema nos métodos com os números que eu testei no momento, o NULL pointer exception com numeros negativos se da
            pelo fato de um arranjo não ser iniciado no construtor pela falta do complemento de 2 não ter sido ajustado ainda.
 */
