package binary;

import exception.BinaryArrayException;

/**
 *
 * @author Anderson
 */
public class BinaryInt {

    public boolean signed;
    public boolean[] binaryNumberWithoutSignal;

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

        boolean[] thisBin = this.fullBynaryNumber();

        return null;
    }

    public void normalize(BinaryInt bin1, int length) throws BinaryArrayException {

        if (length < this.length()) {
            throw new BinaryArrayException("Array");
        }

        boolean[] aux = new boolean[length];

        for (int i = 0; i < length; i++) {

        }
        this.binaryNumberWithoutSignal = aux;
    }

    public static BinaryInt sum(BinaryInt bin1, BinaryInt bin2) {
        return null;
    }

}
