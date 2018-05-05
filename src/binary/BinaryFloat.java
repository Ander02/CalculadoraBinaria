/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binary;

import exception.BinaryArrayException;
import java.util.Arrays;

/**
 *
 * @author Vera
 */
public class BinaryFloat extends BinaryInt {

    public int exp;

    public BinaryFloat(double x,int y) throws BinaryArrayException {        
        super(y);
        this.bitSize = y;
        this.binaryNumber = toFloat(x,y);
        if (x>0){
            this.signed = false;
        }
        
    }

    public void righShift() {
        for (int i = binaryNumber.length - 2; i < 0; i++) {
            this.binaryNumber[i + 1] = this.binaryNumber[i];
        }
        this.binaryNumber[0] = false;
        this.exp++;
    }

    public void leftShift() {
        for (int i = binaryNumber.length - 2; i < 0; i++) {
            this.binaryNumber[i] = this.binaryNumber[i + 1];
        }
        this.binaryNumber[binaryNumber.length - 1] = false;
        this.exp--;
    }

    public void rightShift(boolean[] bin) {
        for (int i = bin.length - 2; i < 0; i++) {
            bin[i + 1] = bin[i];
        }
        bin[0] = false;
    }

    public boolean[] toFloat(double x, int y) throws BinaryArrayException {
        int expA = 128;
        int intPart = (int) x;
        double floatPart = x - intPart;
        boolean[] floatPartA = new boolean[y];
        
        boolean[] intPartA = BinaryInt.toBinary(intPart,y); //Parte inteira do binário
        boolean[] intPartB = new boolean[y];
        int count = 0;

        while (!intPartA[count]) { //Contador para ver quantos right shifts serão necessários aplicar para retornar o expoente.
            count++;
    }
        for (int i = count; i >= 0; i--) {
            intPartB[i] = intPartA[count-i];
            rightShift(intPartA);
            expA++;
        }
        
        count = 0;
        while(floatPart!=0&&count<=floatPartA.length-1){ //Parte quebrada do binário
            System.out.println(floatPart);
            floatPart *= 2;
                if(floatPart>=1){
                    floatPartA[count] = true;
                    floatPart -= 1;
                }
                else{
                    floatPartA[count] = false;
                }
                count++;
        }

        for (int i = 0; i < expA - 128; i++) { //aplicando right shift na parte float para a soma
            rightShift(floatPartA);
        }

        BinaryInt sum1 = new BinaryInt(false, intPartA, y);
        BinaryInt sum2 = new BinaryInt(false, floatPartA, y);

        sum1 = sum1.sum(sum2); //Soma das 2 partes no mesmo nível de expoente.
        
        this.exp = expA;        
        
        return sum1.binaryNumber;        
    }
}
