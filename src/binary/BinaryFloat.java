package binary;

import exception.BinaryArrayException;
import java.util.Arrays;

/**
 *
 * @author Anderson Pessoa
 * @author Marcus Vinicius
 */
public class BinaryFloat extends BinaryInt {

    public int exp;

    public BinaryFloat(double x, int y) throws BinaryArrayException {
        super(y);
        this.bitSize = y;
        this.binaryNumber = toFloat(x, y);
        if (x > 0) {
            this.signed = false;
        }

    }

    public void rightShift() {
        for (int i = binaryNumber.length - 2; i >= 0; i--) {
            this.binaryNumber[i + 1] = this.binaryNumber[i];
        }
        this.binaryNumber[0] = false;
        this.exp++;
    }

    public void leftShift() {
        for (int i = 0; i <= binaryNumber.length - 2; i++) {
            this.binaryNumber[i] = this.binaryNumber[i + 1];
        }
        this.binaryNumber[binaryNumber.length - 1] = false;
        this.exp--;
    }

    public static void leftShift(boolean[] bin) {
        //System.out.println("teste leftShift statico antes: "+Arrays.toString(bin));
        for (int i = 0; i <= bin.length - 2; i++) {
            bin[i] = bin[i + 1];
        }
        bin[bin.length - 1] = false;
        //System.out.println("teste leftShift statico depois: "+Arrays.toString(bin));
    }

    public static void rightShift(boolean[] bin) {
        for (int i = bin.length - 2; i >= 0; i--) {
            bin[i + 1] = bin[i];
        }
        bin[0] = false;
    }

    public boolean[] toFloat(double x, int y) throws BinaryArrayException {
        int contAux = 0;
        int expA = 128;
        int intPart = (int) x;
        double floatPart = x - intPart;
        boolean[] floatPartA = new boolean[y];

        boolean[] intPartA = super.toBinary(intPart, y); //Parte inteira do binário
        boolean[] intPartB = new boolean[y];
        int count = 0;

        while (!intPartA[count]) { //Contador para ver quantos right shifts serão necessários aplicar para retornar o expoente.
            count++;
        }

        System.out.println(count);

        System.out.println("Antes: " + Arrays.toString(intPartA) + "   " + Arrays.toString(intPartB));

        count = y - count - 1;

        for (int i = count; i > 0; i--) {
            rightShift(intPartB);
            intPartB[0] = intPartA[y - 1];
            rightShift(intPartA);
            expA++;
            contAux++;
        }

        System.out.println("Depois: " + Arrays.toString(intPartA) + "   " + Arrays.toString(intPartB));

        count = 0;
        while (floatPart != 0 && count <= floatPartA.length - 1) { //Parte quebrada do binário
            //System.out.println(floatPart);
            floatPart *= 2;
            if (floatPart >= 1) {
                floatPartA[count] = true;
                floatPart -= 1;
            } else {
                floatPartA[count] = false;
            }
            count++;
        }

        System.out.println("Float Part Antes :" + Arrays.toString(floatPartA));

        for (int i = 0; i < expA - 128; i++) { //aplicando right shift na parte float para a soma
            rightShift(floatPartA);
        }

        System.out.println("Float Part Depois :" + Arrays.toString(floatPartA));

        BinaryInt sum1 = new BinaryInt(false, intPartB);
        BinaryInt sum2 = new BinaryInt(false, floatPartA);

        sum1 = sum1.sum(sum2); //Soma das 2 partes no mesmo nível de expoente.

        this.exp = expA;

        return sum1.binaryNumber;
    }

    public double floatToDouble() {
        int exp = this.exp;
        double result;
        double inteiro;
        double flutuante = 0;
        boolean[] intPart = new boolean[this.bitSize];
        intPart[this.bitSize - 1] = true;

        //System.out.println("Array Part flutuante Antes: "+Arrays.toString(this.binaryNumber));
        for (int i = 0; i < exp - 128; i++) {
            //.out.println(this.exp-128);
            leftShift(intPart);
            intPart[this.bitSize - 1] = this.binaryNumber[0];
            this.leftShift();
        }

        //System.out.println("Array Parte inteiro: "+Arrays.toString(intPart));
        //System.out.println("Array Parte flutuante Depois: "+Arrays.toString(this.binaryNumber));
        BinaryInt aux = new BinaryInt(this.signed, intPart);

        inteiro = (double) aux.toInt();

        for (int i = 0; i < this.bitSize - 1; i++) {
            if (this.binaryNumber[i]) {
                flutuante += Math.pow(0.5, (i + 1));
            }
        }

        result = inteiro + flutuante;

        System.out.println("Inteiro: " + inteiro);
        System.out.println("Flutuante: " + flutuante);

        return result;

    }
}
