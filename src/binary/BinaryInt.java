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
        if (x >= 0) {
            this.signed = false;
            this.binaryNumberWithoutSignal = Util.toBinaryIntArray(x);
        } else {
            this.signed = true;
            //this.binaryNumberWithoutSignal = Util.complementoDeDois(Util.toBinaryIntArray(Math.abs(x)));
        }
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
            normalized = bin.normalize(this.binaryNumberWithoutSignal.length);
            other = this.binaryNumberWithoutSignal;
        } else {
            normalized = this.normalize(bin.binaryNumberWithoutSignal.length);
            other = bin.binaryNumberWithoutSignal;
        }

        System.out.println("O mesmo:    " + Arrays.toString(other));
        System.out.println("Normalizado " + Arrays.toString(normalized));

        boolean[] resp = new boolean[normalized.length + 1];
        boolean carry = false;
        for (int i = normalized.length - 1; i >= 0; i--) {
            SumBitAux aux = new SumBitAux(normalized[i], other[i], carry);

            carry = aux.getCarry();
            resp[i + 1] = aux.getResult();
        }

        resp[0] = carry;
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
        return new BinaryInt(false, resp);
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
        //Não funciona
        int value = 0;

        int i = this.binaryNumberWithoutSignal.length - 1;
        while (i >= 0) {

            if (this.binaryNumberWithoutSignal[i] == true) {

                value += (int) Math.pow(2, i);
            }
            i--;
            System.out.println(value);
        }

        return value;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.fullBynaryNumber());
    }

}
