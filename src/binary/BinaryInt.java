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

        if (this.length() >= bin.length()) {
            normalized = bin.normalize(this.length());
            other = this.fullBynaryNumber();
        } else {
            normalized = this.normalize(bin.length());
            other = bin.fullBynaryNumber();
        }

        int i = normalized.length - 1;
        boolean carry = false;
        boolean[] resp = new boolean[normalized.length];
        do {

            SumBitAux aux = new SumBitAux(normalized[i], other[i], carry);

            carry = aux.getCarry();
            resp[i] = aux.getResult();
            i--;
        } while (i >= 0);

        return new BinaryInt(false, resp);
    }

    public boolean[] normalize(int length) {

        if (length == this.length()) {
            return this.fullBynaryNumber();
        }

        boolean[] normalizedBin = new boolean[length];

        int i;
        for (i = 0; i < length - this.length(); i++) {
            normalizedBin[i] = false;
        }

        for (int j = i; j < length; j++) {
            System.out.println("i " + i);
            System.out.println("j " + j);
            normalizedBin[j] = this.binaryNumberWithoutSignal[j - i];
        }

        return normalizedBin;
    }

    public static BinaryInt sum(BinaryInt bin1, BinaryInt bin2) {
        return null;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.fullBynaryNumber());
    }

}
