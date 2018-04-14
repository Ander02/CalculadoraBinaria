package binary;

import exception.BinaryArrayException;
import java.util.Arrays;

/**
 *
 * @author Anderson
 */
public class BinaryInt {

    public boolean signed;
    public boolean[] binaryNumberWithoutSignal;

    public BinaryInt(int x) { 
            this.signed = false;
            this.binaryNumberWithoutSignal = Util.toBinaryIntArray(Math.abs(x));
        
    }
    
    public BinaryInt (boolean[] binaryNumberWithoutSignal){
        this.signed = false;
        this.binaryNumberWithoutSignal = binaryNumberWithoutSignal;
    }

    public BinaryInt(boolean signed, boolean[] binaryNumberWithoutSignal) {
        this.signed = signed;
        this.binaryNumberWithoutSignal = binaryNumberWithoutSignal;
    }

    public int length() {
        return binaryNumberWithoutSignal.length + 1;
    }

    public boolean[] fullBynaryNumber() {
        boolean[] fullBinaryNumber = new boolean[this.length()];
        boolean[] aux = new boolean[fullBinaryNumber.length-1];

        fullBinaryNumber[0] = this.signed;
        for (int i = 1; i < fullBinaryNumber.length; i++) {
            fullBinaryNumber[i] = binaryNumberWithoutSignal[i - 1];
        }

        return fullBinaryNumber;
    }

    public BinaryInt sum(BinaryInt bin) {

        boolean[] normalized;
        boolean[] other;

        if (this.binaryNumberWithoutSignal.length > bin.binaryNumberWithoutSignal.length) {
            normalized = bin.normalize(this.binaryNumberWithoutSignal.length+1);
            other = this.fullBynaryNumber();
            normalized[0] = bin.signed; other[0] = this.signed;
        } else {
            normalized = this.normalize(bin.binaryNumberWithoutSignal.length+1);
            other = bin.fullBynaryNumber();
            normalized[0] = this.signed; other[0] = bin.signed;
        }

        System.out.println("O mesmo:    " + Arrays.toString(other));
        System.out.println("Normalizado " + Arrays.toString(normalized));

        boolean[] resp = new boolean[normalized.length];
        boolean carry = false;
        for (int i = normalized.length - 1; i >= 0; i--) {
            SumBitAux aux = new SumBitAux(normalized[i], other[i], carry);

            carry = aux.getCarry();
            resp[i] = aux.getResult();
        }

        
        boolean[] binAux = new boolean[resp.length-1];
        for (int i = 0; i < resp.length-1; i++) {
            binAux[i] = resp[i+1];
        }
        
        bin.signed = resp[0];
        bin.binaryNumberWithoutSignal = binAux;
        /*
        if (carry == true) {
          resp[0] = true;
            boolean[] resp2 = new boolean[resp.length + 1];
            resp2[0] = carry;

            for (int i = 1; i < resp.length; i++) {
                resp2[i] = resp[i - 1];
            }

            resp = resp2;
        }*/

        System.out.println("resposta  : " + Arrays.toString(resp));
        System.out.println("");
        return bin;
    }
    
    public BinaryInt sub(BinaryInt bin){
        boolean[] normalized;
        boolean[] other;
        
        BinaryInt aux = new BinaryInt (this.signed,this.binaryNumberWithoutSignal);
        
        

        if (this.binaryNumberWithoutSignal.length > bin.binaryNumberWithoutSignal.length) {
            normalized = bin.normalize(this.binaryNumberWithoutSignal.length+1);
            other = this.fullBynaryNumber();
            normalized[0] = bin.signed; other[0] = this.signed;
        } else {
            normalized = this.normalize(bin.binaryNumberWithoutSignal.length+1);
            other = bin.fullBynaryNumber();
            normalized[0] = this.signed; other[0] = bin.signed;
        }
        
        //if (Arrays.toString(normalized).equals(Arrays.toString(other)))
            //return new BinaryInt(0);
        
        bin = Util.complementoDeDois(bin);
        bin = bin.sum(aux);
        
        return bin;        
    }

    public boolean[] normalize(int length) {

        int originalLength = this.binaryNumberWithoutSignal.length;

        if (length == originalLength) {
            return this.binaryNumberWithoutSignal;
        }

        boolean[] normalizedBin = new boolean[length];
        int i;
        for (i = 0; i < length - originalLength; i++) {
            normalizedBin[i] = false;
        }
        for (int j = i; j < length; j++) {
            normalizedBin[j] = this.binaryNumberWithoutSignal[j - i];
        }

        return normalizedBin;
    }

    public static BinaryInt sum(BinaryInt bin1, BinaryInt bin2) {
        return null;
    }

    /**
     * @return The int value representation
     */
    public int toInt() {
        int toInt = 0;
        int length = this.binaryNumberWithoutSignal.length-1;
        
        for (int i = 0; i <= length ; i++) {
            if(this.binaryNumberWithoutSignal[i]){
                toInt += (int) Math.pow(2,(length-i));
            }
        }
        return toInt;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.fullBynaryNumber());
    }

}
